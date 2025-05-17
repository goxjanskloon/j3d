package goxjanskloon.j3d;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import goxjanskloon.gfx.Color;
import goxjanskloon.gfx.Image;
import goxjanskloon.gfx.Rgb;
import goxjanskloon.j3d.hittable.HitRecord;
import goxjanskloon.j3d.hittable.Hittable;
import goxjanskloon.j3d.material.ScatterRecord;
import goxjanskloon.j3d.pdf.HittablePdf;
import goxjanskloon.j3d.pdf.MixturePdf;
import goxjanskloon.j3d.pdf.Pdf;
import goxjanskloon.utils.Interval;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
public class Camera{
    private static final Logger logger=Logger.getLogger(Camera.class);
    static{
        PropertyConfigurator.configure(Camera.class.getResourceAsStream("log4j.properties"));
    }
    public final Hittable world,light;
    public final Ray ray;
    public final Vector upDir,rightDir;
    public final int width,height,maxDepth,samplesPerPixel,dWidth;
    public Color background;
    private final int halfWidth,halfHeight;
    private final ExecutorService threadPool;
    public Camera(Hittable world,Hittable light,Ray ray,Vector upDir,Vector rightDir,int width,int height,int maxDepth,int samplesPerPixel,Color background,int dWidth){
        this.world=world;
        this.light=light;
        this.ray=ray;
        this.upDir=upDir;
        this.rightDir=rightDir;
        halfWidth=(this.width=width)>>1;
        halfHeight=(this.height=height)>>1;
        this.maxDepth=maxDepth;
        this.samplesPerPixel=samplesPerPixel;
        this.background=background;
        this.dWidth=dWidth;
        var threadNumber=width/dWidth+5;
        threadPool=new ThreadPoolExecutor(threadNumber,threadNumber,Long.MAX_VALUE,TimeUnit.DAYS,new ArrayBlockingQueue<>(threadNumber));
    }
    public Color render(Ray ray,int depth){
        if(depth>maxDepth)
            return Color.BLACK;
        HitRecord hit=world.hit(ray,Hittable.HIT_RANGE);
        if(hit==null)
            return background;
        Color emit=hit.material.emit(ray,hit);
        ScatterRecord scatter=hit.material.scatter(ray,hit);
        if(scatter==null)
            return emit;
        if(scatter.skipPdf!=null)
            return render(new Ray(hit.point,scatter.skipPdf),depth+1).scale(scatter.attenuation);
        Pdf pdf=new MixturePdf(new HittablePdf(light,hit.point),scatter.pdf);
        Vector scattered=pdf.generate();
        return render(new Ray(hit.point,scattered),depth+1).scale(scatter.attenuation).scale(hit.material.scatteringPdf(hit,scattered)/pdf.valueOf(scattered));
    }
    public Color render(int x,int y){
        var s=Color.BLACK;
        for(int i=0;i<samplesPerPixel;++i)
            s=s.mix(render(new Ray(ray.origin,(ray.direction.add(upDir.mul((halfHeight-y+Interval.UNIT_RANGE.random()))).add(rightDir.mul(x-halfWidth+Interval.UNIT_RANGE.random()))).unit()),1).normalize());
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
            for(int i=0;i<height;++i){
                for(int j=l;j<r;++j)
                    p[i][j]=render(j,i).toRgb();
                if((int)((i+1)*100.0/height)>(int)(i*100.0/height))
                    logger.info((int)(i*100.0/height)+"% finished.");
            }
            var remain=total.decrementAndGet();
            logger.info("Thread "+l/dWidth+" finished,"+remain+" thread"+(remain>1?"s":"")+" left.");
        }
    }
    public Image render(){
        var image=new Image(new Rgb[height][width]);
        var total=new AtomicInteger();
        for(int i=0;i<width;i+=dWidth){
            total.incrementAndGet();
            threadPool.execute(new renderRunnable(i,image.pixels,total));
            logger.info("Thread "+i/dWidth+" submitted.");
        }
        try{
            threadPool.shutdown();
            if(threadPool.awaitTermination(Integer.MAX_VALUE,TimeUnit.DAYS)){
                logger.info("Rendering finished.");
                return image;
            }
        }catch(InterruptedException e){
            logger.log(Level.ERROR,"Rendering interrupted.",e);
        }
        return null;
    }
}