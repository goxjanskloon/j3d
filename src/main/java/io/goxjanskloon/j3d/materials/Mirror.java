package io.goxjanskloon.j3d.materials;
import io.goxjanskloon.j3d.pdfs.MirrorPdf;
import io.goxjanskloon.j3d.pdfs.Pdf;
import io.goxjanskloon.j3d.Vector;
public class Mirror implements Material{
    @Override public Pdf getPdf(Vector in,Vector normal){
        return new MirrorPdf(Vector.reflect(in,normal));
    }
}