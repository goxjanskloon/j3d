package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.material.Material;
import goxjanskloon.utils.Interval;
import java.util.ArrayList;
public interface Hittable{
    Interval HIT_RANGE=new Interval(1e-5,Double.POSITIVE_INFINITY);
    HitRecord hit(Ray ray,Interval interval);
    Aabb getAabb();
    Vector random(Vector origin);
    double pdfValue(Ray ray);
    static Hittable box(Vector a,Vector b,Material material){
        var min=new Vector(Math.min(a.x,b.x),Math.min(a.y,b.y),Math.min(a.z,b.z));
        var max=new Vector(Math.max(a.x,b.x),Math.max(a.y,b.y),Math.max(a.z,b.z));
        var dx=new Vector(max.x-min.x,0,0);
        var dy=new Vector(0,max.y-min.y,0);
        var dz=new Vector(0,0,max.z-min.z);
        ArrayList<Hittable> sides=new ArrayList<>(6);
        sides.add(new Quadrilateral(new Vector(min.x,min.y,max.z),dx,dy,material));
        sides.add(new Quadrilateral(new Vector(max.x,min.y,max.z),dz.neg(),dy,material));
        sides.add(new Quadrilateral(new Vector(max.x,min.y,min.z),dx.neg(),dy,material));
        sides.add(new Quadrilateral(new Vector(min.x,min.y,min.z),dz,dy,material));
        sides.add(new Quadrilateral(new Vector(min.x,max.y,max.z),dx,dz.neg(),material));
        sides.add(new Quadrilateral(new Vector(min.x,min.y,min.z),dx,dz,material));
        return new BvhTree(sides);
    }
}