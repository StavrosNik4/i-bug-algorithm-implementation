package myfirst_extended;// Env.java
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.*;


public class MyEnv extends EnvironmentDescription {
    public MyEnv(){
        light1SetPosition(-8, 4, 5);
        light1IsOn = true;
        light2SetPosition(8, 4, -5);
        light2IsOn = false; // Never open it
        setWorldSize(10);
        showAxis(true);
        Wall w1 = new Wall(new Vector3d(3, 0, 0), 19, 1, this);
        w1.rotate90(1);
        add(w1);
        add(new Line(new Vector3d(2, 0, 0), 2, this));
        add(new Arch(new Vector3d(0,0,5),this));
        add(new Box(new Vector3d(-2,0,0), new Vector3f(1,1,1),this));
        add(new CherryAgent(new Vector3d(1,0,2),"cherry",0.1f));
        add(new MyRobot(new Vector3d(0, 0, 0), "robot 1"));
        ambientLightColor = white;
        backgroundColor = ligthgray; floorColor = white;
        archColor = red; boxColor = darkgray; wallColor = blue;
    }
}
