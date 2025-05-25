package goxjanskloon.j3d.material;
import goxjanskloon.j3d.Onb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.j3d.hittable.HitRecord;
import goxjanskloon.j3d.pdf.CosinePdf;
import goxjanskloon.j3d.texture.Texture;
import goxjanskloon.utils.MathHelper;
import java.awt.image.BufferedImage;
public class LambertianWithNormalMap implements Material{
    public final Texture texture;
    public final Vector[][] normals;
    public LambertianWithNormalMap(Texture texture,BufferedImage normalMap){
        this(texture,Texture.readNormalMap(normalMap));
    }
    public LambertianWithNormalMap(Texture texture,Vector[][] normals){
        this.texture=texture;
        this.normals=normals;
    }
    @Override public ScatterRecord scatter(Ray ray,HitRecord hit){
        return new ScatterRecord(new CosinePdf(new Onb(hit.normal).transform(normals[(int)(hit.u*normals.length)][(int)(hit.v*normals[0].length)])),texture.color(hit.u,hit.v,hit.point));
    }
    @Override public double scatteringPdf(HitRecord hit,Vector scattered){
        double a=new Onb(hit.normal).transform(normals[(int)(hit.u*normals.length)][(int)(hit.v*normals[0].length)]).dot(scattered);
        return a<0?0:a*MathHelper.IPI;
    }
}