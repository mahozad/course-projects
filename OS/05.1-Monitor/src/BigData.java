import java.util.concurrent.*;

class BigData {

    private static final int ACTIVETHREADS = 20;
    private long startTime;

    public void run() throws InterruptedException {
        Terminal.getFile();
        Terminal.getNumber();
        int ranges[][] = new int[Data.threadsNum][2];
        partition(ranges);
        SubBigData[] subBigData = new SubBigData[Data.threadsNum];
        for (int i = 0; i < Data.threadsNum; i++) {
            subBigData[i] = new SubBigData(ranges[i][0], ranges[i][1]);
        }
        ExecutorService executor = Executors.newFixedThreadPool(ACTIVETHREADS);
        startTime = System.currentTimeMillis();
        for (SubBigData thread : subBigData) {
            executor.execute(thread);
        }
        printResults();
        executor.shutdown();
    }

    private void partition(int[][] ranges) {
        for (int i = 0, bound = 0; i < Data.threadsNum; i++) {
            ranges[i][0] = bound;
            ranges[i][1] = (Data.totalNumbers / Data.threadsNum) + bound - 1;
            bound = ranges[i][1] + 1;
        }
        // first thread should start at 1 because 0th number is the numbers count
        ranges[0][0] = 1;
        //final thread should process up to the final number inclusive
        ranges[Data.threadsNum - 1][1] = Data.totalNumbers;
    }

    private void printResults() throws InterruptedException {
        synchronized (BigData.class) {
            BigData.class.wait();
        }
        long duration = System.currentTimeMillis() - startTime;
        System.out.printf("\n%16s %d\n%16s %.2f sec\n\n", "Maximum:", Data.maximum, "Total" + " " +
                "time:", (double) duration / 1000);
    }
}
