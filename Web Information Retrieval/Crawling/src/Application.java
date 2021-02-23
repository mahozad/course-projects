import java.util.concurrent.*;
import javax.swing.*;
import java.util.*;
import java.net.*;

public class Application {

    private static final int TERMINATION_WAIT_TIME = 2;
    private static MainFrame mainFrame;
    private int numberOfThreads;
    private int numberOfPages;
    private int maxDepth; // min depth is 1 that is only the seed pages are retrieved
    private List<Link> seeds; // seeds should have www. at start of them
    private List<BlockingQueue<Link>> listOfQueues;
    private Set<String> linksVisited;
    private Map<String, Integer> ipMap;
    private BlockingQueue<Doc> retrievedDocsQueue;
    private BlockingQueue<Link> parsedLinksQueue;
    private BlockingQueue<Link> unaryQueue;

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        mainFrame = new MainFrame();
        new Application().run();
    }

    private void run() throws InterruptedException {
        while (mainFrame.isVisible()) {
            getAttributes();
            initializeFields();
            boolean seedsAreCorrect = initializeQueues();
            if (seedsAreCorrect) {
                crawl();
            }
            mainFrame.reset();
        }
    }

    private void crawl() throws InterruptedException {
        Fetcher.setAttributes(unaryQueue, retrievedDocsQueue);
        ExecutorService executorService = Executors.newCachedThreadPool();
        Arbiter arbiter = new Arbiter(listOfQueues, unaryQueue);
        Parser parser = new Parser(maxDepth, numberOfPages, retrievedDocsQueue, parsedLinksQueue);
        UniqueChecker uniqueChecker = new UniqueChecker(parsedLinksQueue, linksVisited, listOfQueues, ipMap);
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(new Fetcher());
        }
        executorService.execute(arbiter);
        executorService.execute(parser);
        executorService.execute(uniqueChecker);
        executorService.shutdown();
        while (!Fetcher.isFinished()) {
            Thread.sleep(100);
        }
        //        executorService.shutdownNow();
        //        executorService.awaitTermination(TERMINATION_WAIT_TIME, TimeUnit.MINUTES);
    }

    private void getAttributes() throws InterruptedException {
        synchronized (MainFrame.class) {
            while (!mainFrame.areAttributesReady()) {
                MainFrame.class.wait();
            }
        }
        numberOfThreads = mainFrame.getNumberOfThreads();
        numberOfPages = mainFrame.getNumberOfPages();
        maxDepth = mainFrame.getMaxDepth();
        seeds = mainFrame.getSeeds();
    }

    private void initializeFields() {
        ipMap = new ConcurrentHashMap<>();
        unaryQueue = new LinkedBlockingQueue<>();
        retrievedDocsQueue = new LinkedBlockingQueue<>();
        linksVisited = new HashSet<>();
        parsedLinksQueue = new LinkedBlockingQueue<>();
        listOfQueues = new LinkedList<>();
    }

    private boolean initializeQueues() {
        try {
            for (Link seed : seeds) {
                InetAddress address = InetAddress.getByName(new URL(seed.getUrl()).getHost());
                String ip = address.getHostAddress();
                ipMap.put(ip, listOfQueues.size());
                linksVisited.add(seed.getUrl());
                LinkedBlockingQueue<Link> queue = new LinkedBlockingQueue<>();
                queue.add(seed);
                listOfQueues.add(queue);
            }
            return true;
        } catch (UnknownHostException | MalformedURLException e) {
            mainFrame.setTextFiledBorder(listOfQueues.size());
            return false;
        }
    }
}
/*
* bugs:
* if there is only one seed and the page redirects the crawler does not work
* progress bar is not shown if it is in 2%
* */
// code for getting input from command line
   /* private static void makeSeeds(String url) throws IOException {
        if (!linksVisited.contains(url)) {
            while (linksVisited.size() - 1 < numberOfThreads) {
                Document htmlDocument = Jsoup.connect(url).get();
                Elements linksOnPage = htmlDocument.select("a[href]");
                for (Element link : linksOnPage) {
                    String urlString = link.attr("abs:href");
                    makeSeeds(urlString);
                }
            }
        }
    }

    public static String getDomainName(String url) throws URISyntaxException {
        String domain = new URI(url).getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    private static void setAttributes(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "-n":
                        numberOfThreads = Integer.valueOf(args[++i]);
                        break;
                    case "-m":
                        numberOfPages = Integer.valueOf(args[++i]);
                        break;
                    case "-d":
                        maxDepth = Integer.valueOf(args[++i]);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
            if (args.length == 0) {
                showUsage(3);
            } else if (args.length != 6) {
                showUsage(1);
            } else if (numberOfThreads < 1 || numberOfPages < 1 || maxDepth < 1) {
                showUsage(2);
            }
        } catch (Exception e) {
            showUsage(3);
        }
    }

    private static void showUsage(int state) {
        if (state == 1) {
            System.out.println("Provide exactly 3 arguments");
        } else if (state == 2) {
            System.out.println("All numbers should be greater than 0");
        } else {
            System.out.println("Usage: -n NumberOfThreads -m MaxPages -d Depth");
        }
    }

    private static void getInitialSeed() {
        Scanner scanner = new Scanner(System.in);
        UrlValidator urlValidator = new UrlValidator();
        System.out.println("Enter the initial seed (URL):");
        seeds = scanner.nextLine();
        while (!urlValidator.isValid(seeds)) {
            System.out.println("The URL is not valid. Enter a valid URL:");
            seeds = scanner.nextLine();
        }
        linksVisited.add(seeds);
    }*/
