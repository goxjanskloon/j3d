package io.goxjanskloon.j3d;
public class Onb{
    public final Vector u,v,w;
    public Onb(Vector u,Vector v,Vector w){
        this.u=u;
        this.v=v;
        this.w=w;
    }
    public Onb(Vector normal){
        w=normal;
        final Vector a=Math.abs(w.x)>.9?new Vector(0,1,0):new Vector(1,0,0);
        v=a.cross(w).unit();
        u=w.cross(v);
    }
    public Vector transform(Vector a){
        return u.mul(a.x).add(v.mul(a.y)).add(w.mul(a.z));
    }
}