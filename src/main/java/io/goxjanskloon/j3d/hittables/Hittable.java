package io.goxjanskloon.j3d.hittables;
import io.goxjanskloon.graphics.*;
import io.goxjanskloon.j3d.Aabb;
import io.goxjanskloon.j3d.Ray;
import io.goxjanskloon.j3d.Vector;
import io.goxjanskloon.j3d.materials.Material;
import io.goxjanskloon.utils.*;
public interface Hittable{
    class HitRecord{
        public final Vector point,normal;
        public final Color color;
        public final double brightness,distance;
        public final Material material;
        public HitRecord(Vector point,Vector normal,Color color,double brightness,double distance,Material material){
            this.point=point;
            this.normal=normal;
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