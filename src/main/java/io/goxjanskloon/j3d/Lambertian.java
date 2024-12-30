package io.goxjanskloon.j3d;
public final class Lambertian implements Material{
    @Override public double getPdfValue(Vector theoretic,Vector real){
        return 1/(2*Math.PI);
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return Vector.randomOnHemisphere(normal);
    }
}