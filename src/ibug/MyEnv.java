package ibug;

import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class MyEnv extends EnvironmentDescription {
    MyEnv(){
        light1SetPosition(20, 10, 0);
        light1IsOn = true;
        //light2SetPosition(8, 4, -5);
        light2IsOn = false;
        add(new CherryAgent(new Vector3d(1,0,2),"cherry",0.1f));
        add(new MyRobot(new Vector3d(0, 0, 0), "robot 1"));
        ambientLightColor = white;
        backgroundColor = ligthgray; floorColor =  new Color3f(0.929F, 0.545F, 0.0F);;
        archColor = red; boxColor = darkgray; wallColor = blue;

    }
}
