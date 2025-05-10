package goxjanskloon.j3d.texture;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Vector;
public class SolidTexture implements Texture{
    public final Color color;
    public SolidTexture(Color color){
        this.color=color;
    }
    @Override public Color color(double u,double v,Vector point){
        return color;
    }
}