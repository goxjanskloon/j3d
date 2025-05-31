package goxjanskloon.utils;
import net.jafama.FastMath;
public class Interval{
    public static final Interval EMPTY=new Interval(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY),UNIVERSE=new Interval(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY),UNIT_RANGE=new Interval(-0.5,0.5);
    public final double min,max;
    public Interval(double min,double max){
        this.min=min;
        this.max=max;
    }
    public double length(){
        return max-min;
    }
    public boolean isEmpty(){
        return max<=min;
    }
    public boolean contains(double x){
        return x>=min&&x<=max;
    }
    public double clamp(double x){
        return x<min?min:FastMath.min(x,max);
    }
    public Interval intersect(Interval other){
        return new Interval(FastMath.max(min,other.min),FastMath.min(max,other.max));
    }
    public Interval unite(Interval other){
        return new Interval(FastMath.min(min,other.min),FastMath.max(max,other.max));
    }
    public double random(){
        return MathHelper.nextDouble(this);
    }
    public Interval move(double a){
        return new Interval(min+a,max+a);
    }
    public Interval padToMinimum(){
        if(max-min>1e-3)
            return this;
        var c=(min+max)/2;
        return new Interval(c-5e-4,c+5e-4);
    }
}