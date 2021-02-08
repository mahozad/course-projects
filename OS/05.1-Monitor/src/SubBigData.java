import java.util.concurrent.locks.*;
import java.io.*;

class SubBigData implements Runnable {

    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Object SYNC = new Object();
    private static int threadsDone = 0;
    private static double average = 0;
    private static int flag = 0;
    private int fileBeg;
    private int fileEnd;

    public SubBigData(int beg, int end) {
        fileBeg = beg;
        fileEnd = end;
    }

    @Override
    public void run() {
        try {
            RandomAccessFile file = new RandomAccessFile(Data.fileAddress, "r");
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
            double subAverage = (double) sum / totalNumbers;
            setMax(max);
            manipulateAverage(subAverage);
            incrementFlag();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void manipulateAverage(double subAverage) throws Exception {
        if (subAverage < 1000) {
            long startTime = System.currentTimeMillis();
            synchronized (SYNC) {
                SYNC.wait(2000);
            }
            if (System.currentTimeMillis() - startTime <= 2000) {
                LOCK.lock();
                average += subAverage;
                System.out.printf("\nUpdated average: %.2f", average / ++threadsDone);
                LOCK.unlock();
            } else {
                System.out.printf("\n%16s", "Time-out!");
            }
        } else {
            LOCK.lock();
            average += subAverage;
            Thread.sleep(500);
            System.out.printf("\nUpdated average: %.2f", average / ++threadsDone);
            LOCK.unlock();
            synchronized (SYNC) {
                for (int i = 0; i < 3; i++) {
                    SYNC.notify();
                }
            }
        }
    }

    private static synchronized void setMax(int subMax) {
        if (subMax > Data.maximum) {
            Data.maximum = subMax;
        }
    }

    private static synchronized void incrementFlag() {
        if (++flag == Data.threadsNum) {
            synchronized (BigData.class) {
                BigData.class.notify();
            }
        }
    }
}