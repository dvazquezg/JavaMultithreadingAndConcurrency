package threads.section5.example1;

/**
 * The ++/-- operators performs 3 actions 1. gets current value 2. computes new value 3. stores new
 * value Race condition: Without atomicity, it may read/hold stale value.
 */
public class InventoryCounter {
  private int items; // items member is shared among threads (stored in heap)

  public InventoryCounter() {
    this.items = 0;
  }

  public void increment() {
    this.items++;
  }

  public void decrement() {
    this.items--;
  }

  public int getItems() {
    return items;
  }
}
