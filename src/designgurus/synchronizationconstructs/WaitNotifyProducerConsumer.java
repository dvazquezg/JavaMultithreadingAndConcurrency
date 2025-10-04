package designgurus.synchronizationconstructs;

/** In this example we use a condition variable (monitor lock) along wait/notify mechanism. */
public class WaitNotifyProducerConsumer {
  private static final Object mtx = new Object(); // monitor lock
  private static int sharedNumber;
  private static boolean ready = false;

  public static void producer() {
    synchronized (mtx) {
      sharedNumber = 42; // Producing a number
      ready = true;
      System.out.println("Producer has produced the number: " + sharedNumber);
      mtx.notify(); // Notify the consumer
    }
  }

  public static void consumer() {
    synchronized (mtx) {
      while (!ready) {
        try {
          mtx.wait(); // Wait for producer until the number is ready
          // in this example, the loop will iterate once more and check !ready. Since ready
          // will be true by then, it will exit the loop and move on to rest of consumer code
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          System.out.println("Consumer thread was interrupted.");
        }
      }
      System.out.println("Consumer has consumed the number: " + sharedNumber);
    }
  }

  public static void main(String[] args) {
    Thread producerThread = new Thread(WaitNotifyProducerConsumer::producer);
    Thread consumerThread = new Thread(WaitNotifyProducerConsumer::consumer);

    producerThread.start();
    consumerThread.start();

    try {
      producerThread.join();
      consumerThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println("Main thread was interrupted.");
    }
  }
}
