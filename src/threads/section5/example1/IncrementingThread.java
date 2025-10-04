package threads.section5.example1;

public class IncrementingThread extends Thread {
  InventoryCounter inventoryCounter;

  public IncrementingThread(InventoryCounter inventoryCounter) {
    this.inventoryCounter = inventoryCounter;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10000; i++) {
      this.inventoryCounter.increment();
    }
  }
}
