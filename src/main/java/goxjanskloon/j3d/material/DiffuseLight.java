package goxjanskloon.j3d.material;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.hittable.HitRecord;
import goxjanskloon.j3d.texture.Texture;
public class DiffuseLight implements Material{
    public final Texture texture;
    public DiffuseLight(Texture texture){
        this.texture=texture;
    }
    @Override public Color emit(Ray ray,HitRecord hit){
        return texture.color(hit.u,hit.v,hit.point);
    }
}