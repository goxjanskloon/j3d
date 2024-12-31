package io.goxjanskloon.j3d;
public record Metal(double roughness) implements Brdf{
    @Override public double getValue(Vector normal,Vector reflectDir){
        return roughness*(1-roughness)/(Math.acos(normal.dot(reflectDir))+roughness)+roughness/(2*Math.PI);
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return Vector.randomOnHemisphere(normal);
    }
}