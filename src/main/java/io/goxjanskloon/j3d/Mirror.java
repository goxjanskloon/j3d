package io.goxjanskloon.j3d;
public final class Mirror implements Material{
    @Override public double getPdfValue(Vector theoretic,Vector real){
        return theoretic==real?1:0;
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return theoretic;
    }
}