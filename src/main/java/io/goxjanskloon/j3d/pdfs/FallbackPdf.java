package io.goxjanskloon.j3d.pdfs;
import io.goxjanskloon.j3d.Vector;
public interface FallbackPdf extends Pdf{
    @Override default double valueOf(Vector direction){
        return 1;
    }
}