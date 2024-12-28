package io.goxjanskloon.graphics;
public record Rgb(int red,int green,int blue){
    public final static int MAX=255;
    @Override public String toString(){
        return red+" "+green+" "+blue;
    }
}