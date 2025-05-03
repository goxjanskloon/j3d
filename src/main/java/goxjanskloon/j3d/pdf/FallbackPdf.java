package goxjanskloon.j3d.pdf;
import goxjanskloon.j3d.Vector;
public interface FallbackPdf extends Pdf{
    @Override default double valueOf(Vector direction){
        return 1;
    }
}