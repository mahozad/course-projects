import java.util.*;
import java.io.*;

class Terminal {

    public static void getFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter the input file address:\n");
        Data.fileAddress = scanner.nextLine();
        File file = new File(Data.fileAddress);
        while (!file.exists() || isNotDat(file) || !file.canRead()) {
            if (!file.exists()) {
                System.out.printf("The file you specified does not exist.");
            } else if (isNotDat(file)) {
                System.out.printf("The file name doesn't comply with naming rules.");
            } else {
                System.out.printf("The file cannot be read from.");
            }
            System.out.printf(" Enter a valid file address:\n");
            Data.fileAddress = scanner.nextLine();
            file = new File(Data.fileAddress);
        }
        try {
            RandomAccessFile mainFile = new RandomAccessFile(file, "r");
            Data.totalNumbers = mainFile.readInt();
            Data.maximum = mainFile.readInt();
            mainFile.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private static boolean isNotDat(File file) {
        return !file.getName().matches("[ -_.A-Za-z0-9]+\\.dat");
    }

    public static void getNumber() {
        boolean isDone = false;
        Scanner scanner = new Scanner(System.in);
        while (!isDone) {
            System.out.printf("Enter number of threads to create:\n");
            try {
                Data.threadsNum = scanner.nextInt();
                while (Data.threadsNum < 1) {
                    System.out.printf("Number of threads cannot be less than 1. Enter a valid " +
                            "number:\n");
                    Data.threadsNum = scanner.nextInt();
                }
                isDone = true;
            } catch (InputMismatchException e) {
                System.err.println("NaN\n");
            }
        }
    }
}
