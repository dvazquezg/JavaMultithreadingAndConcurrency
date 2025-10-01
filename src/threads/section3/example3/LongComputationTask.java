package threads.section3.example3;

import java.math.BigInteger;

public class LongComputationTask implements Runnable {
  private final BigInteger base;
  private final BigInteger power;

  public LongComputationTask(BigInteger base, BigInteger power) {
    this.base = base;
    this.power = power;
  }

  @Override
  public void run() {
    System.out.println(this.base + "^" + this.power + " = " + pow(this.base, this.power));
  }

  private BigInteger pow(BigInteger base, BigInteger power) {
    BigInteger result = BigInteger.ONE;
    for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
      result = result.multiply(base);
    }
    return result;
  }
}
