package goxjanskloon.j3d.material;
import goxjanskloon.j3d.pdf.Pdf;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.pdf.DielectricPdf;
public class Dielectric implements Material{
    public final double refractionIndex;
    public Dielectric(double refractionIndex){
        this.refractionIndex=refractionIndex;
    }
    @Override public Pdf getPdf(Vector in,Vector normal){
        return new DielectricPdf(in,normal,refractionIndex);
    }
}