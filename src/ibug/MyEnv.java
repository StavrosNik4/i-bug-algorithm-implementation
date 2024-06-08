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
        light1SetPosition(5, 2, -4); // second parameter is height
        light1IsOn = true;
        light2IsOn = false;
        this.ambientLightColor = new Color3f(0,0,0);

        // extra
        backgroundColor = ligthgray; floorColor =  new Color3f(0.929F, 0.545F, 0.0F);;
        archColor = red; boxColor = darkgray; wallColor = blue;

        // obstacles

//        addBoxObstacle(this);
//
//        addWallObstacle(this);
//
//        addPyramidObstacle(this);

//        addMaze(this);

        addCustomStructure(this);

        // robot
        add(new MyRobot(new Vector3d(-8, 0, 0), "robot 1"));


    }


//    // Bottle Object
//    static void bottle(EnvironmentDescription environment){
//        environment.add(new Box(new Vector3d(0,0,3), new Vector3f(5,1,5),environment));
//        environment.add(new Box(new Vector3d(0,0,-0.5), new Vector3f(2,1,2),environment));
//    }

    // Adds a big box obstacle
    static void addBoxObstacle(EnvironmentDescription environment){
        // Add a single box at the center
        environment.add(new Box(new Vector3d(0, 0, 0), new Vector3f(4, 1, 4), environment));
    }

    // Adds a wall obstacle
    static void addWallObstacle(EnvironmentDescription environment){
        Wall w1 = new Wall(new Vector3d(-5, 0, 0), 10, 1, environment);
        w1.rotate90(1);
        environment.add(w1);
    }

    // Adds a custom structure using walls
    static void addCustomStructure(EnvironmentDescription environment){

        // Add the vertical wall
        Wall verticalWall = new Wall(new Vector3d(-3, 0, 0), 10, 1, environment);
        verticalWall.rotate90(1);
        environment.add(verticalWall);

        // Add the horizontal wall
        Wall horizontalWall = new Wall(new Vector3d(-1, 0, -5), 5, 1, environment);
        environment.add(horizontalWall);

//        // Add the box
//        Box box = new Box(new Vector3d(2, 0, 0), new Vector3f(3, 1, 3), environment);
//        environment.add(box);


    }



}