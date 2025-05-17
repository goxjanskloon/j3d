package goxjanskloon.j3d.material;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.hittable.HitRecord;
import goxjanskloon.j3d.pdf.SpherePdf;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.texture.Texture;
import goxjanskloon.utils.MathHelper;
public class Isotropic implements Material{
    public final Texture texture;
    public Isotropic(Texture texture){
        this.texture=texture;
    }
    @Override public ScatterRecord scatter(Ray ray,HitRecord hit){
        return new ScatterRecord(SpherePdf.INSTANCE,texture.color(hit.u,hit.v,hit.point));
    }
    @Override public double scatteringPdf(HitRecord hit,Vector scattered){
        return MathHelper.I4PI;
    }
}