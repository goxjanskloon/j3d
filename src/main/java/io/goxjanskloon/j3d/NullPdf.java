package io.goxjanskloon.j3d;
public class NullPdf implements Pdf{
    @Override public double valueOf(Vector direction){
        return 0;
    }
    @Override public Vector generate(){
        return null;
    }
}