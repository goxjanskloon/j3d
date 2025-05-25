package goxjanskloon.j3d.texture;
import goxjanskloon.gfx.Color;
import goxjanskloon.j3d.Vector;
import java.awt.image.BufferedImage;
public class ImageTexture implements Texture{
    public final Color[][] colors;
    public ImageTexture(BufferedImage image){
        int w=image.getWidth(),h=image.getHeight();
        colors=new Color[w][h];
        for(int i=0;i<w;++i)
            for(int j=0;j<h;++j)
                colors[i][j]=new Color(image.getRGB(i,j));
    }
    @Override public Color color(double u,double v,Vector point){
        return colors[(int)(colors.length*u)][(int)(colors[0].length*v)];
    }
}