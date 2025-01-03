package io.goxjanskloon.j3d;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import org.apache.log4j.*;
import io.goxjanskloon.graphics.*;
import io.goxjanskloon.utils.*;
public class Camera{
    public static final Interval HIT_RANGE=new Interval(1e-5,Double.POSITIVE_INFINITY);
    private static final Logger logger=Logger.getLogger(Camera.class);
    static{
        PropertyConfigurator.configure(Camera.class.getClassLoader().getResourceAsStream("log4j.properties"));
    }
    public final Hittable world;
    public final Ray ray;
    public final Vector upDir,rightDir;
    public final int width,height,maxDepth,samplesPerPixel,dWidth;
    public Color backgroundLight;
    private final int halfWidth,halfHeight;
    private final ExecutorService threadPool;
    public Camera(Hittable world,Ray ray,Vector upDir,Vector rightDir,int width,int height,int maxDepth,int samplesPerPixel,Color backgroundLight,int dWidth){
        this.world=world;
        this.ray=ray;
        this.upDir=upDir;
        this.rightDir=rightDir;
        halfWidth=(this.width=width)>>1;
        halfHeight=(this.height=height)>>1;
        this.maxDepth=maxDepth;
        this.samplesPerPixel=samplesPerPixel;
        this.backgroundLight=backgroundLight;
        this.dWidth=dWidth;
        final int threadNumber=width/dWidth+5;
        threadPool=new ThreadPoolExecutor(threadNumber,threadNumber,Long.MAX_VALUE,TimeUnit.DAYS,new ArrayBlockingQueue<>(threadNumber));
    }
    public Color render(Ray ray,int depth){
        Hittable.HitRecord record=world.hit(ray,HIT_RANGE);
        if(record==null){
            if(depth==1) return Color.BLACK;
            return backgroundLight;
        }
        if(depth==maxDepth) return record.color().scale(record.brightness());
        Vector reflectDir=ray.direction.sub(record.normal().mul(ray.direction.dot(record.normal())*2)).unit();
        Vector fuzzedReflectDir=record.brdf().generate(record.normal(),reflectDir);
        return fuzzedReflectDir==null?record.color().scale(record.brightness()):render(new Ray(record.point(),fuzzedReflectDir),depth+1).scale(record.brdf().getValue(reflectDir,fuzzedReflectDir)).scale(record.color()).mix(record.color().scale(record.brightness()));
    }
    public Color render(int x,int y){
        Color s=Color.BLACK;
        for(int i=0;i<samplesPerPixel;++i){
            final Color sample=render(new Ray(ray.origin,(ray.direction.add(upDir.mul((halfHeight-y+Interval.UNIT_RANGE.random()))).add(rightDir.mul(x-halfWidth+Interval.UNIT_RANGE.random()))).unit()),1);
            if(sample.isValid())
                s=s.mix(sample);
        }
        return s.div(samplesPerPixel);
    }
    private class renderRunnable implements Runnable{
        private final int l,r;
        private final Rgb[][] p;
        private final AtomicInteger total;
        public renderRunnable(int i,Rgb[][] pixels,AtomicInteger total){
            r=Math.min((l=i)+dWidth,width);
            p=pixels;
            this.total=total;
        }
        @Override public void run(){
            for(int i=0;i<height;++i)
                for(int j=l;j<r;++j)
                    p[i][j]=render(j,i).toRgb();
            final int remain=total.decrementAndGet();
            logger.log(Level.INFO,"Thread "+l/dWidth+" finished,"+remain+" thread"+(remain>1?"s":"")+" left.");
        }
    }
    public Image render(){
        Image image=new Image(new Rgb[height][width]);
        AtomicInteger total=new AtomicInteger();
        for(int i=0;i<width;i+=dWidth){
            total.incrementAndGet();
            threadPool.execute(new renderRunnable(i,image.pixels,total));
            logger.log(Level.INFO,"Thread "+i/dWidth+" submitted.");
        }
        try{
            threadPool.shutdown();
            if(threadPool.awaitTermination(Integer.MAX_VALUE,TimeUnit.DAYS)){
                logger.log(Level.INFO,"Rendering finished.");
                return image;
            }
        }catch(InterruptedException e){
            logger.log(Level.ERROR,"Rendering interrupted.",e);
        }
        return null;
    }
}