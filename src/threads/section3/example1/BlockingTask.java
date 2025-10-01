package threads.section3.example1;

public class BlockingTask implements Runnable {

  @Override
  public void run() {
    try {
      Thread.sleep(300000); // 5 min
    } catch (InterruptedException e) {
      System.out.println("Thread interrupted. Exiting blocking thread");
    }
  }
}
