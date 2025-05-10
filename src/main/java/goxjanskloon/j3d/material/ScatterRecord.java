package goxjanskloon.j3d.material;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.pdf.Pdf;
public class ScatterRecord{
    public final Pdf pdf;
    public final Color attenuation;
    public final Vector skipPdf;
    public ScatterRecord(Pdf pdf,Color attenuation){
        this(pdf,attenuation,null);
    }
    public ScatterRecord(Pdf pdf,Color attenuation,Vector skipPdf){
        this.pdf=pdf;
        this.attenuation=attenuation;
        this.skipPdf=skipPdf;
    }
}