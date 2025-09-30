package threads.section2.example2;

public class MyThread extends Thread {

  @Override
  public void run() {
    // code that will run in a new thread
    System.out.println(this.getName() + " with priority " + this.getPriority() + " running...");
    System.out.println();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println(this.getName() + " completed.");
  }
}
