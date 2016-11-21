package ag;

public class Cromossomo {
    double individuoX, individuoY, individuoZ;
    
    public Cromossomo(double x, double y, double z) {
        this.individuoX = x;
        this.individuoY = y;
        this.individuoZ = z;
    }
    
    public Cromossomo() {

    }
    
    @Override
    public String toString() {
        return "X: " + this.individuoX + " - " + "Y: " + this.individuoY + " - "
                + "Z: " + this.individuoZ + " Avaliação: " + (individuoX+individuoY+individuoZ) + " ";
    }

    public void setIndividuoX(int individuoX) {
        this.individuoX = individuoX;
    }

    public void setIndividuoY(int individuoY) {
        this.individuoY = individuoY;
    }

    public void setIndividuoZ(int individuoZ) {
        this.individuoZ = individuoZ;
    }

    public double getIndividuoX() {
        return individuoX;
    }

    public double getIndividuoY() {
        return individuoY;
    }

    public double getIndividuoZ() {
        return individuoZ;
    }
    
    
    
    
}
