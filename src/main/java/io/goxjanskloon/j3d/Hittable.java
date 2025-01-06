package io.goxjanskloon.j3d;
import io.goxjanskloon.graphics.*;
import io.goxjanskloon.utils.*;
public interface Hittable{
    class HitRecord{
        public final Vector point,normal;
        public final Color color;
        public final double brightness,distance;
        public final Material material;
        public HitRecord(Vector point,Vector normal,Color color,double brightness,double distance,Material material,Vector in){
            this(point,normal,color,brightness,distance,material,in.dot(normal));
        }
        public HitRecord(Vector point,Vector normal,Color color,double brightness,double distance,Material material,double cosine){
            this.point=point;
            this.normal=cosine<0?normal:normal.neg();
            this.color=color;
            this.brightness=brightness;
            this.distance=distance;
            this.material=material;
        }
    }
    HitRecord hit(Ray ray,Interval interval);
    Aabb getAabb();
    Vector randomOnSurface();
    double pdfValue(Ray ray);
}