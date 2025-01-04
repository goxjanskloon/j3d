package io.goxjanskloon.j3d;
public class HittablePdf implements Pdf{
    public final Hittable object;
    public final Vector origin;
    public HittablePdf(Hittable object,Vector origin){
        this.object=object;
        this.origin=origin;
    }
    @Override public double valueOf(Vector direction){
        return object.pdfValue(new Ray(origin,direction));
    }
    @Override public Vector generate(){
        return object.randomOnSurface().sub(origin).unit();
    }
}