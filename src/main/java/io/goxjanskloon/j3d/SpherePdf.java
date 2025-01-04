package io.goxjanskloon.j3d;
public class SpherePdf implements Pdf{
    @Override public double valueOf(Vector direction){
        return 1/(4*Math.PI);
    }
    @Override public Vector generate(){
        return Vector.randomUnit();
    }
}