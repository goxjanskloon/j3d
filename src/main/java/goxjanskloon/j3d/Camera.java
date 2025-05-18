package goxjanskloon.j3d;
import java.awt.image.BufferedImage;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import goxjanskloon.gfx.Color;
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
    public final int width,height,maxDepth,samplesPerPixel,threads;
    public Color background;
    private final int halfWidth,halfHeight,dHeight;
    private final double iSPP;
    private final ExecutorService threadPool;
    public final boolean log;
    public Camera(Hittable world,Hittable light,Ray ray,Vector upDir,Vector rightDir,int width,int height,int maxDepth,int samplesPerPixel,Color background,int threads,boolean log){
        this.world=world;
        this.light=light;
        this.ray=ray;
        this.upDir=upDir;
        this.rightDir=rightDir;
        halfWidth=(this.width=width)>>1;
        halfHeight=(this.height=height)>>1;
        this.maxDepth=maxDepth;
        iSPP=1.0/(this.samplesPerPixel=samplesPerPixel);
        this.background=background;
        dHeight=height/(this.threads=threads);
        threadPool=new ThreadPoolExecutor(threads,threads,Long.MAX_VALUE,TimeUnit.DAYS,new ArrayBlockingQueue<>(threads));
        this.log=log;
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
        Color s=Color.BLACK;
        for(int i=0;i<samplesPerPixel;++i)
            s=s.mix(render(new Ray(ray.origin,(ray.direction.add(upDir.mul((halfHeight-y+Interval.UNIT_RANGE.random()))).add(rightDir.mul(x-halfWidth+Interval.UNIT_RANGE.random()))).unit()),1).normalize().scale(iSPP));
        return s;
    }
    private class renderRunnable implements Runnable{
        private final int t;
        private final BufferedImage img;
        private final ProgressIndicator progress;
        public renderRunnable(int t,BufferedImage img,ProgressIndicator progress){
            this.t=t;
            this.img=img;
            this.progress=progress;
        }
        @Override public void run(){
            for(int i=dHeight*t,l=Math.min(i+dHeight,height);i<l;progress.increment(),++i)
                for(int j=0;j<width;++j)
                    img.setRGB(j,i,render(j,i).toRgb());
        }
    }
    private static class ProgressIndicator{
        private final AtomicInteger current=new AtomicInteger(0);
        private final int goal;
        public ProgressIndicator(int goal){
            this.goal=goal;
        }
        public void increment(){
            logger.info(String.format("Progress: %d/%d",current.incrementAndGet(),goal));
        }
    }
    private class renderRunnableNoLog implements Runnable{
        private final int t;
        private final BufferedImage img;
        public renderRunnableNoLog(int t,BufferedImage img){
            this.t=t;
            this.img=img;
        }
        @Override public void run(){
            for(int i=dHeight*t,l=Math.min(i+dHeight,height);i<l;++i)
                for(int j=0;j<width;++j)
                    img.setRGB(j,i,render(j,i).toRgb());
        }
    }
    public BufferedImage render(){
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        if(log){
            ProgressIndicator progress=new ProgressIndicator(width);
            for(int t=0;t<threads;++t)
                threadPool.execute(new renderRunnable(t,image,progress));
        }else for(int t=0;t<threads;++t)
            threadPool.execute(new renderRunnableNoLog(t,image));
        threadPool.shutdown();
        try{
            if(threadPool.awaitTermination(Integer.MAX_VALUE,TimeUnit.DAYS))
                return image;
        }catch(InterruptedException e){
            logger.log(Level.ERROR,"Rendering interrupted.",e);
        }
        return null;
    }
}