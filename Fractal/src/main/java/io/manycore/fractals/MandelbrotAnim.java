package io.manycore.fractals;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class MandelbrotAnim extends JFrame {

    private final int width;
    private final int height;


    private final AnimationPanel animationPanel;

    private long lastTime = System.nanoTime();
    private int frameCount = 0;
    private int currentFps = -1;

    public MandelbrotAnim(int width, int height) {
        this.width = width;
        this.height = height;

        this.animationPanel = new AnimationPanel();

        setTitle("BufferedImage Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        add(animationPanel);
        setVisible(true);

    }
    public void animate(int numberFramesToAnimate) {
        double zoom = 100;  // org example: 2500
        int maxIter = 570;  // org example: 570

        for (int i = 0; i < numberFramesToAnimate; i++) {
            MandelbrotSet m = new MandelbrotSet(width, height, zoom, maxIter);

            // Start timing in nanoseconds
            long start = System.nanoTime();
            m.drawFractal(); // Ensure this blocks until fully done
            long end = System.nanoTime();

            // Convert the elapsed time from ns to ms
            double tpfMs = (end - start) / 1_000_000.0;

            // Rolling FPS over one second

            // Get the rendered image and draw info text
            BufferedImage bi = m.getImage();
            setFrameText(bi, i, (int) zoom, tpfMs);

            // Part un//

            computeFps();
            // Update the panel
            this.animationPanel.setBufferedImage(bi);
            this.animationPanel.repaint();
            zoom++;
        }
    }

    private void computeFps() {
        long now = System.nanoTime();
        frameCount++;

        // 1 second = 1_000_000_000 nanoseconds
        if ((now - lastTime) >= 1_000_000_000) { 
            currentFps = frameCount;
            frameCount = 0;
            lastTime = now;
        }
    }

    /**
     * Renders the time-per-frame (ms) and FPS onto the image.
     */
    private void setFrameText(BufferedImage bi, int frameIndex, int zoom, double tpfMs) {
        Graphics2D g2d = bi.createGraphics();
        g2d.setFont(new Font("Arial", Font.BOLD, 30));

        // Format the time-per-frame nicely to 2 decimal places
        String tpfString = String.format("%.2f", tpfMs);

        String text = STR."Frame: \{frameIndex}  Zoom: \{zoom}  Time per frame (ms): \{tpfString}  FPS : \{currentFps}";

        g2d.drawString(text, 10, 40);
        g2d.dispose();  // Clean up graphics context
    }

}
