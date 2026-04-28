package com.cpz.generative.systems.lab.generative.flowfield;

/**
 * @author CPZ
 */
public class FlowFieldConfig {

    private volatile float angleFactor = 4.0f;
    private volatile float forceMagnitude = 0.1f;

    public float getAngleFactor() {
        return angleFactor;
    }

    public void setAngleFactor(float angleFactor) {
        this.angleFactor = Math.max(0f, angleFactor);
    }

    public float getForceMagnitude() {
        return forceMagnitude;
    }

    public void setForceMagnitude(float forceMagnitude) {
        this.forceMagnitude = Math.max(0f, forceMagnitude);
    }
}
