package threads.section2.project1;

public class PoliceThread extends Thread {
  @Override
  public void run() {
    for (int t = 10; t > 0; t--) {
      try {
        Thread.sleep(1000); // one second
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Police coming in " + t + " seconds");
    }
    System.out.println("Police is here! Game over for you hackers!");
    System.exit(0);
  }
}
