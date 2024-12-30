package io.goxjanskloon.j3d;
public interface Material{
    double getPdfValue(Vector theoretic,Vector real);
    Vector generate(Vector normal,Vector theoretic);
}