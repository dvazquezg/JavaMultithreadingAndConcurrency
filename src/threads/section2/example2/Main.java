package threads.section2.example2;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    MyThread myThread = new MyThread();

    System.out.println(
        "We are in Main Thread "
            + Thread.currentThread().getName()
            + " before starting a new thread");
    myThread.start();
    myThread.join();
    System.out.println("Main tread completed.");
  }
}
