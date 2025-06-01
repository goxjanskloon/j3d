package goxjanskloon.utils;
import java.util.concurrent.ThreadLocalRandom;
public interface MathHelper{
    double C2PI=2*Math.PI,C4PI=4*Math.PI,IPI=1/Math.PI,I2PI=1/C2PI,I4PI=1/C4PI;
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