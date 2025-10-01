package threads.section3.example3;

import java.math.BigInteger;

public class Main {
  public static void main(String[] args) {
    BigInteger base = new BigInteger("20000");
    BigInteger power = new BigInteger("100000");
    Thread longComputationThread = new Thread(new LongComputationTask(base, power));
    longComputationThread.setDaemon(true);
    longComputationThread.start();
    // After calling interrupt(), the main method/thread finishes.
    // Since the only remaining thread is a daemon thread,
    // the JVM shuts down immediately, killing the daemon thread
    // regardless of whether it finished computation.
    longComputationThread.interrupt();
  }
}
