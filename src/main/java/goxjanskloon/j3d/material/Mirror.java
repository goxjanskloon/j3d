package goxjanskloon.j3d.material;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.hittable.HitRecord;
public class Mirror implements Material{
    private Mirror(){}
    public static final Mirror INSTANCE=new Mirror();
    @Override public ScatterRecord scatter(Ray ray,HitRecord hit){
        return new ScatterRecord(null,Color.WHITE,Vector.reflect(ray.direction,hit.normal));
    }
}