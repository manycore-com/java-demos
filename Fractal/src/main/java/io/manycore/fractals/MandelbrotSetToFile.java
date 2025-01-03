package io.manycore.fractals;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MandelbrotSetToFile {

    private final int width;
    private final int height;
    private final int MAX_ITER = 570;
    private final double ZOOM = 2500;
    private BufferedImage image;

    public MandelbrotSetToFile(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void action() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        drawFractal();
        saveImage();
    }

    public void drawFractal() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - 400) / ZOOM;
                double cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                image.setRGB(x, y, iter | (iter << 8));
            }
        }
    }

    private void saveImage() {
        try {
            File outputfile = new File("mandelbrot.bmp");
            ImageIO.write(image, "bmp", outputfile);
            System.out.println("Image successfully saved: " + outputfile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error: Could not save image.");
            e.printStackTrace();
        }
    }

}
