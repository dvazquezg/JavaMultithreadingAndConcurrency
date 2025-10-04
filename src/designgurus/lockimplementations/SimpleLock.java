package designgurus.lockimplementations;

public class SimpleLock {
  private boolean isLocked = false;
  private Thread lockingThread = null;

  // Acquire the lock
  public synchronized void lock() throws InterruptedException {
    while (isLocked) {
      wait(); // wait until lock is released
    }
    isLocked = true;
    lockingThread = Thread.currentThread();
  }

  // Release the lock
  public synchronized void unlock() {
    if (Thread.currentThread() != this.lockingThread) {
      throw new IllegalMonitorStateException("Calling thread has not locked this lock");
    }
    isLocked = false;
    lockingThread = null;
    notify(); // wake up one waiting thread
  }
}
