package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.*;
import goxjanskloon.j3d.material.Material;
import goxjanskloon.utils.*;
import net.jafama.FastMath;
public class Sphere implements Hittable{
    public final Vector center;
    public final double radius,radiusSquared,iRadius;
    public final Material material;
    public final Aabb aabb;
    public Sphere(Vector center,double radius,Material material){
        this.center=center;
        iRadius=1/(this.radius=radius);
        radiusSquared=radius*radius;
        this.material=material;
        aabb=new Aabb(new Interval(center.x-radius,center.x+radius),new Interval(center.y-radius,center.y+radius),new Interval(center.z-radius,center.z+radius));
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        Vector co=ray.origin.sub(center);
        double b=ray.direction.dot(co),d=b*b-co.selfDot()+radiusSquared;
        if(d<0)
            return null;
        double sd=FastMath.sqrt(d),t=-b-sd;
        if(!interval.contains(t))
            t+=sd*2;
        if(interval.contains(t)){
            Vector point=ray.at(t),n=point.sub(center).mul(iRadius);
            return new HitRecord(point,ray,n,t,(FastMath.atan2(-n.z,n.x)+FastMath.PI)*MathHelper.I2PI,FastMath.acos(-n.y)*MathHelper.IPI,material);
        }else return null;
    }
    @Override public Aabb getAabb(){
        return aabb;
    }
    @Override public Vector random(Vector origin){
         Vector direction=center.sub(origin);
         Onb uvw=new Onb(direction);
         double z=1+MathHelper.nextDouble(0,FastMath.sqrt(1-radiusSquared/direction.selfDot())-1),a=MathHelper.nextDouble(0,MathHelper.C2PI),b=FastMath.sqrt(1-z*z);
         return uvw.transform(new Vector(FastMath.cos(a)*b,FastMath.sin(a)*b,z));
    }
    @Override public double pdfValue(Ray ray){
        var record=hit(ray,Hittable.HIT_RANGE);
        if(record==null)
            return 0;
       return 1/(MathHelper.C2PI*(1-FastMath.sqrt(1-radiusSquared/center.sub(ray.origin).selfDot())));
    }
}