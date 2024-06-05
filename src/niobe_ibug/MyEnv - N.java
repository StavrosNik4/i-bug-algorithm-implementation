package ibug;

import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class MyEnv extends EnvironmentDescription {
    MyEnv(){
        // lights
        light1SetPosition(5, 2, 5); // robot radius = 0.3
//        light1SetPosition(5, 2, 5);
        light1IsOn = true;
        light2SetPosition(100, 100, 100);
        light2IsOn = false;

        ambientLightColor = white;
        backgroundColor = ligthgray;
        floorColor = darkgray;
//        floorColor =  new Color3f(0.929F, 0.545F, 0.0F);
        wallColor = blue;

        // obstacles
//        Wall w1 = new Wall(new Vector3d(2, 0, 0), 10, 1, this);
//        Wall w1 = new Wall(new Vector3d(2, 0, 0), 10, 1, this);
//        w1.rotate90(1);
//        add(w1);

        // robot
        add(new CherryAgent(new Vector3d(1,0,2),"cherry",0.1f));
        add(new MyRobot(new Vector3d(-5, 0, 0), "robot 1"));


    }
}
