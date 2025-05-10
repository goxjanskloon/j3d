package goxjanskloon.j3d.pdf;
import goxjanskloon.j3d.Vector;
public class SpherePdf implements Pdf{
    public static final SpherePdf INSTANCE=new SpherePdf();
    private SpherePdf(){}
    @Override public double valueOf(Vector direction){
        return 1/(4*Math.PI);
    }
    @Override public Vector generate(){
        return Vector.randomUnit();
    }
}