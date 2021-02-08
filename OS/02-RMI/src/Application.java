import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Application {

    public static void main(String args[]) throws Exception {
        LocateRegistry.createRegistry(1099);
        BigData bigData = new BigData();
        Naming.rebind("//localhost/RmiServer", bigData);
        bigData.run();
    }
}
