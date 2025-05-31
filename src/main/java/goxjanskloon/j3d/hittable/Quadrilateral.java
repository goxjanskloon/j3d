package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.material.Material;
import goxjanskloon.utils.Interval;
import goxjanskloon.utils.MathHelper;
import net.jafama.FastMath;
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
        double d=normal.dot(ray.direction);
        if(FastMath.abs(d)<1e-8)
            return null;
        double t=(D-ray.origin.dot(normal))/d;
        if(!interval.contains(t))
            return null;
        Vector p=ray.at(t),q=p.sub(origin);
        double a=w.dot(q.cross(v));
        if(a<0||a>1)
            return null;
        double b=w.dot(u.cross(q));
        if(b<0||b>1)
            return null;
        return new HitRecord(p,ray,normal,t,a,b,material);
    }
    @Override public Vector random(Vector origin){
        return this.origin.add(u.mul(MathHelper.nextDouble())).add(v.mul(MathHelper.nextDouble())).sub(origin);
    }
    @Override public double pdfValue(Ray ray){
        HitRecord record=hit(ray,Hittable.HIT_RANGE);
        if(record==null)
            return 0;
        return record.distance*record.distance/(FastMath.abs(ray.direction.dot(record.normal))*area);
    }
}