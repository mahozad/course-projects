class Data {

    static final Object RESULTS_FIELD_LOCK = new Object();
    static final Object SUB_BIG_DATA_SYNC = new Object();
    static final Object PROGRESSBAR_LOCK = new Object();
    static boolean isCompleted = true;
    static double progressBarValue;
    static boolean canRun = false;
    static int numberOfThreads;
    static MainFrame mainFrame;
    static String fileAddress;
    static int totalNumbers;
    static int maximum;
}
