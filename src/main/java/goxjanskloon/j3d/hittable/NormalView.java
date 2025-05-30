package goxjanskloon.j3d.hittable;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.material.DiffuseLight;
import goxjanskloon.j3d.texture.SolidTexture;
import goxjanskloon.utils.Interval;
public class NormalView implements Hittable{
    public final Hittable object;
    public NormalView(Hittable object){
        this.object=object;
    }
    public Aabb getAabb(){
        return object.getAabb();
    }
    @Override public Vector random(Vector origin){
        return object.random(origin);
    }
    @Override public double pdfValue(Ray ray){
        return object.pdfValue(ray);
    }
    public HitRecord hit(Ray ray,Interval interval){
        HitRecord record=object.hit(ray,interval);
        if(record==null)
            return null;
        return new HitRecord(record.point,ray,record.normal,record.distance,record.u,record.v,new DiffuseLight(new SolidTexture(new Color((record.normal.x+1)/2,(record.normal.y+1)/2,(record.normal.z+1)/2))));
    }
}