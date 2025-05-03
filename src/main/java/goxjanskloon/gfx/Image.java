package goxjanskloon.gfx;
import java.io.IOException;
import java.io.Writer;
public class Image{
    public final Rgb[][] pixels;
    public Image(Rgb[][] pixels){
        this.pixels=pixels;
    }
    public void output(Writer writer)throws IOException{
        writer.write("P3\n"+pixels[0].length+" "+pixels.length+"\n255\n");
        for(Rgb[] line:pixels)
            for(Rgb pixel:line)
                writer.write(pixel+" ");
    }
}