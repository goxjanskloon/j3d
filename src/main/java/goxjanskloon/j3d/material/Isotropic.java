package goxjanskloon.j3d.material;
import goxjanskloon.j3d.pdf.Pdf;
import goxjanskloon.j3d.pdf.SpherePdf;
import goxjanskloon.j3d.Vector;
public class Isotropic implements Material{
    @Override public Pdf getPdf(Vector in,Vector normal){
        return new SpherePdf();
    }
}