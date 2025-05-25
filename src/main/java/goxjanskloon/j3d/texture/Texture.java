package goxjanskloon.j3d.texture;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Vector;
import java.awt.image.BufferedImage;
public interface Texture{
    Color color(double u,double v,Vector point);
    static Vector[][] readNormalMap(BufferedImage normalMap){
        int w=normalMap.getWidth(),h=normalMap.getHeight();
        Vector[][] normals=new Vector[w][h];
        for(int i=0;i<w;++i)
            for(int j=0;j<h;++j)
                normals[i][j]=new Vector(normalMap.getRGB(i,j));
        return normals;
    }
}