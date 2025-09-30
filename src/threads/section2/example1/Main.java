package threads.section2.example1;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Hello, World!");
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                // code that will run in a new thread
                System.out.println(
                    Thread.currentThread().getName()
                        + " with priority "
                        + Thread.currentThread().getPriority()
                        + " running...");
                System.out.println();
                try {
                  Thread.sleep(3000);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                // System.out.println(Thread.currentThread().getName() + " completed.");
                throw new RuntimeException("Dummy RuntimeException");
              }
            });
    thread.setName("NewWorkerThread");
    thread.setPriority(Thread.MAX_PRIORITY);
    thread.setUncaughtExceptionHandler(
        new Thread.UncaughtExceptionHandler() {
          @Override
          public void uncaughtException(Thread t, Throwable e) {
            System.out.println(
                "A critical error happened in thread "
                    + t.getName()
                    + ". The error is: "
                    + e.getMessage());
          }
        });

    System.out.println(
        "We are in Main Thread "
            + Thread.currentThread().getName()
            + " before starting a new thread");
    thread.start();
    thread.join();
    System.out.println("Main tread completed.");
  }
}
