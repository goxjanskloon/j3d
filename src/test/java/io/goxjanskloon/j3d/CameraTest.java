package io.goxjanskloon.j3d;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import io.goxjanskloon.graphics.*;
import java.io.*;
import java.util.*;
public final class CameraTest{
    @Test public void render(){
        ArrayList<Hittable> world=new ArrayList<>();
        Material material=new Lambertian();
        world.add(new Sphere(new io.goxjanskloon.j3d.Vector(0,50,0),45,Color.BLUE,0,material));
        world.add(new Sphere(new io.goxjanskloon.j3d.Vector(-5,0,0),5,Color.WHITE,1,material));
        world.add(new Sphere(new io.goxjanskloon.j3d.Vector(5,0,0),5,Color.RED,0,material));
        world.add(new Sphere(new io.goxjanskloon.j3d.Vector(0,-50,0),45,Color.YELLOW,0,material));
        BvhTree bvhTree=new BvhTree(world);
        Ray ray=new Ray(new io.goxjanskloon.j3d.Vector(0,0,-20),new Vector(0,0,300));
        Camera camera=new Camera(bvhTree,ray,new Vector(0,1,0),new Vector(1,0,0),600,360,8,10,Color.BLACK,30);
        Image image=camera.render();
        if(image!=null){
            try{
                FileWriter file=new FileWriter("CameraTest.render().ppm");
                image.output(file);
                file.close();
            }catch(IOException e){
                fail("Error writing image to file",e);
            }
        }else
            fail("Error rendering to image");
    }
}