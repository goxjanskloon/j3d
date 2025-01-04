package io.goxjanskloon.j3d;
public class Isotropic implements Material{
    @Override public Pdf getPdf(Vector normal){
        return new SpherePdf();
    }
}