package io.goxjanskloon.j3d.hittables;
import io.goxjanskloon.j3d.Aabb;
import io.goxjanskloon.j3d.Ray;
import io.goxjanskloon.j3d.Vector;
import io.goxjanskloon.utils.*;
public class MotionView implements Hittable{
    public final Hittable object;
    public final Vector motion;
    public final Aabb aabb;
    public MotionView(Hittable object,Vector motion){
        this.object=object;
        this.motion=motion;
        Aabb a=object.getAabb();
        aabb=a.unite(a.move(motion));
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        return object.hit(new Ray(ray.origin,ray.direction.add(motion.mul(Randoms.nextDouble()))),interval);
    }
    @Override public Aabb getAabb(){
        return aabb;
    }
    @Override public Vector randomOnSurface(){
        return object.randomOnSurface();
    }
    @Override public double pdfValue(Ray ray){
        return object.pdfValue(ray);
    }
}