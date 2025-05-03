package goxjanskloon.utils;
import java.util.concurrent.ThreadLocalRandom;
public class Randoms{
    public static double nextDouble(){
        return ThreadLocalRandom.current().nextDouble();
    }
    public static double nextDouble(double min,double max){
        return ThreadLocalRandom.current().nextDouble(min,max);
    }
    public static double nextDouble(Interval interval){
        return nextDouble(interval.min,interval.max);
    }
}