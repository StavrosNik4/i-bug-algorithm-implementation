package ibug;

import simbad.sim.*;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


public class MyRobot extends Agent {

    Point3d start=new Point3d();

    LightSensor lightSensor;

    public MyRobot (Vector3d position, String name) {
        super(position,name);
        lightSensor = RobotFactory.addLightSensor(this);
    }

    public void initBehavior() {

    }

    public void performBehavior() {
        System.out.println("Average luminance is: " + lightSensor.getAverageLuminance());
    }
}