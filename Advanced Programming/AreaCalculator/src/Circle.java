public class Circle extends Shape {

    public Circle(double len) {
        super(len);
    }

    @Override
    public double area() {
        setArea(getLength() * getLength() * Math.PI);
        return getArea();
    }
}
