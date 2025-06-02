package goxjanskloon.j3d.pdf;
import goxjanskloon.j3d.Vector;
import goxjanskloon.utils.MathHelper;
public class MixturePdf implements Pdf{
    public final Pdf p1,p2;
    public MixturePdf(Pdf p1,Pdf p2){
        this.p1=p1;
        this.p2=p2;
    }
    @Override public double valueOf(Vector direction){
        return (p1.valueOf(direction)+p2.valueOf(direction))/2;
    }
    @Override public Vector generate(){
        return MathHelper.nextBoolean()?p1.generate():p2.generate();
    }
}