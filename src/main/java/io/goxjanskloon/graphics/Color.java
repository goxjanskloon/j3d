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
    public Rgb toRgb(){
        final Color c=new Color(RANGE.clamp(red),RANGE.clamp(green),RANGE.clamp(blue)).scale(Rgb.MAX);
        return new Rgb((int)c.red,(int)c.green,(int)c.blue);
    }
    public boolean isValid(){
        return Double.isFinite(red)&&red>=0&&Double.isFinite(green)&&green>=0&&Double.isFinite(blue)&&blue>=0;
    }
}