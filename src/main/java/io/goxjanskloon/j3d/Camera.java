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
    public final Hittable world,light;
    public final Ray ray;
    public final Vector upDir,rightDir;
    public final int width,height,maxDepth,samplesPerPixel,dWidth;
    public Color backgroundLight;
    private final int halfWidth,halfHeight;
    private final ExecutorService threadPool;
    public Camera(Hittable world,Hittable light,Ray ray,Vector upDir,Vector rightDir,int width,int height,int maxDepth,int samplesPerPixel,Color backgroundLight,int dWidth){
        this.world=world;
        this.light=light;
        this.ray=ray;
        this.upDir=upDir;
        this.rightDir=rightDir;
        halfWidth=(this.width=width)>>1;
        halfHeight=(this.height=height)>>1;
        this.maxDepth=maxDepth;
        this.samplesPerPixel=samplesPerPixel;
        this.backgroundLight=backgroundLight;
        this.dWidth=dWidth;
        var threadNumber=width/dWidth+5;
        threadPool=new ThreadPoolExecutor(threadNumber,threadNumber,Long.MAX_VALUE,TimeUnit.DAYS,new ArrayBlockingQueue<>(threadNumber));
    }
    public Color render(Ray ray,int depth){
        var record=world.hit(ray,HIT_RANGE);
        if(record==null){
            if(depth==1)
                return Color.BLACK;
            return backgroundLight;
        }
        Color emitted=record.color.scale(record.brightness);
        if(depth==maxDepth)
            return emitted;
        var scatteringPdf=record.material.getPdf(record.normal);
        if(scatteringPdf==null)
            return emitted;
        var pdf=new MixturePdf(scatteringPdf,new HittablePdf(light,record.point));
        var reflectDirection=pdf.generate();
        if(reflectDirection==null)
            return emitted;
        var pdfValue=pdf.valueOf(reflectDirection);
        if(pdfValue==0)
            return emitted;
        return render(new Ray(record.point,reflectDirection),depth+1).scale(scatteringPdf.valueOf(reflectDirection)/pdf.valueOf(reflectDirection)).scale(record.color).mix(emitted);
    }
    public Color render(int x,int y){
        var s=Color.BLACK;
        for(int i=0;i<samplesPerPixel;++i){
            var sample=render(new Ray(ray.origin,(ray.direction.add(upDir.mul((halfHeight-y+Interval.UNIT_RANGE.random()))).add(rightDir.mul(x-halfWidth+Interval.UNIT_RANGE.random()))).unit()),1);
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
            var remain=total.decrementAndGet();
            logger.log(Level.INFO,"Thread "+l/dWidth+" finished,"+remain+" thread"+(remain>1?"s":"")+" left.");
        }
    }
    public Image render(){
        var image=new Image(new Rgb[height][width]);
        var total=new AtomicInteger();
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