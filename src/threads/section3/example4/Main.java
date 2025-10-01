package threads.section3.example4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    List<Long> inputNumbers = Arrays.asList(100000000L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);
    List<FactorialThread> threads = new ArrayList<>();

    // compute factorial of each number
    for (long inputNumber : inputNumbers) {
      threads.add(new FactorialThread(inputNumber));
    }

    // start factorial computations concurrently
    for (Thread t : threads) {
      t.start();
    }

    // force main thread to wait for all threads to finish
    for (Thread t : threads) {
      t.join(2000); // wait 2 seconds at most (avoids waiting for super long computations)
    }

    for (int i = 0; i < threads.size(); i++) {
      FactorialThread factorialThread = threads.get(i);
      if (factorialThread.isFinished()) {
        System.out.println(
            "Factorial of "
                + factorialThread.getInputNumber()
                + " is "
                + factorialThread.getResult());
      } else {
        factorialThread.interrupt();
        System.out.println(
            "The calculation for "
                + factorialThread.getInputNumber()
                + " is taking so long, thus has been halted.");
      }
    }
  }
}
