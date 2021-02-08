import java.io.*;
import java.rmi.*;

class SubBigDataRun {

    private RandomAccessFile file;
    private int fileBeg;
    private int fileEnd;

    void run(String args[]) throws Exception {
        file = new RandomAccessFile(args[1], "r");
        setRange(args[0]);
        calculate();
    }

    private void setRange(String range) {
        int index = 0;
        while (range.charAt(index) != '-') {
            index++;
        }
        fileBeg = Integer.parseInt(range.substring(0, index));
        fileEnd = Integer.parseInt(range.substring(index + 1));
    }

    private void calculate() throws Exception {
        ServerInterface bigDataServer = (ServerInterface) Naming.lookup("//localhost/RmiServer");
        file.seek(fileBeg * 4);
        int max = file.readInt();
        file.seek(fileBeg * 4);
        int num;
        long sum = 0;
        int totalNumbers = fileEnd - fileBeg + 1;
        for (int i = 0; i < totalNumbers; i++) {
            num = file.readInt();
            sum += num;
            if (num > max) {
                max = num;
            }
        }
        double average = (double) sum / totalNumbers;
        bigDataServer.setResults(max, average);
    }
}
