public class Application {

    public static void main(String args[]) {
        try {
            BigData application = new BigData();
            application.run();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
