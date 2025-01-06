package io.goxjanskloon.j3d;
import io.goxjanskloon.graphics.*;
import io.goxjanskloon.utils.Interval;
import io.goxjanskloon.utils.Randoms;
public class Quadrilateral implements Hittable{
    public final Vector origin,u,v,normal;
    private final Vector w;
    public final double area,brightness;
    private final double D;
    public final Material material;
    public final Color color;
    public final Aabb aabb;
    public Quadrilateral(Vector origin,Vector u,Vector v,Color color,double brightness,Material material){
        this.origin=origin;
        this.u=u;
        this.v=v;
        this.brightness=brightness;
        this.material=material;
        this.color=color;
        var c=u.cross(v);
        normal=c.unit();
        area=c.norm();
        D=origin.dot(normal);
        w=c.div(c.normSq());
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
        return new HitRecord(p,normal,color,brightness,t,material,d);
    }
    @Override public Vector randomOnSurface(){
        return origin.add(u.mul(Randoms.nextDouble())).add(v.mul(Randoms.nextDouble()));
    }
    @Override public double pdfValue(Ray ray){
        var record=hit(ray,Camera.HIT_RANGE);
        if(record==null)
            return 0;
        return record.distance*record.distance/(-ray.direction.dot(record.normal)*area);
    }
}