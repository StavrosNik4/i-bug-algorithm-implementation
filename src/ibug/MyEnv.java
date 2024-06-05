package ibug;

import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

import simbad.sim.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class MyEnv extends EnvironmentDescription {
    MyEnv(){
        // lights
        light1SetPosition(0, 2, 0); // second parameter is height
        light1IsOn = true;
        light2IsOn = false;
        this.ambientLightColor = new Color3f(0,0,0);

        // extra
        backgroundColor = ligthgray; floorColor =  new Color3f(0.929F, 0.545F, 0.0F);;
        archColor = red; boxColor = darkgray; wallColor = blue;

        // obstacles
        Wall w1 = new Wall(new Vector3d(-5, 0, 0), 10, 1, this);
        w1.rotate90(1);
        add(w1);

//        bottle(this);

        // robot
//        add(new CherryAgent(new Vector3d(1,0,2),"cherry",0.1f));
        add(new MyRobot(new Vector3d(-8, 0, 0), "robot 1"));


    }


//    // Bottle Object
//    static void bottle(EnvironmentDescription environment){
//        environment.add(new Box(new Vector3d(0,0,3), new Vector3f(5,1,5),environment));
//        environment.add(new Box(new Vector3d(0,0,-0.5), new Vector3f(2,1,2),environment));
//    }

}

// 0.0617283950617284