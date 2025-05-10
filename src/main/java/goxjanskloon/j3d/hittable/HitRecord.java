package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.material.Material;
public class HitRecord{
    public final Vector point,normal;
    public final boolean frontFace;
    public final double distance,u,v;
    public final Material material;
    public HitRecord(Vector point,Ray ray,Vector outwardNormal,double distance,double u,double v,Material material){
        this.point=point;
        frontFace=ray.direction.dot(outwardNormal)<0;
        normal=frontFace?outwardNormal:outwardNormal.neg();
        this.distance=distance;
        this.u=u;
        this.v=v;
        this.material=material;
    }
}
