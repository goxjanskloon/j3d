package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Dimension;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.utils.Interval;
import goxjanskloon.utils.MathHelper;
import java.util.List;
public class BvhTree implements Hittable{
    public final Hittable left,right;
    public final Aabb aabb;
    public BvhTree(List<Hittable> objects){
        if(objects.isEmpty()){
            left=right=null;
            aabb=Aabb.empty;
        }
        else if(objects.size()==1){
            aabb=(left=objects.getFirst()).getAabb();
            right=null;
        }
        else if(objects.size()==2){
            aabb=(left=objects.getFirst()).getAabb().unite((right=objects.getLast()).getAabb());
        }else{
            Aabb t=Aabb.empty;
            for(Hittable o:objects)
                t=t.unite(o.getAabb());
            Dimension d=(aabb=t).getLongestAxis();
            objects.sort((a,b)->a.getAabb().compareTo(b.getAabb(),d));
            int mid=objects.size()>>1;
            left=new BvhTree(objects.subList(0,mid));
            right=new BvhTree(objects.subList(mid,objects.size()));
        }
    }
    @Override public Aabb getAabb(){
        return aabb;
    }
    @Override public Vector random(Vector origin){
        if(left==null)
            return right.random(origin);
        if(right==null)
            return left.random(origin);
        return MathHelper.nextBoolean()?left.random(origin):right.random(origin);
    }
    @Override public double pdfValue(Ray ray){
        if(left==null)
            return right.pdfValue(ray);
        if(right==null)
            return left.pdfValue(ray);
        return (left.pdfValue(ray)+right.pdfValue(ray))/2;
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        if(left!=null&&right!=null&&!aabb.hit(ray,interval))
            return null;
        HitRecord leftHit=left==null?null:left.hit(ray,interval),rightHit=right==null?null:right.hit(ray,interval);
        if(leftHit==null)
            return rightHit;
        if(rightHit==null)
            return leftHit;
        return leftHit.distance<rightHit.distance?leftHit:rightHit;
    }
}