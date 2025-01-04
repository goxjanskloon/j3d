package io.goxjanskloon.j3d;
import io.goxjanskloon.graphics.*;
import io.goxjanskloon.utils.*;
public class Sphere implements Hittable{
    public final Vector center;
    public final double radius,brightness;
    public final Color color;
    public final Material material;
    public Sphere(Vector center,double radius,Color color,double brightness,Material material){
        this.center=center;
        this.radius=radius;
        this.color=color;
        this.brightness=brightness;
        this.material=material;
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        final Vector co=ray.origin.sub(center);
        final double b=ray.direction.dot(co), d=b*b-co.normSq()+radius*radius;
        if(d<0)
            return null;
        final double sd=Math.sqrt(d);
        double t=-b-sd;
        if(!interval.contains(t))
            t+=sd*2;
        if(interval.contains(t)){
            final Vector point=ray.at(t);
            return new HitRecord(point,point.sub(center).unit(),color,brightness,t,material);
        }else
            return null;
    }
    @Override public Aabb getAabb(){
        return new Aabb(new Interval(center.x-radius,center.x+radius),new Interval(center.y-radius,center.y+radius),new Interval(center.z-radius,center.z+radius));
    }
    @Override public Vector randomOnSurface(){
        return Vector.randomUnit().mul(radius).add(center);
    }
    @Override public double pdfValue(Ray ray){
        final HitRecord record=hit(ray,Camera.HIT_RANGE);
        if(record==null)
            return 0;
       return 1/(2*Math.PI*(1-Math.sqrt(1-radius*radius/center.sub(ray.origin).normSq())));
    }
}