package goxjanskloon.j3d;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.hittable.Hittable;
import goxjanskloon.j3d.hittable.HittableList;
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
import java.util.Objects;
public class CameraTest{
    @Test public void balls() throws IOException{
        Material glass=new Dielectric(1.5,new SolidTexture(Color.WHITE));
        Vector[][] brickwall=Texture.readNormalMap(ImageIO.read(Objects.requireNonNull(CameraTest.class.getResourceAsStream("brickwall-normal.jpg"))));
        Hittable light=new Quadrilateral(new Vector(-5,5,-5),new Vector(10,0,0),new Vector(0,0,10),new DiffuseLight(new SolidTexture(Color.WHITE)));
        ImageIO.write(new Camera(
            new HittableList(new Hittable[]{light,
                new Sphere(new Vector(-2.5,-3,0),2,new Lambertian(
                    new ImageTexture(ImageIO.read(Objects.requireNonNull(CameraTest.class.getResourceAsStream("earth.jpg")))))),
                new Sphere(new Vector(2.5,-2.5,0),2.5,glass),
                new Sphere(new Vector(-3,1,-2.5),2,glass),
                new Sphere(new Vector(2.5,2.5,-2.5),2,glass),
                new Quadrilateral(new Vector(-5,-5,-5),new Vector(10,0,0),new Vector(0,0,10),new LambertianWithNormalMap(new SolidTexture(new Color(0xe5c185)),brickwall)),
                new Quadrilateral(new Vector(-5,5,-5),new Vector(0,-10,0),new Vector(0,0,10),new LambertianWithNormalMap(new SolidTexture(new Color(0x008585)),brickwall)),
                new Quadrilateral(new Vector(5,5,-5),new Vector(0,-10,0),new Vector(0,0,10),new LambertianWithNormalMap(new SolidTexture(new Color(0xc7522a)),brickwall)),
                new Quadrilateral(new Vector(-5,5,5),new Vector(0,-10,0),new Vector(10,0,0),new LambertianWithNormalMap(new SolidTexture(new Color(0x74a892)),brickwall))
            }),light,
            new Ray(new Vector(0,0,-20),new Vector(0,0,300)),
            new Vector(0,0.195,0),new Vector(0.195,0,0),
            1024,1024,16,
            64,1024,1e-4,
            Color.BLACK,
            Runtime.getRuntime().availableProcessors())
            .render(),"png",new File("CameraTest.balls().png"));
    }
}