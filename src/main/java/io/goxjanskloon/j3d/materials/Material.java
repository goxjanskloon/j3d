package io.goxjanskloon.j3d.materials;
import io.goxjanskloon.j3d.pdfs.Pdf;
import io.goxjanskloon.j3d.Vector;
public interface Material{
    Pdf getPdf(Vector in,Vector normal);
}