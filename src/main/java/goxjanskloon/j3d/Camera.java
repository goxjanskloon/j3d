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
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
public class Camera{
    private static final Logger logger=Logger.getLogger(Camera.class);
    static{
        PropertyConfigurator.configure(Camera.class.getResourceAsStream("log4j.properties"));
    }
    public final Hittable world,light;
    public final Ray ray;
    public final Vector upDir,rightDir;
    public final int width,height,maxDepth,threads,minSSP,maxSSP;
    public Color background;
    private final int halfWidth,halfHeight;
    public final double samplingThreshold;
    private final ExecutorService threadPool;
    public Camera(Hittable world,Hittable light,Ray ray,Vector upDir,Vector rightDir,int width,int height,int maxDepth,int minSSP,int maxSSP,double samplingThreshold,Color background,int threads){
        this.world=world;
        this.light=light;
        this.ray=ray;
        this.upDir=upDir;
        this.rightDir=rightDir;
        halfWidth=(this.width=width)>>1;
        halfHeight=(this.height=height)>>1;
        this.maxDepth=maxDepth;
        this.minSSP=minSSP;
        this.maxSSP=maxSSP;
        this.samplingThreshold=samplingThreshold;
        this.background=background;
        threadPool=(this.threads=threads)>1?new ThreadPoolExecutor(threads,threads,0,TimeUnit.SECONDS,new ArrayBlockingQueue<>(threads)):null;
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
        int n=0;
        for(;n<maxSSP;++n){
            Color c=render(new Ray(ray.origin,(ray.direction.add(upDir.mul((halfHeight-y+Interval.UNIT_RANGE.random()))).add(rightDir.mul(x-halfWidth+Interval.UNIT_RANGE.random()))).unit()),1).normalize();
            if(n>=minSSP){
                Color r=s.div(n);
                if(r.diff(c)<samplingThreshold)
                    return r;
            }
            s=s.mix(c);
        }
        return s.div(n);
    }
    private class Worker implements Runnable{
        private final BufferedImage img;
        private final Progress progress;
        public Worker(BufferedImage img,Progress progress){
            this.img=img;
            this.progress=progress;
        }
        @Override public void run(){
            while(true){
                int c=progress.nextColumn();
                if(c!=-1)
                    for(int i=0;i<width;++i)
                        img.setRGB(i,c,render(i,c).toRgb());
                else break;
            }
        }
    }
    private class Progress{
        private final AtomicInteger current=new AtomicInteger(-1);
        private final JProgressBar progressBar;
        public Progress(JProgressBar progressBar){
            this.progressBar=progressBar;
        }
        public int nextColumn(){
            int cur=current.incrementAndGet();
            if(cur>=height)
                return -1;
            progressBar.setValue(cur);
            progressBar.setString(String.format("%.2f%% %d/%d",cur*100.0/height,cur,height));
            return cur;
        }
    }
    public BufferedImage render(){
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        JFrame frame=new JFrame();
        frame.setTitle("Rendering on "+this);
        frame.setSize(400,100);
        JProgressBar progressBar=new JProgressBar(0,height);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.add(progressBar);
        frame.setVisible(true);
        Progress progress=new Progress(progressBar);
        if(threadPool!=null){
            for(int i=0;i<threads;++i)
                threadPool.execute(new Worker(image,progress));
            threadPool.shutdown();
            try{
                if(threadPool.awaitTermination(Integer.MAX_VALUE,TimeUnit.DAYS))
                    return image;
            }catch(InterruptedException e){
                logger.log(Level.ERROR,"Rendering interrupted.",e);
            }
            return null;
        }else{
            new Worker(image,progress).run();
            return image;
        }
    }
}