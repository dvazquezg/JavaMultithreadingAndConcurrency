package designgurus.synchronizationconstructs;

public class Mutex {
  private static int counter = 0;
  private static final Object lock = new Object();

  public static void runExperiment(String experimentName, Runnable task) {
    counter = 0; // reset counter before each experiment

    Thread t1 = new Thread(task);
    Thread t2 = new Thread(task);

    t1.start();
    t2.start();

    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Final counter value " + experimentName + ": " + counter);
  }

  public static void incrementCounterWithMutex() {
    for (int i = 0; i < 100; i++) {
      synchronized (lock) {
        int temp = counter;
        try {
          Thread.sleep(1); // Sleep for 1 millisecond
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        counter = temp + 1;
      }
    }
  }

  public static void incrementCounterNoMutex() {
    for (int i = 0; i < 100; i++) {
      int temp = counter;
      try {
        Thread.sleep(1); // Sleep for 1 millisecond
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      counter = temp + 1;
    }
  }

  public static void main(String[] args) {
    runExperiment("With Mutex Experiment", Mutex::incrementCounterWithMutex);
    runExperiment("No Mutex Experiment", Mutex::incrementCounterNoMutex);
  }
}
