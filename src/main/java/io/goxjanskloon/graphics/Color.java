package io.goxjanskloon.graphics;
import io.goxjanskloon.utils.Interval;
public record Color(double red,double green,double blue){
    public static final Interval RANGE=new Interval(0,1);
    public static final Color WHITE=new Color(1,1,1);
    public static final Color BLACK=new Color(0,0,0);
    public static final Color RED=new Color(1,0,0);
    public static final Color GREEN=new Color(0,1,0);
    public static final Color BLUE=new Color(0,0,1);
    public static final Color YELLOW=new Color(1,1,0);
    public static final Color CYAN=new Color(0,1,1);
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
    public Color gamma(){
        return new Color(red<0?0:Math.sqrt(red),green<0?0:Math.sqrt(green),blue<0?0:Math.sqrt(blue));
    }
    public Rgb toRgb(){
        Color c=gamma();
        c=new Color(RANGE.clamp(c.red),RANGE.clamp(c.green),RANGE.clamp(c.blue));
        c=c.scale(Rgb.MAX);
        return new Rgb((int)c.red,(int)c.green,(int)c.blue);
    }
    public boolean isValid(){
        return Double.isFinite(red)&&Double.isFinite(green)&&Double.isFinite(blue);
    }
}