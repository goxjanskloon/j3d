package goxjanskloon.j3d;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.hittable.BvhTree;
import goxjanskloon.j3d.hittable.Hittable;
import goxjanskloon.j3d.hittable.Quadrilateral;
import goxjanskloon.j3d.hittable.Sphere;
import goxjanskloon.j3d.material.Dielectric;
import goxjanskloon.j3d.material.DiffuseLight;
import goxjanskloon.j3d.material.Lambertian;
import goxjanskloon.j3d.material.LambertianWithNormalMap;
import goxjanskloon.j3d.material.Material;
import goxjanskloon.j3d.texture.ImageTexture;
import goxjanskloon.j3d.texture.SolidTexture;
import goxjanskloon.j3d.texture.Texture;
import org.junit.jupiter.api.Test;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
public class CameraTest{
    @Test public void balls() throws IOException{
        ArrayList<Hittable> world=new ArrayList<>(9);
        Material glass=new Dielectric(1.5,new SolidTexture(Color.WHITE));
        world.add(new Sphere(new Vector(-2.5,-3,0),2,new Lambertian(
            new ImageTexture(ImageIO.read(Objects.requireNonNull(CameraTest.class.getResourceAsStream("earth.jpg")))))));
        world.add(new Sphere(new Vector(2.5,-2.5,0),2.5,glass));
        world.add(new Sphere(new Vector(-3,1,-2.5),2,glass));
        world.add(new Sphere(new Vector(2.5,2.5,-2.5),2,glass));
        Vector[][] brickwall=Texture.readNormalMap(ImageIO.read(Objects.requireNonNull(CameraTest.class.getResourceAsStream("brickwall-normal.jpg")))),
                     circles=Texture.readNormalMap(ImageIO.read(Objects.requireNonNull(CameraTest.class.getResourceAsStream("circles-normal.png"))));
        world.add(new Quadrilateral(new Vector(-5,-5,-5),new Vector(10,0,0),new Vector(0,0,10),new LambertianWithNormalMap(new SolidTexture(Color.YELLOW),circles)));
        world.add(new Quadrilateral(new Vector(-5,5,-5),new Vector(0,-10,0),new Vector(0,0,10),new LambertianWithNormalMap(new SolidTexture(Color.BLUE),brickwall)));
        world.add(new Quadrilateral(new Vector(5,5,-5),new Vector(0,-10,0),new Vector(0,0,10),new LambertianWithNormalMap(new SolidTexture(Color.RED),circles)));
        world.add(new Quadrilateral(new Vector(-5,5,5),new Vector(0,-10,0),new Vector(10,0,0),new LambertianWithNormalMap(new SolidTexture(Color.GREEN),brickwall)));
        Hittable light=new Quadrilateral(new Vector(-5,5,-5),new Vector(10,0,0),new Vector(0,0,10),new DiffuseLight(new SolidTexture(Color.WHITE.scale(2))));
        world.add(light);
        ImageIO.write(new Camera(new BvhTree(world),light,
            new Ray(new Vector(0,0,-20),new Vector(0,0,300)),
            new Vector(0,0.195,0),new Vector(0.195,0,0),
            1024,1024,16,
            8,1024,1e-4,
            Color.BLACK,
            Runtime.getRuntime().availableProcessors())
            .render(),"png",new File("CameraTest.balls().png"));
    }
}