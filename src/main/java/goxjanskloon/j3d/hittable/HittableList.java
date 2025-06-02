package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.utils.Interval;
import goxjanskloon.utils.MathHelper;
public class HittableList implements Hittable{
    public final Hittable[] objects;
    public HittableList(Hittable[] objects){
        this.objects=objects;
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        HitRecord hit=null;
        for(Hittable o:objects){
            HitRecord h=o.hit(ray,interval);
            if(h!=null){
                if(hit!=null){
                    if(h.distance<hit.distance)
                        hit=h;
                }else hit=h;
            }
        }
        return hit;
    }
    @Override public Aabb getAabb(){
        throw new UnsupportedOperationException();
    }
    @Override public Vector random(Vector origin){
        return objects[MathHelper.nextInt(0,objects.length)].random(origin);
    }
    @Override public double pdfValue(Ray ray){
        double s=0;
        for(Hittable o:objects)
            s+=o.pdfValue(ray);
        return s/objects.length;
    }
}