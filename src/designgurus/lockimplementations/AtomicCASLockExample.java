package designgurus.lockimplementations;

public class AtomicCASLockExample {
  private static final AtomicCASLock lock = new AtomicCASLock();

  public static void main(String[] args) {
    Runnable task =
        () -> {
          lock.lock();
          try {
            System.out.println(Thread.currentThread().getName() + " acquired the lock");
            try {
              Thread.sleep(1000); // simulate work
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " released the lock");
          }
        };

    Thread t1 = new Thread(task, "Thread-1");
    Thread t2 = new Thread(task, "Thread-2");
    Thread t3 = new Thread(task, "Thread-3");

    t1.start();
    t2.start();
    t3.start();
  }
}
