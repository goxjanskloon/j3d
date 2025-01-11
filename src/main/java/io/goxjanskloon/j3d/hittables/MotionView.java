package io.goxjanskloon.j3d.hittables;
import io.goxjanskloon.j3d.Aabb;
import io.goxjanskloon.j3d.Ray;
import io.goxjanskloon.j3d.Vector;
import io.goxjanskloon.utils.*;
public class MotionView implements Hittable{
    public final Hittable object;
    public final Vector motion;
    public MotionView(Hittable object,Vector motion){
        this.object=object;
        this.motion=motion;
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        return object.hit(new Ray(ray.origin,ray.direction.add(motion.mul(Randoms.nextDouble()))),interval);
    }
    @Override public Aabb getAabb(){
        Aabb aabb=object.getAabb();
        return aabb.unite(aabb.move(motion));
    }
    @Override public Vector randomOnSurface(){
        return object.randomOnSurface();
    }
    @Override public double pdfValue(Ray ray){
        return object.pdfValue(ray);
    }
}