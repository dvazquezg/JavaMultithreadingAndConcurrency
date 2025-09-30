package threads.section2.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
  public static final int MAX_PASSWORD = 9999;

  public static void main(String[] args) {
    Random random = new Random();
    Vault vault = new Vault(random.nextInt(MAX_PASSWORD) + 1);

    List<Thread> threads = new ArrayList<>();
    threads.add(new AscendingHackerThread(vault));
    threads.add(new DescendingHackerThread(vault));
    threads.add(new PoliceThread());

    // start threads
    for (Thread t : threads) {
      t.start();
    }
  }
}
