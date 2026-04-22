package com.cpz.generative.systems.lab.window;

/**
 * @author CPZ
 */
public record WindowOffsets(int left, int right, int top, int bottom) {

    public int totalHorizontal() { return left + right; }

    public int totalVertical() { return top + bottom; }

}
