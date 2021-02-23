public class Square extends Shape {

    public Square(double len) {
        super(len);
    }

    @Override
    public double area() {
        setArea(getLength() * getLength());
        return getArea();
    }
}
