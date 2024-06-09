package niobe_ibug;

import simbad.sim.EnvironmentDescription;
import javax.vecmath.Vector3d;

public class MyEnv extends EnvironmentDescription {
    MyEnv(){
        // lights
        light1SetPosition(5, 2, 5);
        light1IsOn = true;
        light2SetPosition(100, 100, 100);
        light2IsOn = false;

        ambientLightColor = white;
        backgroundColor = ligthgray;
        floorColor = darkgray;
        wallColor = blue;

        // obstacles
//        Wall w1 = new Wall(new Vector3d(2, 0, 0), 10, 1, this);
//        w1.rotate90(1);
//        add(w1);

        // robot
        add(new MyRobot(new Vector3d(-5, 0, 0), "robot 1"));
    }
}