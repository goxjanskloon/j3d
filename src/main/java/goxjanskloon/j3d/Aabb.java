package goxjanskloon.j3d;
import goxjanskloon.utils.Interval;
import net.jafama.FastMath;
public class Aabb{
    public final Interval x,y,z;
    public static final Aabb empty=new Aabb(Interval.EMPTY,Interval.EMPTY,Interval.EMPTY);
    public Aabb(Interval x,Interval y,Interval z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public Aabb(Vector a,Vector b){
        x=new Interval(FastMath.min(a.x,b.x),FastMath.max(a.x,b.x));
        y=new Interval(FastMath.min(a.y,b.y),FastMath.max(a.y,b.y));
        z=new Interval(FastMath.min(a.z,b.z),FastMath.max(a.z,b.z));
    }
    public Interval get(Dimension d){
        return switch(d){
            case X->x;
            case Y->y;
            case Z->z;
        };
    }
    public Dimension getLongestAxis(){
        var xl=x.length();
        var yl=y.length();
        var zl=z.length();
        if(xl>yl&&xl>zl)
            return Dimension.X;
        if(yl>xl&&yl>zl)
            return Dimension.Y;
        return Dimension.Z;
    }
    public boolean hit(Ray ray,Interval interval){
        for(int i=0;i<=2;++i){
            var d=Dimension.valueOf(i);
            if((interval=interval.intersect(new Interval((get(d).min-ray.origin.get(d))/ray.direction.get(d),(get(d).max-ray.origin.get(d))/ray.direction.get(d)))).isEmpty())
                return false;
        }
        return true;
    }
    public Aabb unite(Aabb other){
        return new Aabb(x.unite(other.x),y.unite(other.y),z.unite(other.z));
    }
    public int compareTo(Aabb other,Dimension d){
        return Double.compare(get(d).min,other.get(d).min);
    }
    public Aabb move(Vector motion){
        return new Aabb(x.move(motion.x),y.move(motion.y),z.move(motion.z));
    }
    public Aabb padToMinimum(){
        return new Aabb(x.padToMinimum(),y.padToMinimum(),z.padToMinimum());
    }
}