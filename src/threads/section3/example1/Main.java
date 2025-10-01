package threads.section3.example1;

public class Main {
  public static void main(String[] args) {
    Thread blockingClass = new Thread(new BlockingTask());
    blockingClass.start();
    blockingClass.interrupt();
  }
}
