package com.cpz.generative.systems.lab.generative.flowfield;

/**
 * @author CPZ
 */
public final class FlowField {

    private final NoiseSource3D noiseSource;
    private final FlowFieldConfig config;

    public FlowField(NoiseSource3D noiseSource, FlowFieldConfig config) {
        if (noiseSource == null) throw new IllegalArgumentException("noiseSource must not be null");
        if (config == null) throw new IllegalArgumentException("config must not be null");
        this.noiseSource = noiseSource;
        this.config = config;
    }

    public float getForceX(float x, float y, float noiseScale, float time) {
        float angle = getAngle(x, y, noiseScale, time);
        return (float) Math.cos(angle) * config.getForceMagnitude();
    }

    public float getForceY(float x, float y, float noiseScale, float time) {
        float angle = getAngle(x, y, noiseScale, time);
        return (float) Math.sin(angle) * config.getForceMagnitude();
    }

    private float getAngle(float x, float y, float noiseScale, float time) {
        return noiseSource.noise(x * noiseScale, y * noiseScale, time)
                * (float) (Math.PI * 2.0)
                * config.getAngleFactor();
    }

}