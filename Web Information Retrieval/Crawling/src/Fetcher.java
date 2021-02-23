import org.jsoup.nodes.*;
import org.jsoup.*;

import java.util.concurrent.*;

public class Fetcher implements Runnable {

    private static BlockingQueue<Link> sourceLinksQueue;
    private static BlockingQueue<Doc> documentsQueue;
    private static boolean isFinished = false;

    static synchronized boolean isFinished() {
        return isFinished;
    }

    static void setAttributes(BlockingQueue<Link> srcQueue, BlockingQueue<Doc> rtvQueue) {
        sourceLinksQueue = srcQueue;
        documentsQueue = rtvQueue;
        isFinished = false;
    }

    @Override
    public void run() {
        fetch();
        setFinishedFlag();
    }

    private void fetch() {
        while (!Parser.isFinished()) {
            try {
                Link nextLink = sourceLinksQueue.take();
                Document htmlDocument = Jsoup.connect(nextLink.getUrl()).get();
                documentsQueue.put(new Doc(htmlDocument, nextLink.getDepth()));
            } catch (Exception ignored) {
            }
        }
    }

    private static synchronized void setFinishedFlag() {
        isFinished = true;
    }
}
