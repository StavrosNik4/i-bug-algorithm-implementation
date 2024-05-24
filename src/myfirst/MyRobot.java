// MyRobot.java
package myfirst;
import javax.vecmath.Vector3d;
import simbad.sim.Agent;
public class MyRobot extends Agent {
    public MyRobot (Vector3d position, String name) {
        super(position,name);
    }
    public void initBehavior() {
        setTranslationalVelocity(0.5);
        setRotationalVelocity(0.3);
    }
    public void performBehavior()
    {
    }
}
