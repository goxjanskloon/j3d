package io.goxjanskloon.j3d.pdfs;
import io.goxjanskloon.j3d.Vector;
public class MirrorPdf implements FallbackPdf{
    public final Vector reflectDirection;
    public MirrorPdf(Vector reflectDirection){
        this.reflectDirection=reflectDirection;
    }
    @Override public Vector generate(){
        return reflectDirection;
    }
}