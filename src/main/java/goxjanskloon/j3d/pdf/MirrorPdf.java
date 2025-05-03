package goxjanskloon.j3d.pdf;
import goxjanskloon.j3d.Vector;
public class MirrorPdf implements FallbackPdf{
    public final Vector reflectDirection;
    public MirrorPdf(Vector reflectDirection){
        this.reflectDirection=reflectDirection;
    }
    @Override public Vector generate(){
        return reflectDirection;
    }
}