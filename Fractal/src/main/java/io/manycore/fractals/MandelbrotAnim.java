package io.manycore.fractals;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotAnim extends JFrame {

    private final int width;
    private final int height;

    private final AnimationPanel animationPanel;

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
            long start = System.currentTimeMillis();
            m.drawFractal();
            long end = System.currentTimeMillis();
            long tot = end - start;
            if (0 == tot) {
                tot = 1;
            }
            long fps = 1000 / tot;

            BufferedImage bi = m.getImage();
            setFrameText(bi, i, (int)zoom, (int)fps);
            this.animationPanel.setBufferedImage(bi);
            this.animationPanel.repaint();

            zoom++;
        }
    }

    private void setFrameText(BufferedImage bi, int i, int zoom, int fps) {
        Graphics2D g2d = bi.createGraphics();
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        String text = "Frame: " + i + "  Zoom: " + zoom + "  Fps: " + fps;
        g2d.drawString(text, 10, 40);
    }

}
