package goxjanskloon.j3d;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
    public final int width,height,maxDepth,samplesPerPixel,threads;
    public Color background;
    private final int halfWidth,halfHeight,dHeight;
    private final double iSPP;
    private final ExecutorService threadPool;
    public Camera(Hittable world,Hittable light,Ray ray,Vector upDir,Vector rightDir,int width,int height,int maxDepth,int samplesPerPixel,Color background,int threads){
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
        private final Rgb[][] p;
        private final ProgressIndicator g;
        public renderRunnable(int t,Rgb[][] pixels,ProgressIndicator progress){
            this.t=t;
            p=pixels;
            g=progress;
        }
        @Override public void run(){
            for(int i=dHeight*t,l=Math.min(i+dHeight,height);i<l;g.increment(),++i)
                for(int j=0;j<width;++j)
                    p[i][j]=render(j,i).toRgb();
        }
    }
    private static class ProgressIndicator{
        private int current=0;
        private final int goal;
        public ProgressIndicator(int goal){
            this.goal=goal;
        }
        synchronized public void increment(){
            logger.info(String.format("Progress: %d/%d",++current,goal));
        }
    }
    public Image render(){
        Image image=new Image(new Rgb[height][width]);
        ProgressIndicator progress=new ProgressIndicator(width);
        for(int t=0;t<threads;++t)
            threadPool.execute(new renderRunnable(t,image.pixels,progress));
        try{
            threadPool.shutdown();
            if(threadPool.awaitTermination(Integer.MAX_VALUE,TimeUnit.DAYS))
                return image;
        }catch(InterruptedException e){
            logger.log(Level.ERROR,"Rendering interrupted.",e);
        }
        return null;
    }
}