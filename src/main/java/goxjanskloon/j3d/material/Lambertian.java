package goxjanskloon.j3d.material;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.hittable.HitRecord;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.pdf.CosinePdf;
import goxjanskloon.j3d.texture.Texture;
import goxjanskloon.utils.MathHelper;
public class Lambertian implements Material{
    public final Texture texture;
    public Lambertian(Texture texture){
        this.texture=texture;
    }
    @Override public ScatterRecord scatter(Ray ray,HitRecord hit){
        return new ScatterRecord(new CosinePdf(hit.normal),texture.color(hit.u,hit.v,hit.point));
    }
    @Override public double scatteringPdf(HitRecord hit,Vector scattered){
        double a=hit.normal.dot(scattered);
        return a<0?0:a*MathHelper.IPI;
    }
}