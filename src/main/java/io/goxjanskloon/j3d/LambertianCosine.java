package io.goxjanskloon.j3d;
public class LambertianCosine implements Brdf{
    @Override public double getValue(Vector normal,Vector reflectDir){
        return reflectDir.dot(normal)/Math.PI;
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return Vector.cosineOnHemisphere(normal);
    }
}