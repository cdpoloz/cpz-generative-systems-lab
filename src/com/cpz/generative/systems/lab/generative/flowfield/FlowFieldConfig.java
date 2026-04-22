package com.cpz.generative.systems.lab.generative.flowfield;

import static com.cpz.generative.systems.lab.main.Launcher.PROPS;

/**
 * @author CPZ
 */
public final class FlowFieldConfig {

    public final int width;
    public final int height;
    public final int particleCount;
    public final float noiseScale;
    public final float fieldStrength;
    public final float maxSpeed;
    public final float timeStep;
    public final int backgroundAlpha;
    public final float strokeWeight;

    public FlowFieldConfig(
            int width,
            int height,
            int particleCount,
            float noiseScale,
            float fieldStrength,
            float maxSpeed,
            float timeStep,
            int backgroundAlpha,
            float strokeWeight
    ) {
        this.width = width;
        this.height = height;
        this.particleCount = particleCount;
        this.noiseScale = noiseScale;
        this.fieldStrength = fieldStrength;
        this.maxSpeed = maxSpeed;
        this.timeStep = timeStep;
        this.backgroundAlpha = backgroundAlpha;
        this.strokeWeight = strokeWeight;
    }

    public static FlowFieldConfig defaultConfig(int width, int height) {
        return new FlowFieldConfig(
                (int)(width * Float.parseFloat(PROPS.getProperty("screen.scale.factor"))),
                (int)(height * Float.parseFloat(PROPS.getProperty("screen.scale.factor"))),
                2500,
                0.0025f,
                0.35f,
                2.0f,
                0.003f,
                12,
                0.5f
        );
    }
}
