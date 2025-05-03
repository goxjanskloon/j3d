package goxjanskloon.j3d.material;
import goxjanskloon.j3d.pdf.Pdf;
import goxjanskloon.j3d.Vector;
public interface Material{
    Pdf getPdf(Vector in,Vector normal);
}