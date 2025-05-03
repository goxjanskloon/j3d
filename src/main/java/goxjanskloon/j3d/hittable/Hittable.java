package goxjanskloon.j3d.hittable;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.material.Material;
import goxjanskloon.utils.Interval;
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