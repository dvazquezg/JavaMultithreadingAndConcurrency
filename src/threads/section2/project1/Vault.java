package threads.section2.project1;

public class Vault {
  private final int password;

  public Vault(int password) {
    this.password = password;
  }

  public boolean isCorrectPassword(int guess) {
    try {
      Thread.sleep(4);
    } catch (Exception e) {
      System.out.println("Unexpected error happened " + e.getMessage());
    }
    return this.password == guess;
  }
}
