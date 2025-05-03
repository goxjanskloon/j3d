package goxjanskloon.j3d;
public enum Dimension{
    X,Y,Z;
    public static Dimension valueOf(int value){
        return switch(value){
            case 0->X;
            case 1->Y;
            case 2->Z;
            default->throw new IllegalArgumentException();
        };
    }
}