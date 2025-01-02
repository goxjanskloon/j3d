package io.goxjanskloon.j3d;
@Deprecated public class Metal implements Brdf{
    public final double roughness;
    public Metal(double roughness){
        this.roughness=roughness;
    }
    @Override public double getValue(Vector normal,Vector reflectDir){
        return roughness*(1-roughness)/(Math.acos(normal.dot(reflectDir))+roughness)+roughness/(2*Math.PI);
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return Vector.randomOnHemisphere(normal);
    }
}