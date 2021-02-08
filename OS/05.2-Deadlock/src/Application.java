import java.io.*;
import java.lang.management.*;
import java.util.*;

public class Application {

    private static Thread[] threads = new Thread[3];

    public static void main(String args[]) {
        try {
            threads[0] = new Thread(new Runner(1));
            threads[1] = new Thread(new Runner(1));
            threads[2] = new Thread(new Runner(2));
            for (Thread t : threads) {
                t.start();
            }
            Thread.sleep(4000);
            Thread inspector = new Thread(new Inspector());
            inspector.start();
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

    public static Thread[] getThreads() {
        return threads;
    }
}

class Runner implements Runnable {

    private static Stack<Thread> stack = new Stack<>();
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();
    private Random random = new Random();
    private RandomAccessFile file;
    private int num = 0;
    private int myId;

    Runner(int id) {
        myId = id;
    }

    @Override
    public void run() {
        try {
            file = new RandomAccessFile("file.txt", "rw");
            while (true) {
                if (myId == 1) {
                    synchronized (LOCK1) {
                        stack.push(Thread.currentThread());
                        Thread.sleep(2500);
                        synchronized (LOCK2) {
                            stack.push(Thread.currentThread());
                            work();
                        }
                    }
                } else {
                    synchronized (LOCK2) {
                        stack.push(Thread.currentThread());
                        synchronized (LOCK1) {
                            stack.push(Thread.currentThread());
                            work();
                        }
                    }
                }
            }
        } catch (InterruptedException | IOException e) {
            e.getMessage();
        }
    }

    public void work() {
        for (int i = 0; i < 200; i++) {
            try {
                num += random.nextInt(10);
                file.writeInt(num);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public static Stack getStack() {
        return stack;
    }
}

class Inspector implements Runnable {

    @Override
    public void run() {
        ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
        long ids[] = tmx.findDeadlockedThreads();
        if (ids != null) {
            System.out.println("Deadlock detected!");
            Thread last = (Thread) Runner.getStack().pop();
            for (int i = 0; i < 3; i++) {
                if (Application.getThreads()[i] != last) {
                    Application.getThreads()[i].interrupt();
                }
            }
        }
    }
}