package io.goxjanskloon.j3d.materials;
import io.goxjanskloon.j3d.pdfs.CosinePdf;
import io.goxjanskloon.j3d.pdfs.Pdf;
import io.goxjanskloon.j3d.Vector;
public class Lambert implements Material{
    @Override public Pdf getPdf(Vector in,Vector normal){
        return new /*Pdf(){
            @Override public double valueOf(Vector direction){
                return 1/(2*Math.PI);
            }
            @Override public Vector generate(){
                return Vector.randomOnHemisphere(normal);
            }
        }*/CosinePdf(normal);
    }
}