package io.goxjanskloon.j3d;
public class Light implements Brdf{
    @Override public double getValue(Vector normal,Vector reflectDir){
        return 0;
    }
    @Override public Vector generate(Vector normal,Vector theoretic){
        return null;
    }
}