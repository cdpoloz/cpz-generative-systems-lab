package com.cpz.generative.systems.lab.generative.flowfield;

import com.cpz.utils.color.Colors;

/**
 * @author CPZ
 */
public class ParticleStyle {

    private int color1, color2, color;
    private float strokeWeight;

    public ParticleStyle() {
        strokeWeight = 1f;
    }

    public ParticleStyle(ParticleStyle particleStyle) {
        this.color1 = particleStyle.color1;
        this.color2 = particleStyle.color2;
        this.color = particleStyle.color;
        this.strokeWeight = particleStyle.strokeWeight;
    }

    public void updateCurrentColor(float fColor) {
        color = Colors.lerpColor(color1, color2, fColor);
    }

    public void setColors(int color1, int color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    public int getColor() {
        return color;
    }

    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public float getStrokeWeight() {
        return strokeWeight;
    }
}
