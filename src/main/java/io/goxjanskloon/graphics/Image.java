package io.goxjanskloon.graphics;
import java.io.*;
public record Image(Rgb[][] pixels){
    public void output(Writer writer)throws IOException{
        writer.write("P3\n"+pixels[0].length+" "+pixels.length+"\n255\n");
        for(Rgb[] line:pixels)
            for(Rgb pixel:line)
                writer.write(pixel+" ");
    }
}