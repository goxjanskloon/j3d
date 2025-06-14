package goxjanskloon.j3d;
import goxjanskloon.utils.MathHelper;
import net.jafama.FastMath;
public class Vector{
    public final double x,y,z;
    public Vector(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public Vector(int xyz){
        x=((xyz&0xff0000)>>15)/255.0-1;
        y=((xyz&0xff00)>>7)/255.0-1;
        z=((xyz&0xff)<<1)/255.0-1;
    }
    public double get(Dimension d){
        return switch(d){
            case X->x;
            case Y->y;
            case Z->z;
        };
    }
    public double dot(Vector v){
        return x*v.x+y*v.y+z*v.z;
    }
    public Vector cross(Vector v){
        return new Vector(y*v.z-z*v.y,z*v.x-x*v.z,x*v.y-y*v.x);
    }
    public Vector add(Vector v){
        return new Vector(x+v.x,y+v.y,z+v.z);
    }
    public Vector sub(Vector v){
        return new Vector(x-v.x,y-v.y,z-v.z);
    }
    public Vector mul(Vector v){
        return new Vector(x*v.x,y*v.y,z*v.z);
    }
    public Vector mul(double v){
        return new Vector(x*v,y*v,z*v);
    }
    public Vector div(Vector v){
        return new Vector(x/v.x,y/v.y,z/v.z);
    }
    public Vector div(double v){
        return mul(1/v);
    }
    public Vector neg(){
        return new Vector(-x,-y,-z);
    }
    public double selfDot(){
        return dot(this);
    }
    public double norm(){
        return FastMath.sqrt(selfDot());
    }
    public Vector unit(){
        return div(norm());
    }
    public Vector rotate(Ray axis,double angle){
        return sub(axis.origin).rotate(axis.direction,angle).add(axis.origin);
    }
    public Vector rotate(Vector axis,double angle){
        var cos=FastMath.cos(angle);
        return mul(cos).add(axis.mul(1-cos).mul(dot(axis))).add(axis.cross(this).mul(FastMath.sin(angle)));
    }
    public static Vector randomUnit(){
        double a=MathHelper.nextDouble(0,MathHelper.C2PI),b=MathHelper.nextDouble(),c=2*FastMath.sqrt(b*(1-b));
        return new Vector(FastMath.cos(a)*c,FastMath.sin(a)*c,1-2*b);
    }
    public static Vector randomOnHemisphere(Vector normal){
        var v=randomUnit();
        if(normal.dot(v)>0)
            return v;
        return v.neg();
    }
    public static Vector cosineOnHemisphere(Vector normal){
        var a=MathHelper.nextDouble(0,MathHelper.C2PI);
        var b=MathHelper.nextDouble();
        var c=FastMath.sqrt(b);
        return new Onb(normal).transform(new Vector(FastMath.cos(a)*c,FastMath.sin(a)*c,FastMath.sqrt(1-b)));
    }
    public static Vector cosineOnHemisphere(Onb onb){
        var a=MathHelper.nextDouble(0,MathHelper.C2PI);
        var b=MathHelper.nextDouble();
        var c=FastMath.sqrt(b);
        return onb.transform(new Vector(FastMath.cos(a)*c,FastMath.sin(a)*c,FastMath.sqrt(1-b)));
    }
    public static Vector reflect(Vector v,Vector normal){
        return v.sub(normal.mul(v.dot(normal)*2));
    }
    public static Vector refract(Vector uv,Vector normal,double eoe){
        var pe=normal.mul(Math.min(-uv.dot(normal),1)).add(uv).mul(eoe);
        return normal.mul(-FastMath.sqrt(Math.abs(1-pe.selfDot()))).add(pe);
    }
}