package io.goxjanskloon.j3d;
import io.goxjanskloon.graphics.*;
import io.goxjanskloon.utils.Interval;
public class Quadrilateral implements Hittable{
    public final Vector origin,u,v,w,normal;
    public final double area,brightness,D;
    public final Brdf brdf;
    public final Color color;
    public final Aabb aabb;
    public Quadrilateral(Vector origin,Vector u,Vector v,Color color,double brightness,Brdf brdf){
        this.origin=origin;
        this.u=u;
        this.v=v;
        this.brightness=brightness;
        this.brdf=brdf;
        this.color=color;
        final Vector c=u.cross(v);
        normal=c.unit();
        area=c.norm();
        D=origin.dot(normal);
        w=c.div(c.normSq());
        final Aabb a=new Aabb(origin,origin.add(u).add(v)).unite(new Aabb(origin.add(u),origin.add(v)));
        aabb=new Aabb(new Interval(a.x.min,Math.max(a.x.min+1e-3,a.x.max)),new Interval(a.y.min,Math.max(a.y.min+1e-3,a.y.max)),new Interval(a.z.min,Math.max(a.z.min+1e-3,a.z.max)));
    }
    @Override public Aabb getAabb(){
        return aabb;
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        final double d=normal.dot(ray.direction);
        if(Math.abs(d)<1e-8)
            return null;
        final double t=(D-ray.origin.dot(normal))/d;
        if(!interval.contains(t))
            return null;
        final Vector p=ray.at(t),q=p.sub(origin);
        final double a=w.dot(q.cross(v));
        if(a<0||a>1)
            return null;
        final double b=w.dot(u.cross(q));
        if(b<0||b>1)
            return null;
        return new HitRecord(p,normal,color,brightness,t,brdf);
    }
}