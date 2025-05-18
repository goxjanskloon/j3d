package goxjanskloon.j3d;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.hittable.BvhTree;
import goxjanskloon.j3d.hittable.Hittable;
import goxjanskloon.j3d.hittable.Quadrilateral;
import goxjanskloon.j3d.hittable.Sphere;
import goxjanskloon.j3d.material.Dielectric;
import goxjanskloon.j3d.material.Lambertian;
import goxjanskloon.j3d.material.DiffuseLight;
import goxjanskloon.j3d.texture.SolidTexture;
import org.junit.jupiter.api.Test;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.fail;
public class CameraTest{
    @Test public void balls(){
        final List<Hittable> world=new ArrayList<>();
        var glass=new Dielectric(1.5,new SolidTexture(Color.WHITE));
        world.add(new Sphere(new Vector(-2.5,-3,0),2,glass));
        world.add(new Sphere(new Vector(2.5,-2.5,0),2.5,glass));
        world.add(new Sphere(new Vector(-3,1,-2.5),2,glass));
        world.add(new Sphere(new Vector(2.5,2.5,-2.5),2,glass));
        world.add(new Quadrilateral(new Vector(-5,-5,-5),new Vector(10,0,0),new Vector(0,0,10),new Lambertian(new SolidTexture(Color.YELLOW))));
        world.add(new Quadrilateral(new Vector(-5,5,-5),new Vector(0,-10,0),new Vector(0,0,10),new Lambertian(new SolidTexture(Color.BLUE))));
        world.add(new Quadrilateral(new Vector(5,5,-5),new Vector(0,-10,0),new Vector(0,0,10),new Lambertian(new SolidTexture(Color.RED))));
        world.add(new Quadrilateral(new Vector(-5,5,5),new Vector(0,-10,0),new Vector(10,0,0),new Lambertian(new SolidTexture(Color.GREEN))));
        Hittable light=new Quadrilateral(new Vector(-5,5,-5),new Vector(10,0,0),new Vector(0,0,10),new DiffuseLight(new SolidTexture(Color.WHITE.scale(2))));
        world.add(light);
        BvhTree bvhTree=new BvhTree(world);
        Ray ray=new Ray(new Vector(0,0,-20),new Vector(0,0,300));
        Camera camera=new Camera(bvhTree,light,ray,new Vector(0,0.195,0),new Vector(0.195,0,0),1024,1024,20,500,Color.BLACK,Runtime.getRuntime().availableProcessors(),false);
        BufferedImage image=camera.render();
        if(image!=null){
            try{
                ImageIO.write(image,"PNG",new File("CameraTest.balls().png"));
            }catch(IOException e){
                fail("Error writing image to file",e);
            }
        }else fail("Error rendering to image");
    }
}