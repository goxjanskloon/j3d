package io.goxjanskloon.j3d;
public class Lambertian implements Material{
    @Override public Pdf getPdf(Vector normal){
        return new CosinePdf(normal);
    }
}