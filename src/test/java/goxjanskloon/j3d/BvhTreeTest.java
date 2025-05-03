package goxjanskloon.j3d;
import goxjanskloon.j3d.hittable.BvhTree;
import goxjanskloon.j3d.hittable.Hittable;
import goxjanskloon.j3d.hittable.Sphere;
import goxjanskloon.utils.Interval;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class BvhTreeTest{
    private final ArrayList<Hittable> objects;
    private final BvhTree bvhTree;
    public BvhTreeTest(){
        objects=new ArrayList<>();
        objects.add(new Sphere(new Vector(0,2,0),1,null,0,null));
        objects.add(new Sphere(new Vector(0,-2,0),1,null,0,null));
        objects.add(new Sphere(new Vector(2,0,0),1,null,0,null));
        objects.add(new Sphere(new Vector(-2,0,0),1,null,0,null));
        bvhTree=new BvhTree(objects);
    }
    @Test public void hit(){
        final Ray r1=new Ray(new Vector(0,0,-1),new Vector(0,1.5,0));
        assertNotNull(objects.getFirst().hit(r1,Interval.UNIVERSE));
        assertNotNull(bvhTree.hit(r1,Interval.UNIVERSE));
        assertNotNull(bvhTree.hit(new Ray(new Vector(0,0,-1),new Vector(0,-1.5,0)),Interval.UNIVERSE));
        assertNotNull(bvhTree.hit(new Ray(new Vector(0,0,-1),new Vector(1.5,0,0)),Interval.UNIVERSE));
        assertNotNull(bvhTree.hit(new Ray(new Vector(0,0,-1),new Vector(-1.5,0,0)),Interval.UNIVERSE));
    }
}