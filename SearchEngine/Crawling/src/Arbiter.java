import java.util.concurrent.*;
import java.util.*;

/**
 * Arbiter places urls into a unary queue where fetchers can get them.
 * Fetchers could have accessed the queues directly but now they just look into the
 * unary queue and do not care the waiting time in each queue or... and just take
 * the next item. Also it is possible that for example all 20 threads want to take
 * their link from for instance the first list but with unary queue urls taken are
 * collected distributively.
 */
class Arbiter implements Runnable {

    private static final int SWITCH_INTERVAL = 100;
    private List<BlockingQueue<Link>> listOfQueues;
    private BlockingQueue<Link> unaryQueue;
    private int numberOfQueues;

    Arbiter(List<BlockingQueue<Link>> queueList, BlockingQueue<Link> destQueue) {
        listOfQueues = queueList;
        unaryQueue = destQueue;
        numberOfQueues = listOfQueues.size();
    }

    @Override
    public void run() {
        int queueNumber = 0;
        while (!UniqueChecker.isFinished()) {
            try {
                Link nextLink = listOfQueues.get(queueNumber).poll
                        (SWITCH_INTERVAL, TimeUnit.MILLISECONDS);
                if (nextLink != null) {
                    unaryQueue.put(nextLink);
                }
                queueNumber = (queueNumber + 1) % numberOfQueues;
            } catch (Exception ignored) {
            }
        }
    }
}
