package niobe_ibug;

import simbad.sim.*;
import java.lang.Math;

import javax.vecmath.Vector3d;

public class MyRobot extends Agent {

    LightSensor centerLightSensor, leftLightSensor, rightLightSensor;
    RangeSensorBelt sonars;

    double iL, iH = 0;

    // robot's center sensor measurement, when the center of the robot is 0.6 + 0.3(robot radius) measurement units
    // away from the lights projection
    private static final double THRESHOLD = 0.079302;

    private static final double SAFETY = 1.0 + 0.3; // robot radius = 0.3
    private static final double CONSTANT = 10000.0; //10000.0500.0
    private static final double ERROR = 0.00001;

    // Constructor
    public MyRobot (Vector3d position, String name) {
        super(position,name);
        centerLightSensor = RobotFactory.addLightSensor(this);
        leftLightSensor = RobotFactory.addLightSensorLeft(this);
        rightLightSensor = RobotFactory.addLightSensorRight(this);
        sonars = RobotFactory.addSonarBeltSensor(this, 24, 0.6f);
    }

    // To rotate
    public void rotate(double lls_meas, double rls_meas) {
//        System.out.println("i'm rotating");
        setTranslationalVelocity(0);

        if (Math.abs(lls_meas - rls_meas) < ERROR)
            setRotationalVelocity(0);
        else
            setRotationalVelocity(CONSTANT*(lls_meas - rls_meas));
    }

    // To stop
    public void terminate() {
        setTranslationalVelocity(0);
        setRotationalVelocity(0);
        System.out.println("STOP");
    }

    // To trace the obstacle
    public void circumference() {
        System.out.println("CIRCUMFERENCE");
        double x, y;
        double sx = 0, sy = 0;

        for (int i=0; i < sonars.getNumSensors(); i++) {
            double d = sonars.getMeasurement(i) + this.radius;
            double th = sonars.getSensorAngle(i);

            if (sonars.hasHit(i)) {
//                System.out.println(i);
                x = -d * Math.cos(th);
                y = -d * Math.sin(th);
                sx += x;
                sy += y;
            }
        }

        if (sx != 0 || sy != 0) {
            setTranslationalVelocity(sx);
            setRotationalVelocity(sy);
        }

    }

    //
    public Vector3d circle() {
        System.out.println("CIRCLE");
        double x = 0, z = 0;
        double d, th;
        int min = 0;

        for (int i = 0; i < sonars.getNumSensors(); i++)
            if (sonars.getMeasurement(i) < sonars.getMeasurement(min))
                min = i;

        double left = sonars.getLeftQuadrantMeasurement();
        double right = sonars.getRightQuadrantMeasurement();
//        System.out.println("left = " + left);
//        System.out.println("right = " + right);
//        System.out.println("min = " + min);

        th = sonars.getSensorAngle(min);
        d = sonars.getMeasurement(min) + this.getRadius();

        if (d < SAFETY) {
            if (left < right) {
                z += -d * Math.cos(th);
                x += d * Math.sin(th);
            }
            else {
                z += d * Math.cos(th);
                x += -d * Math.sin(th);
            }
        }

        return new Vector3d(x,0,z);
    }

    //
    public Vector3d keepDistance() {
        int min = 0;

        for (int i = 1; i < sonars.getNumSensors(); i++)
            if (sonars.getMeasurement(i) < sonars.getMeasurement(min))
                min = i;

        double f = sonars.getSensorAngle(min);
        double dist = sonars.getMeasurement(min) + this.getRadius();
        double dx = 0, dz = 0;

        if (SAFETY - dist > 0.0) {
            dx = (dist - SAFETY) * Math.cos(f);
            dz = (dist - SAFETY) * Math.sin(f);
        }

        System.out.println("min meas. = " + sonars.getMeasurement(min));
        System.out.println("d = " + dist);
        System.out.println("dx = " + dx);
        System.out.println("dz = " + dz);

        return new Vector3d(dx,0,dz);
    }


    // Initial behavior
    public void initBehavior() {
        System.out.println(this.getRadius());
    }

    // Main behavior
    public void performBehavior() {

        double l = leftLightSensor.getLux();
        double r = rightLightSensor.getLux();
        double c = centerLightSensor.getLux();
        double d = (l+r) / 2.0;

        this.iL = c;

        rotate(l, r);
        this.setTranslationalVelocity(1-d);


        if (c >= THRESHOLD)
            terminate();

        c = centerLightSensor.getLux();

        if (!(Math.abs(iL - c) < ERROR)) {
            iH = centerLightSensor.getLux();
        }

        do{
            setTranslationalVelocity(5*keepDistance().x + 5*circle().x);
            setRotationalVelocity(5*keepDistance().z + 5*circle().z);

            c = centerLightSensor.getLux();
            System.out.println("c = " + c);
            System.out.println("iH = " + iH);
        }while (c <= iH);

        if (c >= THRESHOLD)
            terminate();

//        System.out.println("l = " + l);
//        System.out.println("r = " + r);
//        System.out.println("c = " + c);



    }
}