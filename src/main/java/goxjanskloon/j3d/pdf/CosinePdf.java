package goxjanskloon.j3d.pdf;
import goxjanskloon.j3d.Onb;
import goxjanskloon.j3d.Vector;
import goxjanskloon.utils.MathHelper;
import net.jafama.FastMath;
public class CosinePdf implements Pdf{
    public final Onb onb;
    public CosinePdf(Vector normal){
        onb=new Onb(normal);
    }
    @Override public double valueOf(Vector direction){
        return FastMath.abs(direction.dot(onb.w))*MathHelper.IPI;
    }
    @Override public Vector generate(){
        return Vector.cosineOnHemisphere(onb);
    }
}