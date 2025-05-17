package goxjanskloon.j3d.pdf;
import goxjanskloon.j3d.Vector;
import goxjanskloon.utils.MathHelper;
public class SpherePdf implements Pdf{
    public static final SpherePdf INSTANCE=new SpherePdf();
    private SpherePdf(){}
    @Override public double valueOf(Vector direction){
        return MathHelper.I4PI;
    }
    @Override public Vector generate(){
        return Vector.randomUnit();
    }
}