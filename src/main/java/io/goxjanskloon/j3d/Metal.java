package io.goxjanskloon.j3d;
record Metal(double roughness) implements Material{
    @Override public double getPossibility(Vector theoretic,Vector real){
        return Math.pow(roughness,(theoretic.dot(real)+1)/2);
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return Vector.randomOnHemisphere(normal);
    }
}