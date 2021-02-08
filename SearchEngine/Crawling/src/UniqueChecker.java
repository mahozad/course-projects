import java.util.concurrent.*;
import java.util.*;
import java.net.*;

class UniqueChecker implements Runnable {

    private static boolean isFinished = false;
    private Set<String> linksVisited;
    private List<BlockingQueue<Link>> listOfQueues;
    private Map<String, Integer> ipMap;
    private BlockingQueue<Link> parsedLinksQueue;

    static synchronized boolean isFinished() {
        return isFinished;
    }

    UniqueChecker(BlockingQueue<Link> parsedLinks, Set<String> visitedLinks,
                  List<BlockingQueue<Link>> queueList, Map<String, Integer> map) {
        parsedLinksQueue = parsedLinks;
        linksVisited = visitedLinks;
        listOfQueues = queueList;
        ipMap = map;
        isFinished = false;
    }

    @Override
    public void run() {
        checkAndMap();
        setFinishedFlag();
    }

    private void checkAndMap() {
        while (!Parser.isFinished()) {
            try {
                Link nextLink = parsedLinksQueue.take();
                if (!linksVisited.contains(nextLink.getUrl())) {
                    linksVisited.add(nextLink.getUrl());
                    InetAddress address = InetAddress.getByName(new URL(nextLink.getUrl())
                            .getHost());
                    String ip = address.getHostAddress();
                    Integer listIndex = ipMap.get(ip);
                    if (listIndex != null) {
                        listOfQueues.get(listIndex).put(nextLink);
                    } else {
                        ipMap.put(ip, listOfQueues.size());
                        linksVisited.add(nextLink.getUrl());
                        LinkedBlockingQueue<Link> queue = new LinkedBlockingQueue<>();
                        queue.add(nextLink);
                        listOfQueues.add(queue);
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
