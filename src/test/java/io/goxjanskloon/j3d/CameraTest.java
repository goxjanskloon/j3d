package io.goxjanskloon.j3d;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import io.goxjanskloon.graphics.*;
import java.io.*;
import java.util.*;
public final class CameraTest{
    @Test public void render(){
        ArrayList<Hittable> world=new ArrayList<>();
        world.add(new Sphere(new io.goxjanskloon.j3d.Vector(0,50,0),45,Color.BLUE,0,new Metal(0.3)));
        world.add(new Sphere(new io.goxjanskloon.j3d.Vector(-5,0,0),5,Color.WHITE,1,new Mirror()));
        world.add(new Sphere(new io.goxjanskloon.j3d.Vector(5,0,0),5,Color.RED,0,new Lambertian()));
        world.add(new Sphere(new io.goxjanskloon.j3d.Vector(0,-50,0),45,Color.YELLOW,0,new Metal(0.3)));
        BvhTree bvhTree=new BvhTree(world);
        Ray ray=new Ray(new io.goxjanskloon.j3d.Vector(0,0,-20),new Vector(0,0,300));
        Camera camera=new Camera(bvhTree,ray,-Math.PI/2,600,360,8,1000,Color.BLACK,30);
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