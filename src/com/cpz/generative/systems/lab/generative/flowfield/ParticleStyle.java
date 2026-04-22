package com.cpz.generative.systems.lab.generative.flowfield;


import com.cpz.utils.color.Colors;

/**
 * @author CPZ
 */
public class ParticleStyle {

    private int c1, c2, c;
    private float strokeWeight, alpha;

    public ParticleStyle() {
        strokeWeight = 1f;
    }

    public void update(float fColor, float fAlpha) {
        c = Colors.lerpColor(c1, c2, fColor);
        c = Colors.argb((int) (alpha * fAlpha), Colors.red(c), Colors.green(c), Colors.blue(c));
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public int getC() {
        return c;
    }

    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public float getStrokeWeight() {
        return strokeWeight;
    }
}
