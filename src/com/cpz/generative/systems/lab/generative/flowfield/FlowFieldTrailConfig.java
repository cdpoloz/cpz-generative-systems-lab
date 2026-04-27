package com.cpz.generative.systems.lab.generative.flowfield;

/**
 * @author CPZ
 */
public class FlowFieldTrailConfig {

    private int particlesAmount = 10;

    private float noiseScale = 0.001f;
    private float time = 0.0f;
    private float timeStep = 0.02f;
    private float maxSpeed = 2.5f;

    private int color1;
    private int color2;

    private float alphaMax = 255f;
    private float strokeWeightMax = 0.5f;

    public int getParticlesAmount() {
        return particlesAmount;
    }

    public void setParticlesAmount(int particlesAmount) {
        this.particlesAmount = Math.max(1, particlesAmount);
    }

    public float getNoiseScale() {
        return noiseScale;
    }

    public void setNoiseScale(float noiseScale) {
        this.noiseScale = Math.max(0.0f, noiseScale);
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = Math.max(0.0f, time);
    }

    public float getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(float timeStep) {
        this.timeStep = Math.max(0.0f, timeStep);
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getColor1() {
        return color1;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public float getAlphaMax() {
        return alphaMax;
    }

    public void setAlphaMax(float alphaMax) {
        this.alphaMax = Math.clamp(alphaMax, 0.0f, 255.0f);
    }

    public float getStrokeWeightMax() {
        return strokeWeightMax;
    }

    public void setStrokeWeightMax(float strokeWeightMax) {
        this.strokeWeightMax = Math.max(0.0f, strokeWeightMax);
    }
}
