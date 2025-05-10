package goxjanskloon.j3d.material;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.hittable.HitRecord;
public interface Material{
    default Color emit(Ray ray,HitRecord hit){
        return Color.BLACK;
    }
    default ScatterRecord scatter(Ray ray,HitRecord hit){
        return null;
    }
    default double scatteringPdf(HitRecord hit,Vector scattered){
        return 0;
    }
}