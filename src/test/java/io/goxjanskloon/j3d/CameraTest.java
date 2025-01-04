package io.goxjanskloon.j3d;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import io.goxjanskloon.graphics.*;
import java.io.*;
import java.util.*;
public class CameraTest{
    @Test public void balls(){
        final List<Hittable> world=new ArrayList<>();
        world.add(new Sphere(new Vector(-5,0,0),5,Color.RED,0,new Lambertian()));
        world.add(new Sphere(new Vector(5,0,0),5,Color.BLUE,0,new Lambertian()));
        Hittable light=new Quadrilateral(new Vector(-5,11,-5),new Vector(10,0,0),new Vector(0,0,10),Color.WHITE,5,new Light());
        world.add(light);
        final BvhTree bvhTree=new BvhTree(world);
        final Ray ray=new Ray(new Vector(0,0,-20),new Vector(0,0,300));
        final Camera camera=new Camera(bvhTree,light,ray,new Vector(0,1,0),new Vector(1,0,0),600,360,8,100,Color.BLACK,30);
        final Image image=camera.render();
        if(image!=null){
            try{
                final FileWriter file=new FileWriter("CameraTest.balls().ppm");
                image.output(file);
                file.close();
            }catch(IOException e){
                fail("Error writing image to file",e);
            }
        }else
            fail("Error rendering to image");
    }
    @Test public void cornellBox(){
        final List<Hittable> world=new ArrayList<>();
        final Material lambertian=new Lambertian();
        world.add(new Quadrilateral(new Vector(-5,10,-5),new Vector(0,0,10),new Vector(0,-20,0),Color.RED,0,lambertian));
        world.add(new Quadrilateral(new Vector(5,10,-5),new Vector(0,0,10),new Vector(0,-20,0),Color.GREEN,0,lambertian));
        world.add(new Quadrilateral(new Vector(-5,-10,-5),new Vector(0,0,10),new Vector(10,0,0),Color.YELLOW,0,lambertian));
        world.add(new Quadrilateral(new Vector(-5,10,5),new Vector(10,0,0),new Vector(0,-20,0),Color.CYAN,0,lambertian));
        Hittable light=new Quadrilateral(new Vector(-5,10,-5),new Vector(10,0,0),new Vector(0,0,10),Color.WHITE,1,new Light());
        world.add(light);
        final BvhTree bvhTree=new BvhTree(world);
        final Ray ray=new Ray(new Vector(0,0,-20),new Vector(0,0,300));
        final Camera camera=new Camera(bvhTree,light,ray,new Vector(0,1,0),new Vector(1,0,0),600,360,8,1000,Color.BLACK,30);
        final Image image=camera.render();
        if(image!=null){
            try{
                final FileWriter file=new FileWriter("CameraTest.cornellBox().ppm");
                image.output(file);
                file.close();
            }catch(IOException e){
                fail("Error writing image to file",e);
            }
        }else
            fail("Error rendering to image");
    }
}