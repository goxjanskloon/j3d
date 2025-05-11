package goxjanskloon.j3d;
import goxjanskloon.gfx.Color;
import goxjanskloon.gfx.Image;
import goxjanskloon.j3d.hittable.BvhTree;
import goxjanskloon.j3d.hittable.Hittable;
import goxjanskloon.j3d.hittable.Quadrilateral;
import goxjanskloon.j3d.hittable.Sphere;
import goxjanskloon.j3d.material.Dielectric;
import goxjanskloon.j3d.material.Lambertian;
import goxjanskloon.j3d.material.DiffuseLight;
import goxjanskloon.j3d.material.Material;
import goxjanskloon.j3d.texture.SolidTexture;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.fail;
public class CameraTest{
    @Test public void balls(){
        final List<Hittable> world=new ArrayList<>();
        world.add(new Sphere(new Vector(0,-2.5,0),2,new Dielectric(1.5,new SolidTexture(Color.WHITE))));
        world.add(new Quadrilateral(new Vector(-5,-5,-5),new Vector(10,0,0),new Vector(0,0,10),new Lambertian(new SolidTexture(Color.YELLOW))));
        Hittable light=new Quadrilateral(new Vector(-5,5,-5),new Vector(10,0,0),new Vector(0,0,10),new DiffuseLight(new SolidTexture(Color.WHITE.scale(3))));
        world.add(light);
        final BvhTree bvhTree=new BvhTree(world);
        final Ray ray=new Ray(new Vector(0,0,-20),new Vector(0,0,300));
        final Camera camera=new Camera(bvhTree,light,ray,new Vector(0,1,0),new Vector(1,0,0),300,300,8,10000,Color.BLACK,15);
        final Image image=camera.render();
        if(image!=null){
            try(FileWriter file=new FileWriter("CameraTest.balls().ppm")){
                image.output(file);
            }catch(IOException e){
                fail("Error writing image to file",e);
            }
        }else
            fail("Error rendering to image");
    }
    @Test public void cornellBox(){
        var world=new ArrayList<Hittable>();
        var red   = new Lambertian(new SolidTexture(Color.RED));
        var white = new Lambertian(new SolidTexture(Color.WHITE));
        var green = new Lambertian(new SolidTexture(Color.GREEN));
        var light = new DiffuseLight(new SolidTexture(Color.BLUE.scale(15)));

        // Cornell box sides
        world.add(new Quadrilateral(new Vector(555,0,0), new Vector(0,0,555), new Vector(0,555,0), green));
        world.add(new Quadrilateral(new Vector(0,0,555), new Vector(0,0,-555),new Vector(0,555,0), red));
        world.add(new Quadrilateral(new Vector(0,555,0), new Vector(555,0,0), new Vector(0,0,555), white));
        world.add(new Quadrilateral(new Vector(0,0,555), new Vector(555,0,0), new Vector(0,0,-555), white));
        world.add(new Quadrilateral(new Vector(555,0,555), new Vector(-555,0,0), new Vector(0,555,0), white));

        // Light
        world.add(new Quadrilateral(new Vector(213,554,227), new Vector(130,0,0), new Vector(0,0,105), light));

        // Box
        world.add(Hittable.box(new Vector(265,0,295), new Vector(165,330,165), white));

        // Glass Sphere
        var glass = new Dielectric(1.5,new SolidTexture(Color.WHITE));
        world.add(new Sphere(new Vector(190,90,190), 90, glass));

        // Light Sources
        var emptyMaterial=new Material(){};
        ArrayList<Hittable> lights=new ArrayList<>(2);
        lights.add(new Quadrilateral(new Vector(343,554,332),new Vector(-130,0,0),new Vector(0,0,-105),emptyMaterial));
        lights.add(new Sphere(new Vector(190, 90, 190), 90, emptyMaterial));
        var camera=new Camera(new BvhTree(world),new BvhTree(lights),new Ray(new Vector(0,0,-20),new Vector(0,0,300)),new Vector(0,1,0),new Vector(1,0,0),600,360,50,100,Color.BLACK,30);
        var image=camera.render();
        if(image!=null){
            try(FileWriter file=new FileWriter("CameraTest.cornellBox().ppm")){
                image.output(file);
            }catch(IOException e){
                fail("Error writing image to file",e);
            }
        }else
            fail("Error rendering to image");
    }
}