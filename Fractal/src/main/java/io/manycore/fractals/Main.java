package io.manycore.fractals;


public class Main {
    public static void main(String[] args) {
        MandelbrotSetToFile m = new MandelbrotSetToFile(3840, 2160);
        long start = System.currentTimeMillis();
        m.action();
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + "ms");
    }
}