package designgurus.synchronizationconstructs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {
  private static volatile int counter = 0;
  private static final int TARGET_VALUE = 1000;
  private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  public static int incrementValue() {
    lock.writeLock().lock(); // this behaves like a mutex lock (only one thread is allowed to
    // hold lock at a time
    try {
      Thread.sleep(1);
      if (counter < TARGET_VALUE) {
        counter++;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.writeLock().unlock();
    }
    return counter;
  }

  public static int readValue() {
    lock.readLock()
        .lock(); // this will allow multiple threads to access lock unlike write lock/mutex
    try {
      Thread.sleep(1);
      return counter; // will execute finally block before actually returning value
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.readLock().unlock(); // Method returns after the finally block completes
    }
    return 0;
  }

  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    List<Thread> readers = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      readers.add(
          new Thread(
              () -> {
                while (readValue() < TARGET_VALUE) {
                  try {
                    Thread.sleep(1);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
              }));
    }

    List<Thread> writers = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      writers.add(
          new Thread(
              () -> {
                while (incrementValue() < TARGET_VALUE) {
                  try {
                    Thread.sleep(1);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
              }));
    }

    readers.forEach(Thread::start);
    writers.forEach(Thread::start);

    readers.forEach(
        t -> {
          try {
            t.join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        });

    writers.forEach(
        t -> {
          try {
            t.join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        });

    long end = System.currentTimeMillis();
    System.out.println("Time taken: " + (end - start) / 1000.0 + " seconds");
  }
}
