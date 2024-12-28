package io.goxjanskloon.v3d;
public record Ray(Vector orig,Vector dir){
    public Vector at(double t){
        return orig.add(dir.mul(t));
    }
}