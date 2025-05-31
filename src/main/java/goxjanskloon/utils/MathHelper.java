package goxjanskloon.utils;
import net.jafama.FastMath;
import java.util.concurrent.ThreadLocalRandom;
public interface MathHelper{
    double C2PI=2*FastMath.PI,C4PI=4*FastMath.PI,IPI=1/FastMath.PI,I2PI=1/C2PI,I4PI=1/C4PI;
    static double nextDouble(){
        return ThreadLocalRandom.current().nextDouble();
    }
    static double nextDouble(double min,double max){
        return ThreadLocalRandom.current().nextDouble(min,max);
    }
    static double nextDouble(Interval interval){
        return nextDouble(interval.min,interval.max);
    }
}