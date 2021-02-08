import java.rmi.server.*;
import java.util.*;
import java.rmi.*;
import java.io.*;

class BigData extends UnicastRemoteObject implements ServerInterface {

    private int totalNumbers;
    private int endPoint = 0;
    private String address;
    private long startTime;
    private double sum = 0;
    private int prcsNum;
    private int max;

    BigData() throws RemoteException {
        super();
    }

    void run() throws Exception {
        getAndCheckFile();
        getAndCheckNum();
        String[] ranges = new String[prcsNum];
        partition(ranges);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < prcsNum; i++) {
            String command = String.format("java -cp C:\\Users\\Mahdi\\IdeaProjects" + " " +
                    "SubBigData %s %s", ranges[i], address);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        }
    }

    private void partition(String[] array) {
        int ranges[][] = new int[prcsNum][2];
        for (int i = 0, bound = 0; i < prcsNum; i++) {
            ranges[i][0] = bound;
            ranges[i][1] = (totalNumbers / prcsNum) + bound - 1;
            bound = ranges[i][1] + 1;
        }
        ranges[0][0] = 1;
        ranges[prcsNum - 1][1] = totalNumbers;
        for (int i = 0; i < prcsNum; i++) {
            array[i] = String.format("%s-%s", ranges[i][0], ranges[i][1]);
        }
    }

    private void getAndCheckFile() throws Exception {
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
        mainFile.close();
    }

    private void getAndCheckNum() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter number of processes to create:\n");
        try {
            prcsNum = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("NaN\n");
            getAndCheckNum();
        }
        while (prcsNum < 1) {
            System.out.printf("Number of processes cannot be less than 1." + " Enter a valid " +
                    "number:\n");
            try {
                prcsNum = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("NaN\n");
                getAndCheckNum();
            }
        }
    }

    private boolean isNotDat(File file) {
        return !file.getName().endsWith(".dat");
    }

    @Override
    public void setResults(int subProcessMax, double subProcessAvg) throws Exception {
        sum += subProcessAvg;
        if (subProcessMax > max) {
            max = subProcessMax;
        }
        if (++endPoint == prcsNum) {
            long duration = System.currentTimeMillis() - startTime;
            double average = sum / prcsNum;
            System.out.printf("\n%11s %.2f\n%11s %d\n%11s %.2f sec\n\n", "Average:", average,
                    "Maximum:", max, "Total time:", (double) duration / 1000);
            System.exit(0);
        }
    }
}
