// Env.java
package ibug;

import myfirst.MyRobot;
import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;

import javax.vecmath.Vector3d;

public class MyEnv extends EnvironmentDescription {
    MyEnv(){
        add(new CherryAgent(new Vector3d(1,0,2),"cherry",0.1f));
        add(new MyRobot(new Vector3d(0, 0, 0), "robot 1"));
    }
}
