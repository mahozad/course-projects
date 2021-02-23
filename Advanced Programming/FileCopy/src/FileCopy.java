import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileCopy {

    public void mainMethod(File File1, File File2) throws IOException {
        if (File1.isDirectory()) {
            if (!File2.exists()) {
                File2.mkdirs();
            }
            String contents[] = File1.list();
            for (String tempFile : contents) {
                File newFile1 = new File(File1, tempFile);
                File newFile2 = new File(File2, tempFile);
                mainMethod(newFile1, newFile2);
            }
        } else {
            RandomAccessFile myFile1 = new RandomAccessFile(File1, "r");
            RandomAccessFile myFile2 = new RandomAccessFile(File2, "rw");
            byte tempByte;
            for (int i = 0; i < myFile1.length(); i++) {
                tempByte = myFile1.readByte();
                myFile2.write(tempByte);
            }
        }
    }
}
