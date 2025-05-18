package goxjanskloon.gfx;
import goxjanskloon.utils.Interval;
public class Color{
    public final double red,green,blue;
    public static final Interval RANGE=new Interval(0,1);
    public static final Color WHITE=new Color(1,1,1),BLACK=new Color(0,0,0),RED=new Color(1,0,0),GREEN=new Color(0,1,0),BLUE=new Color(0,0,1),YELLOW=new Color(1,1,0),CYAN=new Color(0,1,1);
    public Color(double red,double green,double blue){
        this.red=red;
        this.green=green;
        this.blue=blue;
    }
    public Color mix(Color other){
        return new Color(red+other.red,green+other.green,blue+other.blue);
    }
    public Color scale(Color other){
        return new Color(red*other.red,green*other.green,blue*other.blue);
    }
    public Color scale(double scale){
        return new Color(red*scale,green*scale,blue*scale);
    }
    public Color div(double scale){
        return scale(1/scale);
    }
    public int toRgb(){
        Color c=new Color(RANGE.clamp(Math.sqrt(red)),RANGE.clamp(Math.sqrt(green)),RANGE.clamp(Math.sqrt(blue))).scale(255);
        return (((int)c.red)<<16)|(((int)c.green)<<8)|(int)c.blue;
    }
    public Color normalize(){
        return new Color(Double.isNaN(red)||Double.isInfinite(red)?0:red,Double.isNaN(green)||Double.isInfinite(green)?0:green,Double.isNaN(blue)||Double.isInfinite(blue)?0:blue);
    }
}