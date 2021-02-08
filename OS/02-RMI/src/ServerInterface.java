import java.rmi.*;

interface ServerInterface extends Remote {

    void setResults(int subProcessMax, double subProcessSum) throws Exception;
}
