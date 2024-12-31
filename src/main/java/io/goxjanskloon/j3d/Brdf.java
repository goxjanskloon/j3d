package io.goxjanskloon.j3d;
public interface Brdf{
    double getValue(Vector normal,Vector reflectDir);
    Vector generate(Vector normal,Vector theoretic);
}