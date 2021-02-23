public abstract class Shape implements Calculable {
    private double area;
    private double length;

    public Shape(double len) {
        if (len >= 0) {
            length = len;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getLength() {
        return length;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double ar) {
        area = ar;
    }
}