package designgurus.synchronizationconstructs;

public class BusyWaitBlockingProducerConsumer {
  private static final Object mtx = new Object(); // mutex/lock
  private static int sharedNumber;
  private static boolean ready = false;

  private static void producer() {
    synchronized (mtx) {
      try {
        Thread.sleep(2000); // Sleep for 2 seconds
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Producer thread was interrupted");
      }
      sharedNumber = 42; // Producing a number
      ready = true;
      System.out.println("Producer has produced the number: " + sharedNumber);
    }
  }

  private static void consumer() {
    // Busy waiting loop
    while (true) {
      // Since the call to Thread.sleep(2000) is inside the synchronized (mtx) block,
      // the producer does hold the lock (mtx) for the entire 2 seconds. aka. Blocked!
      // this will cause the code to stop and wait (albeit not consuming CPU cycles)

      synchronized (mtx) {
        if (ready) {
          System.out.println("Consumer has consumed the number: " + sharedNumber);
          break;
        }
      }
      // This code will only be reached if consumer acquired lock first, but note "ready" will still
      // be false as producer has not produced/shared the number
      try {
        Thread.sleep(1); // Sleep for a short time
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Consumer thread was interrupted");
      }
    }
  }

  public static void main(String[] args) {
    Thread producerThread = new Thread(BusyWaitBlockingProducerConsumer::producer);
    Thread consumerThread = new Thread(BusyWaitBlockingProducerConsumer::consumer);

    producerThread.start();
    consumerThread.start();

    try {
      producerThread.join();
      consumerThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println("Main thread was interrupted");
    }
  }
}
