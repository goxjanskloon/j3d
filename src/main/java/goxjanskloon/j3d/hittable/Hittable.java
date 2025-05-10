package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.utils.Interval;
public interface Hittable{
    Interval HIT_RANGE=new Interval(1e-5,Double.POSITIVE_INFINITY);
    HitRecord hit(Ray ray,Interval interval);
    Aabb getAabb();
    Vector random(Vector origin);
    double pdfValue(Ray ray);
}