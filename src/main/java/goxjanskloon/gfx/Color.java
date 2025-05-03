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
        return new Color(red/scale,green/scale,blue/scale);
    }
    public Rgb toRgb(){
        var c=new Color(RANGE.clamp(red),RANGE.clamp(green),RANGE.clamp(blue)).scale(Rgb.MAX);
        return new Rgb((int)c.red,(int)c.green,(int)c.blue);
    }
    public boolean isValid(){
        return Double.isFinite(red)&&red>=0&&Double.isFinite(green)&&green>=0&&Double.isFinite(blue)&&blue>=0;
    }
}