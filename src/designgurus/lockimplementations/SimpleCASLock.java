package designgurus.lockimplementations;

public class SimpleCASLock {
  private boolean locked = false;

  // custom compareAndSet implementation
  private synchronized boolean compareAndSet(boolean expected, boolean newValue) {
    if (locked == expected) {
      locked = newValue;
      return true;
    }
    return false;
  }

  public void lock() {
    while (!compareAndSet(false, true)) {
      Thread.yield(); // give other threads CPU time while spinning
    }
  }

  public void unlock() {
    locked = false;
  }
}
