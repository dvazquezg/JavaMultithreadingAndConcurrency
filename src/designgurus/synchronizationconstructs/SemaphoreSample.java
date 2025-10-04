package designgurus.synchronizationconstructs;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Note: AtomicInteger in Java provides a thread-safe way to perform operations on an int value
 * without requiring explicit synchronization mechanisms like synchronized blocks or locks. It
 * achieves this primarily through the use of Compare-And-Swap (CAS) operations, which are
 * low-level, atomic CPU instructions.
 *
 * <p>Note 2: Do you need a Semaphore here? Usually, no. AtomicInteger.incrementAndGet() (or
 * addAndGet(), compareAndSet()) are already atomic and lock-free. Adding a Semaphore just to
 * protect increments would make things slower, because we’re layering lock-based coordination on
 * top of already thread-safe operations. This is for demonstration purposes.
 *
 * <p>Note 3: Semaphore vs Fixed Thread Pool - difference at a high level:
 *
 * <p>Fixed Thread Pool (ExecutorService with fixed size) Controls the number of worker threads that
 * can run tasks concurrently. Tasks beyond the limit wait in a queue until a thread becomes
 * available. Focus: managing task execution.
 *
 * <p>Semaphore Controls the number of threads that can access a particular resource or critical
 * section at the same time. Threads not allowed to proceed block until permits are available.
 * Focus: managing access to resources.
 *
 * <p>Example to illustrate: If you want at most 5 tasks running in parallel, use a fixed thread
 * pool. If you have unlimited tasks, but only 3 database connections, you might use a semaphore
 * with 3 permits to guard the database connection pool.
 */
public class SemaphoreSample {
  // Global shared resource
  // AtomicInteger allows multiple threads to read/write value of counter without requiring
  // synchronization
  private static final AtomicInteger counter = new AtomicInteger(0);

  // Semaphore with a count of 5
  // smaller number: fewer changes at once (slower)
  // greater numbers: more changes at once (faster)
  // keep in mind thread-safe operations is directly handled by AtomicInteger
  private static final Semaphore semaphore = new Semaphore(5);

  private static final int TARGET_VALUE = 5000;

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();

    Thread[] workers = new Thread[10];
    for (int i = 0; i < workers.length; i++) {
      workers[i] = new Thread(SemaphoreSample::worker);
      workers[i].start();
    }

    for (Thread worker : workers) {
      try {
        worker.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread was interrupted");
      }
    }

    long endTime = System.currentTimeMillis();
    System.out.println("Time taken: " + (endTime - startTime) / 1000.0 + " seconds");
  }

  private static void worker() {
    while (true) {
      try {
        semaphore.acquire(); // Acquire the semaphore
        if (counter.get() >= TARGET_VALUE) {
          break;
        }
        counter.incrementAndGet(); // Atomically increments the counter
        Thread.sleep(1); // Simulate work
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread was interrupted");
      } finally {
        semaphore.release(); // Release the semaphore
      }
    }
  }
}
