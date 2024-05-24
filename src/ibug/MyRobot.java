package ibug;

import simbad.sim.*;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


public class MyRobot extends Agent {

    Point3d start=new Point3d();

    LightSensor centerLightSensor, leftLightSensor, rightLightSensor;

    public MyRobot (Vector3d position, String name) {
        super(position,name);
        centerLightSensor = RobotFactory.addLightSensor(this);
        leftLightSensor = RobotFactory.addLightSensorLeft(this);
        rightLightSensor = RobotFactory.addLightSensorRight(this);
    }

    public void initBehavior() {

    }

    public void performBehavior() {
        System.out.println("Average luminance is: " + centerLightSensor.getAverageLuminance());
    }
}