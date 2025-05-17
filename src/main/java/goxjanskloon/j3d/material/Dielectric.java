package goxjanskloon.j3d.material;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.hittable.HitRecord;
import goxjanskloon.j3d.texture.Texture;
import goxjanskloon.utils.Randoms;
public class Dielectric implements Material{
    public final double refraction,iRefraction,reflectance,iReflectance;
    public final Texture texture;
    public Dielectric(double refraction,Texture texture){
        this.refraction=refraction;
        double r=(1-refraction)*(1+refraction);
        reflectance=r*r;
        iRefraction=1/refraction;
        double ir=(1-iRefraction)*(1+iRefraction);
        iReflectance=ir*ir;
        this.texture=texture;
    }
    @Override public ScatterRecord scatter(Ray ray,HitRecord hit){
        double r=hit.frontFace?iRefraction:refraction,c=Math.min(ray.direction.neg().dot(hit.normal),1),a=hit.frontFace?iReflectance:reflectance;
        return new ScatterRecord(null,texture.color(hit.u,hit.v,hit.point),r*Math.sqrt(1-c*c)>1||a+(1-a)*Math.pow(1-c,5)>Randoms.nextDouble()?Vector.reflect(ray.direction,hit.normal):Vector.refract(ray.direction,hit.normal,r));
    }
}