package io.goxjanskloon.j3d;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import io.goxjanskloon.graphics.*;
import java.io.*;
import java.util.*;
public final class CameraTest{
    @Test public void render(){
        final ArrayList<Hittable> world=new ArrayList<>();
        world.add(new Sphere(new Vector(-5,11,0),5,Color.WHITE,100,new Light()));
        world.add(new Sphere(new Vector(5,11,0),5,Color.WHITE,100,new Light()));
        //world.add(new Sphere(new Vector(0,50,0),45,Color.WHITE,5,new Light()));
        world.add(new Sphere(new Vector(-5,0,0),5,Color.RED,0,new LambertianUniform()));
        world.add(new Sphere(new Vector(5,0,0),5,Color.RED,0,new LambertianCosine()));
        //world.add(new Sphere(new Vector(0,-50,0),45,Color.YELLOW,0,new LambertianUniform()));
        final BvhTree bvhTree=new BvhTree(world);
        final Ray ray=new Ray(new Vector(0,0,-20),new Vector(0,0,300));
        final Camera camera=new Camera(bvhTree,ray,new Vector(0,1,0),new Vector(1,0,0),600,360,8,500,Color.BLACK,30);
        final Image image=camera.render();
        if(image!=null){
            try{
                final FileWriter file=new FileWriter("CameraTest.render().ppm");
                image.output(file);
                file.close();
            }catch(IOException e){
                fail("Error writing image to file",e);
            }
        }else
            fail("Error rendering to image");
    }
}