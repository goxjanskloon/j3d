package io.goxjanskloon.j3d.pdfs;
import io.goxjanskloon.j3d.Vector;
import io.goxjanskloon.utils.Randoms;
public class DielectricPdf implements FallbackPdf{
    public final Vector direction;
    public DielectricPdf(Vector in,Vector normal,double refractionIndex){
        if(in.dot(normal)>0)
            refractionIndex=1/refractionIndex;
        var cos=Math.min(-in.dot(normal),1);
        var sin=Math.sqrt(1-cos*cos);
        var r=1-refractionIndex*refractionIndex;
        r*=r;
        direction=refractionIndex*sin>1||r+(1-r)*Math.pow(1-cos,5)>Randoms.nextDouble()?
                Vector.reflect(in,normal)
                :Vector.refract(in,normal,refractionIndex);
    }
    @Override public Vector generate(){
        return direction;
    }
}