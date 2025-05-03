package goxjanskloon.j3d.pdf;
import goxjanskloon.j3d.Onb;
import goxjanskloon.j3d.Vector;
public class CosinePdf implements Pdf{
    public final Onb onb;
    public CosinePdf(Vector normal){
        onb=new Onb(normal);
    }
    @Override public double valueOf(Vector direction){
        return Math.abs(direction.dot(onb.w))/Math.PI;
    }
    @Override public Vector generate(){
        return Vector.cosineOnHemisphere(onb);
    }
}