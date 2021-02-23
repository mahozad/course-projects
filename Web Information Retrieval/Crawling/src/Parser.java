import org.jsoup.select.Elements;
import org.jsoup.nodes.*;

import java.util.concurrent.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

class Parser implements Runnable {

    private static final String REPOSITORY_FILE_NAME = "repo.dat";
    private static boolean isFinished = false;
    private BlockingQueue<Doc> documentsQueue;
    private BlockingQueue<Link> parsedLinksQueue;
    private List<Entry> retrieved;
    private int numOfPagesVisited = 0;
    private int maxPageNumber;
    private int maxDepth;

    static synchronized boolean isFinished() {
        return isFinished;
    }

    Parser(int depth, int pageNumber, BlockingQueue<Doc> docs, BlockingQueue<Link> parsed) {
        maxDepth = depth;
        maxPageNumber = pageNumber;
        documentsQueue = docs;
        parsedLinksQueue = parsed;
        retrieved = new LinkedList<>();
        isFinished = false;
    }

    @Override
    public void run() {
        parse();
        setFinishedFlag();
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(REPOSITORY_FILE_NAME));
            stream.writeObject(retrieved);
            stream.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Crawling was successful but cannot store the " +
                    "results to the file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // nextDocument.body().text(); returns the body text only but
    // nextDocument.text(); may also include the title and header
    /*Elements paragraphs = doc.select("p");
      for(Element p : paragraphs){}*/
    private void parse() {
        while (numOfPagesVisited < maxPageNumber) {
            try {
                Doc nextDoc = documentsQueue.take();
                if (nextDoc.getDepth() < maxDepth) {
                    retrieved.add(new Entry(nextDoc.getDocument()));
                    numOfPagesVisited++;
                    MainFrame.setProgressBarStatus(numOfPagesVisited);
                    Elements linksOnPage = nextDoc.getDocument().select("a[href]");
                    for (Element link : linksOnPage) {
                        String urlString = link.attr("abs:href");
                        Link newLink = new Link(urlString, nextDoc.getDepth() + 1);
                        parsedLinksQueue.put(newLink);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static synchronized void setFinishedFlag() {
        isFinished = true;
    }
}
