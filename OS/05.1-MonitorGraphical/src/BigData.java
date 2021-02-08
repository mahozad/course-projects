import java.util.concurrent.*;
import javax.swing.*;

class BigData {

    private static final int ACTIVE_THREADS = 8;
    private ExecutorService executor;
    private long startTime;

    void run() {
        try {
            int ranges[][] = new int[Data.numberOfThreads][2];
            partition(ranges); // set range of data in file for each thread
            SubBigData.initialize();
            SubBigData[] subBigData = new SubBigData[Data.numberOfThreads];
            for (int i = 0; i < Data.numberOfThreads; i++) {
                subBigData[i] = new SubBigData(ranges[i][0], ranges[i][1]);
            }
            executor = Executors.newFixedThreadPool(ACTIVE_THREADS);
            startTime = System.currentTimeMillis();
            for (SubBigData thread : subBigData) {
                executor.execute(thread);
            }
            executor.shutdown();
            produceResults();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Exception in run method",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void partition(int[][] ranges) {
        for (int i = 0, bound = 0; i < Data.numberOfThreads; i++) {
            ranges[i][0] = bound;
            ranges[i][1] = (Data.totalNumbers / Data.numberOfThreads) + bound - 1;
            bound = ranges[i][1] + 1;
        }

        // first thread should start at 1 because 0th number is the numbers count
        ranges[0][0] = 1;

        // last thread should process up to the final number inclusive
        ranges[Data.numberOfThreads - 1][1] = Data.totalNumbers;
    }

    private void produceResults() throws InterruptedException {

        // wait for the specified time until all sub threads are done
        executor.awaitTermination(30, TimeUnit.MINUTES);
        Data.mainFrame.modifyProgressBar(100, false);
        long duration = System.currentTimeMillis() - startTime;
        Data.mainFrame.modifyResultsTextfield(String.format("\n%9s %d\n%12s %.2f " +
                "sec\n", "Maximum:", Data.maximum, "Total time:", (double)
                duration / 1000), true);
        Data.isCompleted = true;
    }
}