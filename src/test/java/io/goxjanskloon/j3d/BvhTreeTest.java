package io.goxjanskloon.j3d;
import org.junit.jupiter.api.*;
import io.goxjanskloon.utils.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
public final class BvhTreeTest{
    private final ArrayList<Hittable> objects;
    private final BvhTree bvhTree;
    public BvhTreeTest(){
        objects=new ArrayList<>();
        objects.add(new Sphere(new io.goxjanskloon.j3d.Vector(0,2,0),1,null,0,null));
        objects.add(new Sphere(new io.goxjanskloon.j3d.Vector(0,-2,0),1,null,0,null));
        objects.add(new Sphere(new io.goxjanskloon.j3d.Vector(2,0,0),1,null,0,null));
        objects.add(new Sphere(new io.goxjanskloon.j3d.Vector(-2,0,0),1,null,0,null));
        bvhTree=new BvhTree(objects);
    }
    @Test public void hit(){
        final Ray r1=new Ray(new io.goxjanskloon.j3d.Vector(0,0,-1),new io.goxjanskloon.j3d.Vector(0,1.5,0));
        assertNotNull(objects.getFirst().hit(r1,Interval.UNIVERSE));
        assertNotNull(bvhTree.hit(r1,Interval.UNIVERSE));
        assertNotNull(bvhTree.hit(new Ray(new io.goxjanskloon.j3d.Vector(0,0,-1),new io.goxjanskloon.j3d.Vector(0,-1.5,0)),Interval.UNIVERSE));
        assertNotNull(bvhTree.hit(new Ray(new io.goxjanskloon.j3d.Vector(0,0,-1),new io.goxjanskloon.j3d.Vector(1.5,0,0)),Interval.UNIVERSE));
        assertNotNull(bvhTree.hit(new Ray(new io.goxjanskloon.j3d.Vector(0,0,-1),new Vector(-1.5,0,0)),Interval.UNIVERSE));
    }
}