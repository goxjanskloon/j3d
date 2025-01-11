package io.goxjanskloon.j3d.materials;
import io.goxjanskloon.j3d.pdfs.Pdf;
import io.goxjanskloon.j3d.pdfs.SpherePdf;
import io.goxjanskloon.j3d.Vector;
public class Isotropic implements Material{
    @Override public Pdf getPdf(Vector in,Vector normal){
        return new SpherePdf();
    }
}