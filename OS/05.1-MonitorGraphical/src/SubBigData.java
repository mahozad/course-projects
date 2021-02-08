import java.util.concurrent.locks.*;
import javax.swing.*;
import java.io.*;

class SubBigData implements Runnable {

    private static final ReentrantLock LOCK = new ReentrantLock(true);
    private static int threadsDone;
    private static double average;
    private int fileBeg;
    private int fileEnd;

    SubBigData(int beg, int end) {
        fileBeg = beg;
        fileEnd = end;
    }

    static void initialize() {
        threadsDone = 0;
        average = 0;
    }

    @Override
    public void run() {
        try {
            double subAverage = calculateSubAverage();
            manipulateAverage(subAverage);
            Data.mainFrame.modifyProgressBar(100.0 / Data.numberOfThreads, true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception in SubBigData " +
                    "class", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calculateSubAverage() throws IOException {
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
        checkMax(max);
        return subAverage;
    }

    private void manipulateAverage(double subAverage) throws InterruptedException {
        if (subAverage < 1000) {
            manipulateSmallAverage(subAverage);
        } else {
            manipulateLargeAverage(subAverage);
        }
    }

    private void manipulateLargeAverage(double subAverage) throws
            InterruptedException {
        LOCK.lock();
        average += subAverage;
        Thread.sleep(500);
        Data.mainFrame.modifyResultsTextfield(String.format("Updated " +
                "average:" + " %.2f\n", average / ++threadsDone), true);
        LOCK.unlock();
        for (int i = 0; i < 3; i++) {
            synchronized (Data.SUB_BIG_DATA_SYNC) {
                Data.SUB_BIG_DATA_SYNC.notify();
            }
        }
    }

    private void manipulateSmallAverage(double subAverage) throws
            InterruptedException {
        long startTime = System.currentTimeMillis();
        synchronized (Data.SUB_BIG_DATA_SYNC) {
            Data.SUB_BIG_DATA_SYNC.wait(2000);
        }
        if (System.currentTimeMillis() - startTime < 2000) {
            LOCK.lock();
            average += subAverage;
            Data.mainFrame.modifyResultsTextfield(String.format("Updated " +
                    "average: %.2f\n", average / ++threadsDone), true);
            LOCK.unlock();
        } else {
            Data.mainFrame.modifyResultsTextfield("Time-out!\n", true);
        }
    }

    private static synchronized void checkMax(int subMax) {
        if (subMax > Data.maximum) {
            Data.maximum = subMax;
        }
    }
}