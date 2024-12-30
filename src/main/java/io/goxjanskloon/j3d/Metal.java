package io.goxjanskloon.j3d;
public record Metal(double roughness) implements Material{
    @Override public double getPdfValue(Vector theoretic,Vector real){
        return roughness*(1-roughness)/(Math.acos(theoretic.dot(real))+roughness)+roughness/(2*Math.PI);
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return Vector.randomOnHemisphere(normal);
    }
}