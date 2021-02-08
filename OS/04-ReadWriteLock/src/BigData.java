import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class BigData {

    private static final int ACTIVETHREADS = 20;
    private ExecutorService executor;
    private String fileAddress;
    private int totalNumbers;
    private int threadsNum;
    private int initialMax;
    private long startTime;

    public void run() throws IOException, InterruptedException {
        getAndCheckFile();
        getAndCheckNum();
        int ranges[][] = new int[threadsNum][2];
        partition(ranges);
        SubBigData[] subBigData = new SubBigData[threadsNum];
        for (int i = 0; i < threadsNum; i++) {
            subBigData[i] = new SubBigData(ranges[i][0], ranges[i][1]);
        }
        SubBigData.setMaxAndAddress(initialMax, fileAddress);
        executor = Executors.newFixedThreadPool(ACTIVETHREADS);
        startTime = System.currentTimeMillis();
        for (SubBigData thread : subBigData) {
            executor.execute(thread);
        }
        printResults();
    }

    private void partition(int[][] ranges) {
        for (int i = 0, bound = 0; i < threadsNum; i++) {
            ranges[i][0] = bound;
            ranges[i][1] = (totalNumbers / threadsNum) + bound - 1;
            bound = ranges[i][1] + 1;
        }
        // first thread should start at 1 because 0th number is the numbers count
        ranges[0][0] = 1;
        //final thread should process to the final number inclusive
        ranges[threadsNum - 1][1] = totalNumbers;
    }

    private void getAndCheckFile() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter the input file address:\n");
        fileAddress = scanner.nextLine();
        File file = new File(fileAddress);
        while (!file.exists() || isNotDat(file) || !file.canRead()) {
            if (!file.exists()) {
                System.out.printf("The file you specified does not exist.");
            } else if (isNotDat(file)) {
                System.out.printf("This file is not in .dat format.");
            } else {
                System.out.printf("The file cannot be read from.");
            }
            System.out.printf(" Enter a valid file address:\n");
            fileAddress = scanner.nextLine();
            file = new File(fileAddress);
        }
        RandomAccessFile mainFile = new RandomAccessFile(file, "r");
        totalNumbers = mainFile.readInt();
        initialMax = mainFile.readInt();
        mainFile.close();
    }

    private boolean isNotDat(File file) {
        return !file.getName().endsWith(".dat");
    }

    private void getAndCheckNum() {
        boolean isDone = false;
        Scanner scanner = new Scanner(System.in);
        while (!isDone) {
            System.out.printf("Enter number of threads to create:\n");
            try {
                threadsNum = scanner.nextInt();
                while (threadsNum < 1) {
                    System.out.printf("Number of threads cannot be less than 1. Enter a valid " +
                            "number:\n");
                    threadsNum = scanner.nextInt();
                }
                isDone = true;
            } catch (InputMismatchException e) {
                System.err.println("NaN\n");
            }
        }
        System.out.println();
    }

    private void printResults() throws InterruptedException {
        while (SubBigData.getFlag() < threadsNum) {
            Thread.sleep(1);
        }
        long duration = System.currentTimeMillis() - startTime;
        System.out.printf("\n%16s %d\n%16s %.2f sec\n\n", "Maximum:", SubBigData.getMax(),
                "Total" + " time:", (double) duration / 1000);
        executor.shutdown();
    }
}
