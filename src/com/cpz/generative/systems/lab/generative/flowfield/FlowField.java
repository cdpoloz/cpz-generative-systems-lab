package com.cpz.generative.systems.lab.generative.flowfield;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author CPZ
 */
public final class FlowField {

    private final PApplet sketch;
    private final FlowFieldConfig config;

    public FlowField(PApplet sketch, FlowFieldConfig config) {
        this.sketch = sketch;
        this.config = config;
    }

    public PVector lookup(float x, float y, float time) {
        float nx = x * config.noiseScale;
        float ny = y * config.noiseScale;
        float angle = sketch.noise(nx, ny, time) * PApplet.TWO_PI * 4.0f;
        PVector force = PVector.fromAngle(angle);
        force.mult(config.fieldStrength);
        return force;
    }
}