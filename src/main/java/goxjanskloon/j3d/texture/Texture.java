package goxjanskloon.j3d.texture;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Vector;
public interface Texture{
    Color color(double u,double v,Vector point);
}