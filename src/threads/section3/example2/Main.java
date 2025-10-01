package threads.section3.example2;

import java.math.BigInteger;

public class Main {
  public static void main(String[] args) {
    BigInteger base = new BigInteger("20000");
    BigInteger power = new BigInteger("10000000");
    Thread longComputationThread = new Thread(new LongComputationTask(base, power));
    longComputationThread.start();
    longComputationThread.interrupt();
  }
}
