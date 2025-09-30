package threads.section2.project1;

public class DescendingHackerThread extends HackerThread {
  public static final int MAX_PASSWORD = 9999;

  public DescendingHackerThread(Vault vault) {
    super(vault);
  }

  @Override
  public void run() {
    for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
      if (this.vault.isCorrectPassword(guess)) {
        System.out.println(this.getName() + " guessed the password " + guess);
        System.exit(0);
      }
    }
  }
}
