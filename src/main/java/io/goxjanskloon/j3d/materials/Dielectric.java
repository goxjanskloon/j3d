package io.goxjanskloon.j3d.materials;
import io.goxjanskloon.j3d.pdfs.Pdf;
import io.goxjanskloon.j3d.Vector;
import io.goxjanskloon.j3d.pdfs.DielectricPdf;
public class Dielectric implements Material{
    public final double refractionIndex;
    public Dielectric(double refractionIndex){
        this.refractionIndex=refractionIndex;
    }
    @Override public Pdf getPdf(Vector in,Vector normal){
        return new DielectricPdf(in,normal,refractionIndex);
    }
}