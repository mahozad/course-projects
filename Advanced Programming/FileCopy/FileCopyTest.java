
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class FileCopyTest {

    public static void main(String[] args) throws IOException {
        FileCopy program = new FileCopy();
        String path1;
        String path2;
        Scanner input = new Scanner(System.in);
        System.out.printf("Enter the path you want to be copied from: ");
        path1 = input.nextLine();
        File File1 = new File(path1);
        System.out.printf("Enter the path you want to copy to: ");
        path2 = input.nextLine();
        File File2 = new File(path2);
        program.mainMethod(File1, File2);
    }
}
