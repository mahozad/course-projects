import java.io.*;

public class SubBigData implements Runnable {

    private static double totalSum = 0;
    private static String filePath;
    private static int flag = 0;
    private static int max;
    private int fileBeg;
    private int fileEnd;

    public SubBigData(int beg, int end) {
        fileBeg = beg;
        fileEnd = end;
    }

    @Override
    public void run() {
        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "r");
            file.seek(fileBeg * 4);
            int max = file.readInt();
            file.seek(fileBeg * 4);
            long sum = 0;
            int num;
            int totalNumbers = fileEnd - fileBeg + 1;
            for (int i = 0; i < totalNumbers; i++) {
                num = file.readInt();
                sum += num;
                if (num > max) {
                    max = num;
                }
            }
            file.close();
            double average = (double) sum / totalNumbers;
            updateAverageAndMax(average, max);
        } catch (IOException ignored) {
        }
    }

    public static void setMaxAndAddress(int initialMax, String path) {
        max = initialMax;
        filePath = path;
    }

    private static synchronized void updateAverageAndMax(double average, int subMax) {
        totalSum += average;
        if (subMax > max) {
            max = subMax;
        }
        flag++;
    }

    public static int getMax() {
        return max;
    }

    public static double getSum() {
        return totalSum;
    }

    public static synchronized int getFlag() {
        return flag;
    }
}
