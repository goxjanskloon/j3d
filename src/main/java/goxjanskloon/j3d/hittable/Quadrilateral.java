package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.material.Material;
import goxjanskloon.utils.Interval;
import goxjanskloon.utils.Randoms;
public class Quadrilateral implements Hittable{
    public final Vector origin,u,v,normal;
    private final Vector w;
    public final double area;
    private final double D;
    public final Material material;
    public final Aabb aabb;
    public Quadrilateral(Vector origin,Vector u,Vector v,Material material){
        this.origin=origin;
        this.material=material;
        Vector n=(this.u=u).cross(this.v=v);
        normal=n.unit();
        area=n.norm();
        D=origin.dot(normal);
        w=n.div(n.selfDot());
        aabb=new Aabb(origin,origin.add(u).add(v)).unite(new Aabb(origin.add(u),origin.add(v))).padToMinimum();
    }
    @Override public Aabb getAabb(){
        return aabb;
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        var d=normal.dot(ray.direction);
        if(Math.abs(d)<1e-8)
            return null;
        var t=(D-ray.origin.dot(normal))/d;
        if(!interval.contains(t))
            return null;
        var p=ray.at(t);
        var q=p.sub(origin);
        var a=w.dot(q.cross(v));
        if(a<0||a>1)
            return null;
        var b=w.dot(u.cross(q));
        if(b<0||b>1)
            return null;
        return new HitRecord(p,ray,normal,t,a,b,material);
    }
    @Override public Vector random(Vector origin){
        return this.origin.add(u.mul(Randoms.nextDouble())).add(v.mul(Randoms.nextDouble())).sub(origin);
    }
    @Override public double pdfValue(Ray ray){
        var record=hit(ray,Hittable.HIT_RANGE);
        if(record==null)
            return 0;
        return record.distance*record.distance/(Math.abs(ray.direction.dot(record.normal))*area);
    }
}