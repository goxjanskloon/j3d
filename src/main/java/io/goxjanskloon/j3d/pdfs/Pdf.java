package io.goxjanskloon.j3d.pdfs;
import io.goxjanskloon.j3d.Vector;
public interface Pdf{
    double valueOf(Vector direction);
    Vector generate();
}