package ibug;

import simbad.sim.*;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


public class MyRobot extends Agent {

    // parameters for circum navigate
    double K1 = 2;
    double K2 = 1;
    double K3 = 1;
    double SAFETY = 1;

    LightSensor centerLightSensor, leftLightSensor, rightLightSensor;
    RangeSensorBelt sonars;

    // intensity threshold for the robot to be in a distance less than 0.6m away from the projection of the light
    double goalIntensity = 0.79;

    double iL = 0;
    double iH = 0;

    // array to store the 3 most recent intensity measurements
    double[] i_k = {0.0, 0.0, 0.0};

    public enum RobotBehavior {
        UFWD,
        UORI,
        UFOL
    }

    public RobotBehavior behavior;

    public MyRobot (Vector3d position, String name) {
        super(position,name);

        centerLightSensor = RobotFactory.addLightSensor(this, new Vector3d(0.0,0.5,0.0), 0, "center");
        leftLightSensor = RobotFactory.addLightSensor(this, new Vector3d(0.6,0.5,-0.6), 0, "left");
        rightLightSensor = RobotFactory.addLightSensor(this, new Vector3d(0.6,0.5,0.6), 0, "right");
        sonars = RobotFactory.addSonarBeltSensor(this,8);
    }

    public void initBehavior() {
        behavior = RobotBehavior.UORI;
    }

    // Function to choose which behavior to perform per step
    public void performBehavior() {
        System.out.println("Average Center luminance is: " + Math.pow(centerLightSensor.getLux(), 0.1));
        System.out.println("Average Left luminance is: " + Math.pow(leftLightSensor.getLux(), 0.1));
        System.out.println("Average Right luminance is: " + Math.pow(rightLightSensor.getLux(), 0.1));

        // get each light sensor's intensity
        double leftIntensity = Math.pow(leftLightSensor.getLux(), 0.1);
        double rightIntensity = Math.pow(rightLightSensor.getLux(), 0.1);
        double centerIntensity = Math.pow(centerLightSensor.getLux(), 0.1);


        // Skip 0 values (this was originally for getAverageLuminance, no longer needed)
        if (centerIntensity == 0) {
            behavior = RobotBehavior.UORI;
            return;
        }

        // shift the previous 3 intensity measurements
        i_k[2] = i_k[1];
        i_k[1] = i_k[0];
        i_k[0] = centerIntensity;

        // Stop the robot if it reached the max intensity
        if (centerIntensity > goalIntensity){   // step 3
            setTranslationalVelocity(0);
            setRotationalVelocity(0);
            System.out.println("Reached the goal!");
            return;
        }


        switch (behavior) {
            case UFWD:

                if (iL != centerIntensity) { // step 4 of i-bug algorithm
                    iH = centerIntensity;
                }

                setTranslationalVelocity(1.0 / (centerIntensity * 0.5));
                if (!collisionDetected()) {
                    behavior = RobotBehavior.UORI; // step 5 of i-bug algorithm (go to step 1)
                }
                else {
                    setTranslationalVelocity(0);
                    behavior = RobotBehavior.UFOL; // step 6 of i-bug algorithm
                }

                break;
            case UORI:

                iL = centerIntensity;   // step 1 of i-bug algorithm


                // step 2 of i-bug algorithm
                setTranslationalVelocity(0);

                if (Math.abs(rightIntensity - leftIntensity) > 0.001) {
                    System.out.println(100 * (leftIntensity - rightIntensity));
                    setRotationalVelocity(100 * (leftIntensity - rightIntensity));
                }
                else if (centerIntensity > rightIntensity && centerIntensity > leftIntensity){
                    setRotationalVelocity(1);
                }
                else {
                    setRotationalVelocity(0);
                    behavior = RobotBehavior.UFWD;
                }
                break;

            case UFOL:
                circumNavigate(false); // step 6 of i-bug algorithm

                // leave obstacle (local max)
                if (centerIntensity > iH && i_k[1] > i_k[2] && i_k[1] > i_k[0]) { // step 7 of i-bug algorithm      // extra: centerIntensity > iL
                    behavior = RobotBehavior.UORI; // (go to step 1)
                }

                break;
        }

    }

    // Function that gets the measurements of the distance from an obstacle for each sonar and if it is lower than a
    // safety threshold, it returns true
    @Override
    public boolean collisionDetected() {

        for (int i = 0; i < sonars.getNumSensors(); i++) {
            if (sonars.getMeasurement(i) < SAFETY) {
                return true;
            }
        }
        return false;

    }


    public double wrapToPi(double a) {
        while (a > Math.PI)
            a -= Math.PI * 2;
        while (a <= -Math.PI)
            a += Math.PI * 2;
        return a;
    }


    public Point3d getSensedPoint(int sonar) {
        double v;
        if (sonars.hasHit(sonar))
            v = getRadius() + sonars.getMeasurement(sonar);
        else
            v = getRadius() + sonars.getMaxRange();
        double x = v * Math.cos(sonars.getSensorAngle(sonar));
        double z = v * Math.sin(sonars.getSensorAngle(sonar));
        return new Point3d(x, 0, z);
    }


    public void circumNavigate(boolean CLOCKWISE){
        int min;
        min=0;
        for (int i=1;i<sonars.getNumSensors();i++)
            if (sonars.getMeasurement(i)<sonars.getMeasurement(min))
                min=i;
        Point3d p = getSensedPoint(min);
        double d = p.distance(new Point3d(0,0,0));
        Vector3d v;
        if (CLOCKWISE)
            v = new Vector3d(-p.z,0,p.x);
        else
            v = new Vector3d(p.z,0,-p.x);
        double phLin = Math.atan2(v.z,v.x);
        double phRot = Math.atan(K3*(d-SAFETY));
        if (CLOCKWISE)
            phRot=-phRot;
        double phRef = wrapToPi(phLin+phRot);

        setRotationalVelocity(K1*phRef);
        setTranslationalVelocity(K2*Math.cos(phRef));

    }


}