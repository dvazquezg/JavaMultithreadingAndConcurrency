package threads.section3.example4;

import java.math.BigInteger;

public class FactorialThread extends Thread {
  private final long inputNumber;
  private BigInteger result = BigInteger.ZERO;
  private boolean isFinished = false;

  public FactorialThread(long inputNumber) {
    this.inputNumber = inputNumber;
  }

  @Override
  public void run() {
    this.result = factorial(this.inputNumber);
    this.isFinished = true;
  }

  public BigInteger factorial(long n) {
    BigInteger tempResult = BigInteger.ONE;

    for (long i = n; i > 0; i--) {
      if (this.isInterrupted()) {
        return BigInteger.ZERO;
      }
      tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
    }
    return tempResult;
  }

  public BigInteger getResult() {
    return result;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public long getInputNumber() {
    return inputNumber;
  }
}
