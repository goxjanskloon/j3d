package io.goxjanskloon.j3d;
public final class Mirror implements Brdf{
    @Override public double getValue(Vector normal,Vector reflectDir){
        return normal==reflectDir?1:0;
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return theoretic;
    }
}