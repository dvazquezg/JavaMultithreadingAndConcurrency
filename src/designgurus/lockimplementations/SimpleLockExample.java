package designgurus.lockimplementations;

public class SimpleLockExample {
  private static final SimpleLock lock = new SimpleLock();

  public static void main(String[] args) {
    Runnable task =
        () -> {
          try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " acquired the lock");
            Thread.sleep(1000); // simulate work
          } catch (InterruptedException e) {
            e.printStackTrace();
          } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " released the lock");
          }
        };

    Thread t1 = new Thread(task, "Thread-1");
    Thread t2 = new Thread(task, "Thread-2");

    t1.start();
    t2.start();
  }
}
