package io.goxjanskloon.v3d;
import io.goxjanskloon.graphics.*;
import io.goxjanskloon.utils.*;
public interface Hittable{
    record HitRecord(Vector point,Vector normal,Color color,double brightness,double dist,Material material){ }
    HitRecord hit(Ray ray,Interval interval);
    Aabb getAabb();
}