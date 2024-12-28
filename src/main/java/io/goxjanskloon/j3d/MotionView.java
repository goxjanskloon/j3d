package io.goxjanskloon.j3d;
import io.goxjanskloon.utils.*;
public record MotionView(Hittable hittable,Vector motion) implements Hittable{
    @Override public HitRecord hit(Ray ray,Interval interval){
        return hittable.hit(new Ray(ray.orig(),ray.dir().add(motion.mul(Randoms.nextDouble()))),interval);
    }
    @Override public Aabb getAabb(){
        Aabb aabb=hittable.getAabb();
        return aabb.unite(aabb.move(motion));
    }
}