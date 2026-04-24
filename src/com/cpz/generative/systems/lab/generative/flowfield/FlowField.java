package com.cpz.generative.systems.lab.generative.flowfield;

import processing.core.PApplet;

/**
 * @author CPZ
 */
public final class FlowField {

    private final PApplet sketch;
    private float angleFactor;
    private float forceMagnitude;

    public FlowField(PApplet sketch) {
        this.sketch = sketch;
        // default values
        this.angleFactor = 4.0f;
        this.forceMagnitude = 0.1f;
    }

    public FlowField(PApplet sketch, float angleFactor, float forceMagnitude) {
        this.sketch = sketch;
        this.angleFactor = angleFactor;
        this.forceMagnitude = forceMagnitude;
    }

    public float getForceX(float x, float y, float noiseScale, float time) {
        float angle = getAngle(x, y, noiseScale, time);
        return (float) Math.cos(angle) * forceMagnitude;
    }

    public float getForceY(float x, float y, float noiseScale, float time) {
        float angle = getAngle(x, y, noiseScale, time);
        return (float) Math.sin(angle) * forceMagnitude;
    }

    private float getAngle(float x, float y, float noiseScale, float time) {
        return sketch.noise(x * noiseScale, y * noiseScale, time)
                * PApplet.TWO_PI
                * angleFactor;
    }

    public void setAngleFactor(float angleFactor) {
        this.angleFactor = angleFactor;
    }

    public void setForceMagnitude(float forceMagnitude) {
        this.forceMagnitude = forceMagnitude;
    }

}