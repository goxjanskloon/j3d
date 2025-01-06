package io.goxjanskloon.j3d;
public class Lambertian implements Material{
    @Override public Pdf getPdf(Vector normal){
        return new Pdf(){
            @Override public double valueOf(Vector direction){
                return 1/(2*Math.PI);
            }
            @Override public Vector generate(){
                return Vector.randomOnHemisphere(normal);
            }
        };
    }
}