import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class SubBigData implements Runnable {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static int threadsDone = 0;
    private static double average = 0;
    private static String filePath;
    private static int flag = 0;
    private static int max;
    private int fileBeg;
    private int fileEnd;

    public SubBigData(int beg, int end) {
        fileBeg = beg;
        fileEnd = end;
    }

    public static void setMaxAndAddress(int initialMax, String path) {
        max = initialMax;
        filePath = path;
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
            setMax(max);
            manipulateAverage(average);
        } catch (Exception ignored) {
        }
    }

    private static void manipulateAverage(double subAverage) throws Exception {
        if (subAverage < 1000) {
            lock.readLock().lock();
            System.out.printf("\nCurrent average: %.2f", threadsDone == 0 ? average : average /
                    threadsDone);
            incrementFlag();
            lock.readLock().unlock();
        } else {
            boolean expired = !lock.writeLock().tryLock(2, TimeUnit.SECONDS);
            if (expired) {
                System.out.printf("\n%16s", "Time-out!");
            } else {
                average += subAverage;
                Thread.sleep(1000);
                System.out.printf("\nUpdated average: %.2f", average / ++threadsDone);
            }
            incrementFlag();
            lock.writeLock().unlock();
        }
    }

    private static synchronized void setMax(int subMax) {
        if (subMax > max) {
            max = subMax;
        }
    }

    public static int getMax() {
        return max;
    }

    public synchronized static int getFlag() {
        return flag;
    }

    private static synchronized void incrementFlag() {
        flag++;
    }
}