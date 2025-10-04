package threads.section5.example1;

public class Store {
  public static void main(String[] args) throws InterruptedException {
    InventoryCounter inventoryCounter = new InventoryCounter(); // shared object (resource)
    IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
    DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

    incrementingThread.start();
    decrementingThread.start();

    incrementingThread.join(); // wait for thread to finish
    decrementingThread.join(); // wait for thread to finish

    // Race condition happens. Incorrect output is computed.
    System.out.println("Total inventory: " + inventoryCounter.getItems());
  }
}
