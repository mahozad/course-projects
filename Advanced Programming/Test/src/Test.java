import java.util.Random;

public class Test {
    public static void main(String args[]) {
        Random random = new Random();
        int ar[];
        ar = new int[10];
        ar = null;
        double sum = 0;
        for (long i = 0; i < 100; i++) {
            double Maaref = 13.5 + random.nextDouble() * 6.5;
            double Shiveh = 18.1 + random.nextDouble() * 1.9;
            double Memari = 18.75 + random.nextDouble() * 1.25;
            sum += ((Maaref * 2) + (Shiveh * 2) + (Memari * 3) + 119.6) / 14;
        }
        System.out.println("salam".length());
        System.out.printf("\nAverage: %.2f\n", sum / 100);
    }
}