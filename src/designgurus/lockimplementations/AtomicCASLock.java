package designgurus.lockimplementations;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicCASLock {
  private final AtomicBoolean locked = new AtomicBoolean(false);

  // Try to acquire the lock
  public void lock() {
    while (!locked.compareAndSet(false, true)) {
      // Spin-wait (busy waiting)
      Thread.yield(); // give other threads a chance
    }
  }

  // Release the lock
  public void unlock() {
    locked.set(false);
  }
}
