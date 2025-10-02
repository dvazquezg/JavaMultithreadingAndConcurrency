package threads.section4.example1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LatencyOptimizationRecolorImage {
  public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
  public static final String OUTPUT_FILE = "./out/many-flowers.jpg";

  public static void main(String[] args) throws IOException {
    BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
    BufferedImage resultImage =
        new BufferedImage(
            originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

    long startTime = System.currentTimeMillis();

    // single threaded solution
    // recolorSingleThreaded(originalImage, resultImage);

    // multithreaded solution
    int numberOfThreads = 4;
    recolorMultithreaded(originalImage, resultImage, numberOfThreads);

    // compute time
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;

    // save resulting image
    File outputFile = new File(OUTPUT_FILE);
    ImageIO.write(resultImage, "jpg", outputFile);

    System.out.println("Time to process: " + duration);
  }

  public static void recolorMultithreaded(
      BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
    List<Thread> threads = new ArrayList<>();
    int width = originalImage.getWidth();
    int height = originalImage.getHeight() / numberOfThreads;
    int extraRows = originalImage.getHeight() % numberOfThreads; // extra items to distribute
    // System.out.println(extraRows);
    // since integer division may leave a few bottom pixel rows out, include them among threads
    // Sample: original height = 10 and threads = 4 -> section height: 10/4 = 2, remainder = 10%4 =
    // 2
    // Distribute remaining rows among threads
    int xOrigin = 0;
    int yOrigin = 0;
    for (int i = 0; i < numberOfThreads; i++) {
      int yOriginFinal = yOrigin;
      int blockHeight = height + (i < extraRows ? 1 : 0); // distribute extra rows in curr block
      Thread thread =
          new Thread(
              () -> {
                recolorImage(
                    originalImage,
                    resultImage,
                    xOrigin,
                    yOriginFinal,
                    width,
                    yOriginFinal + blockHeight);
              });
      threads.add(thread);
      yOrigin += blockHeight;
    }

    for (Thread thread : threads) {
      thread.start();
    }

    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
      }
    }
  }

  public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
    recolorImage(
        originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
  }

  /**
   * Recolor square section of image starting from top-left corner and given width and length
   *
   * @param originalImage original image
   * @param resultImage output image
   * @param xCorner x coordinate of top left corner image section
   * @param yCorner y coordinate of top left corner image section
   * @param width section width
   * @param height section height
   */
  public static void recolorImage(
      BufferedImage originalImage,
      BufferedImage resultImage,
      int xCorner,
      int yCorner,
      int width,
      int height) {
    for (int x = xCorner; x < xCorner + width && x < originalImage.getWidth(); x++) {
      for (int y = yCorner; y < yCorner + height && y < originalImage.getHeight(); y++) {
        recolorPixel(originalImage, resultImage, x, y);
      }
    }
  }

  /**
   * Recolors given pixel
   *
   * @param originalImage original image
   * @param resultImage colored image
   * @param x pixel coordinate
   * @param y pixel coordinate
   */
  public static void recolorPixel(
      BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
    int rgb = originalImage.getRGB(x, y);

    int red = getRed(rgb);
    int green = getGreen(rgb);
    int blue = getBlue(rgb);

    int newRed;
    int newGreen;
    int newBlue;

    if (isShadeOfGray(red, green, blue)) {
      newRed = Math.min(255, red + 10);
      newGreen = Math.max(0, green - 80);
      newBlue = Math.max(0, blue - 20);
    } else {
      newRed = red;
      newGreen = green;
      newBlue = blue;
    }
    int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
    setRGB(resultImage, x, y, newRGB);
  }

  public static void setRGB(BufferedImage image, int x, int y, int rgb) {
    image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
  }

  public static boolean isShadeOfGray(int red, int green, int blue) {
    return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
  }

  public static int createRGBFromColors(int red, int green, int blue) {
    int rgb = 0;
    rgb |= blue;
    rgb |= green << 8;
    rgb |= red << 16;
    rgb |= 0xFF000000; // alpha/transparency value
    return rgb;
  }

  public static int getBlue(int rgb) {
    return rgb & 0x000000FF;
  }

  public static int getGreen(int rgb) {
    return (rgb & 0x0000FF00) >> 8; // Note: 0xFF is 11111111 in binary
  }

  public static int getRed(int rgb) {
    return (rgb & 0x00FF0000) >> 16;
  }
}
