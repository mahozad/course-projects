public class Rectangle extends Shape {
    private double width;

    public Rectangle(double len, double wi) {
        super(len);
        setWidth(wi);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double wi) {
        if (wi >= 0) {
            width = wi;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public double area() {
        setArea(getLength() * getWidth());
        return getArea();
    }
}
