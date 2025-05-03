package goxjanskloon.j3d;
public class Ray{
    public final Vector origin,direction;
    public Ray(Vector origin,Vector direction){
        this.origin=origin;
        this.direction=direction;
    }
    public Vector at(double t){
        return origin.add(direction.mul(t));
    }
}