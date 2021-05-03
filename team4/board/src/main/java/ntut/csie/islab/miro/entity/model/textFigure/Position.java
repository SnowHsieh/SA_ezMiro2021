package ntut.csie.islab.miro.entity.model.textFigure;

public class Position {
    private double x ;
    private double y ;
    private double z ;

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
