package io.goxjanskloon.j3d;
public class LambertianUniform implements Brdf{
    @Override public double getValue(Vector normal,Vector reflectDir){
        return 1/(2*Math.PI);
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return Vector.randomOnHemisphere(normal);
    }
}