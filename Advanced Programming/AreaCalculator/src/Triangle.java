public class Triangle extends Shape {
    private double height;

    public Triangle(double len, double he) {
        super(len);
        setHeight(he);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double he) {
        if (he >= 0) {
            height = he;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public double area() {
        setArea(getLength() * getHeight() / 2);
        return getArea();
    }
}
