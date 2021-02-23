import java.util.*;
import java.io.*;

public class CrawledReader {

    private static final String REPOSITORY_FILE_NAME = "repo.dat";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(REPOSITORY_FILE_NAME));
        LinkedList<Entry> retrieved = (LinkedList<Entry>) stream.readObject();
        System.out.println("Size:\t" + retrieved.size() + "\n");
        for (Entry entry : retrieved) {
            System.out.printf("Title:\n%s\n\nTime of retrieval:\n%s\n", entry.getTitle(), entry.getTime());
            System.out.println("==========================");
        }
    }
}
