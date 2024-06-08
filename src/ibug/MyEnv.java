package ibug;

import simbad.sim.EnvironmentDescription;

import simbad.sim.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class MyEnv extends EnvironmentDescription {
    MyEnv(){
        // lights
        light1IsOn = true;
        light2IsOn = false;
        this.ambientLightColor = new Color3f(0,0,0);

        // extra
        backgroundColor = ligthgray; floorColor =  new Color3f(0.929F, 0.545F, 0.0F);;
        archColor = red; boxColor = darkgray; wallColor = blue;

        // obstacles
//        spiral(this);
        spheres(this);
//        box(this);
//        bottle(this);

    }

    void spiral(EnvironmentDescription environment){
        environment.add(new Box(new Vector3d(0,0,7), new Vector3f(14,1,1),environment));
        environment.add(new Box(new Vector3d(-2,0,3), new Vector3f(12,1,1),environment));
        environment.add(new Box(new Vector3d(0,0,-9), new Vector3f(16,1,1),environment));
        environment.add(new Box(new Vector3d(-7.5,0,-2.5), new Vector3f(1,1,12),environment));
        environment.add(new Box(new Vector3d(7.5,0,-0.5), new Vector3f(1,1,16),environment));
        environment.add(new Box(new Vector3d(3.5,0,-1), new Vector3f(1,1,7),environment));
        environment.add(new Box(new Vector3d(0,0,-5), new Vector3f(7,1,1),environment));
        environment.add(new Box(new Vector3d(-3.5,0,-2.5), new Vector3f(1,1,4),environment));
        environment.add(new Box(new Vector3d(-1.5,0,-1), new Vector3f(3,1,1),environment));

        environment.light1SetPosition(2, 2, -4); // second parameter is height
        environment.add(new MyRobot(new Vector3d(-8, 0, 5), "robot 1"));

    }

    void spheres(EnvironmentDescription environment){
        environment.add(new Box(new Vector3d(0,0,3), new Vector3f(5,1,5),environment));
        environment.add(new Box(new Vector3d(2.75,0,3), new Vector3f(0.5f,1,4),environment));
        environment.add(new Box(new Vector3d(3.25,0,3), new Vector3f(0.5f,1,3),environment));
        environment.add(new Box(new Vector3d(-2.75,0,3), new Vector3f(0.5f,1,4),environment));
        environment.add(new Box(new Vector3d(-3.25,0,3), new Vector3f(0.5f,1,3),environment));

        environment.add(new Box(new Vector3d(0,0,-4), new Vector3f(2,1,2),environment));

        environment.light1SetPosition(0, 2, 8); // second parameter is height
        environment.add(new MyRobot(new Vector3d(0, 0, -8), "robot 1"));

    }

    static void box(EnvironmentDescription environment){
        environment.add(new Box(new Vector3d(0,0,0), new Vector3f(5,1,5),environment));

        environment.light1SetPosition(0, 2, -6); // second parameter is height
        environment.add(new MyRobot(new Vector3d(0, 0, 8), "robot 1"));

    }
    static void bottle(EnvironmentDescription environment){
        environment.add(new Box(new Vector3d(0,0,3), new Vector3f(5,1,5),environment));
        environment.add(new Box(new Vector3d(0,0,-0.5), new Vector3f(2,1,2),environment));

        environment.light1SetPosition(0, 2, -6); // second parameter is height
        environment.add(new MyRobot(new Vector3d(0, 0, 8), "robot 1"));
    }



}