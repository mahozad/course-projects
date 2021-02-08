import java.io.*;
import java.util.*;

public class BigData {

    private int prcsNum;
    private String address;
    private int totalNumbers;
    private double sum = 0;
    private int max;
    private long start;

    public void run() throws IOException, InterruptedException {
        getAndCheckFile();
        getAndCheckNum();
        String prcsBound[] = new String[prcsNum];
        int bound = 0;
        for (int i = 0; i < prcsNum; i++) {
            prcsBound[i] = String.format("%s-%s", bound, (totalNumbers / prcsNum + bound));
            bound += (totalNumbers / prcsNum);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < prcsNum; i++) {
            String command = String.format("java -cp C:\\Users\\Mahdi\\IdeaProjects SubProcess %s %s %d", prcsBound[i], address, i);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        }
        printResults();
    }

    public void getAndCheckFile() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter the input file address:\n");
        address = scanner.nextLine();
        File file = new File(address);
        while (!file.exists() || file.isDirectory() || isNotDat(file)) {
            if (!file.exists()) {
                System.out.printf("The file you specified does not exist.");
            } else if (file.isDirectory()) {
                System.out.printf("The address you specified is a directory.");
            } else {
                System.out.printf("This file is not in .dat format.");
            }
            System.out.printf(" Enter a valid file address:\n");
            address = scanner.nextLine();
            file = new File(address);
        }
        RandomAccessFile mainFile = new RandomAccessFile(file, "r");
        totalNumbers = mainFile.readInt();
        max = mainFile.readInt();
    }

    public void getAndCheckNum() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter number of processes to create:\n");
        try {
            prcsNum = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("NaN\n");
            getAndCheckNum();
        }
        while (prcsNum < 1) {
            System.out.printf("Number of processes cannot be less than 1. Enter a valid number:\n");
            try {
                prcsNum = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("NaN\n");
                getAndCheckNum();
            }
        }
    }

    public boolean isNotDat(File file) {
        return !file.getName().endsWith(".dat");
    }

    public void printResults() throws IOException, InterruptedException {
        for (int i = 0; i < prcsNum; i++) {
            String fileName = String.format("file%d.txt", i);
            File file = new File(fileName);
            while (!file.exists()) {
            }
            Thread.sleep(100);
            RandomAccessFile mainFile = new RandomAccessFile(fileName, "r");
            int tempMax = mainFile.readInt();
            if (tempMax > max) {
                max = tempMax;
            }
            sum += mainFile.readDouble();
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        double average = sum / prcsNum;
        System.out.printf("\n\n   Average: %.2f\n   Maximum: %d\n" + "Total time: %.2f s\n\n",
                average, max, (double) duration / 1000);
    }
}
