package goxjanskloon.j3d.material;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.hittable.HitRecord;
import goxjanskloon.j3d.texture.Texture;
public class Metal implements Material{
    public final Texture texture;
    public final double fuzz;
    public Metal(Texture texture,double fuzz){
        this.texture=texture;
        this.fuzz=fuzz;
    }
    @Override public ScatterRecord scatter(Ray ray,HitRecord hit){
        return new ScatterRecord(null,texture.color(hit.u,hit.v,hit.point),Vector.reflect(ray.direction,hit.normal).add(Vector.randomUnit().mul(fuzz)));
    }
}