package goxjanskloon.j3d.material;
import goxjanskloon.j3d.pdf.MirrorPdf;
import goxjanskloon.j3d.pdf.Pdf;
import goxjanskloon.j3d.Vector;
public class Mirror implements Material{
    @Override public Pdf getPdf(Vector in,Vector normal){
        return new MirrorPdf(Vector.reflect(in,normal));
    }
}