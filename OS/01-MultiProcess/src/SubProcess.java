import java.io.*;

public class SubProcess {

    private static int fileEnd;
    private static int fileBeg;
    private static RandomAccessFile file;
    private static int max;
    private static double average;

    public static void main(String args[]) throws IOException {
        setRange(args[0]);
        checkExistence(args[1]);
        file = new RandomAccessFile(args[1], "r");
        calculate();
        writeResults(args[2]);
    }

    public static void checkExistence(String path) {
        File file=new File(path);
        if (file.exists()){}
    }

    public static void setRange(String range) {
        int index = 0;
        while (range.charAt(index) != '-') {
            index++;
        }
        fileBeg = Integer.parseInt(range.substring(0, index));
        fileEnd = Integer.parseInt(range.substring(index + 1));
    }

    public static void calculate() throws IOException {
        file.seek(fileBeg * 4 + 4);
        max = file.readInt();
        file.seek(fileBeg * 4 + 4);
        int num;
        long sum = 0;
        int totalNumbers = fileEnd - fileBeg;
        for (int i = 0; i < totalNumbers; i++) {
            num = file.readInt();
            if (num > max) {
                max = num;
            }
            sum += num;
        }
        average = (double) sum / totalNumbers;
    }

    public static void writeResults(String processid) throws IOException {
        String fileName = String.format("file%s.txt", processid);
        RandomAccessFile file = new RandomAccessFile(fileName, "rw");
        file.writeInt(max);
        file.writeDouble(average);
        file.close();
    }
}
