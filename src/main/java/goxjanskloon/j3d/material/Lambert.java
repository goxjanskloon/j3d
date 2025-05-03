package goxjanskloon.j3d.material;
import goxjanskloon.j3d.pdf.Pdf;
import goxjanskloon.j3d.Vector;
public class Lambert implements Material{
    @Override public Pdf getPdf(Vector in,Vector normal){
        return new Pdf(){
            @Override public double valueOf(Vector direction){
                return 1/(2*Math.PI);
            }
            @Override public Vector generate(){
                return Vector.randomOnHemisphere(normal);
            }
        };
    }
}