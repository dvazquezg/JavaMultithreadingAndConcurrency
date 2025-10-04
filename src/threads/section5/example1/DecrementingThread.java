package threads.section5.example1;

public class DecrementingThread extends Thread {
  InventoryCounter inventoryCounter;

  public DecrementingThread(InventoryCounter inventoryCounter) {
    this.inventoryCounter = inventoryCounter;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10000; i++) {
      this.inventoryCounter.decrement();
    }
  }
}
