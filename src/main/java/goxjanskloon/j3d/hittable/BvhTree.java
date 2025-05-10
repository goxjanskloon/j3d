package goxjanskloon.j3d.hittable;
import goxjanskloon.j3d.Aabb;
import goxjanskloon.j3d.Ray;
import goxjanskloon.j3d.Vector;
import goxjanskloon.utils.Interval;
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
            var tb=Aabb.empty;
            for(Hittable obj: objects)
                tb=tb.unite(obj.getAabb());
            var d=(aabb=tb).getLongestAxis();
            objects.sort((a,b)->a.getAabb().compareTo(b.getAabb(),d));
            var mid=objects.size()>>1;
            left=new BvhTree(objects.subList(0,mid));
            right=new BvhTree(objects.subList(mid,objects.size()));
        }
    }
    @Override public Aabb getAabb(){
        return aabb;
    }
    @Override public Vector random(Vector origin){
        throw new UnsupportedOperationException();
    }
    @Override public double pdfValue(Ray ray){
        throw new UnsupportedOperationException();
    }
    @Override public HitRecord hit(Ray ray,Interval interval){
        var leftHit=left==null?null:left.hit(ray,interval);
        var rightHit=right==null?null:right.hit(ray,interval);
        if(leftHit==null) return rightHit;
        if(rightHit==null) return leftHit;
        return leftHit.distance<rightHit.distance?leftHit:rightHit;
    }
}