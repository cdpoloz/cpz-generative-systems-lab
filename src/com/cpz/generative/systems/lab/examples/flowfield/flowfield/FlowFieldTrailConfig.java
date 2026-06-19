package com.cpz.generative.systems.lab.examples.flowfield.flowfield;

/**
 * @author CPZ
 */
public class FlowFieldTrailConfig {

    private int particlesAmount = 10;
    private int trailsAmount = 100;
    private float noiseScale = 0.001f;
    private float time = 0.0f;
    private float timeStep = 0.02f;
    private float maxSpeed = 2.5f;

    private int color1;
    private int color2;

    private float alphaMax = 255f;
    private float strokeWeight = 0.5f;

    private volatile boolean styleUpdateRequested;
    private volatile boolean noiseScaleUpdateRequested;
    private volatile boolean timeStepUpdateRequested;
    private volatile boolean maxSpeedUpdateRequested;

    public void requestStyleUpdate() {
        styleUpdateRequested = true;
    }

    public boolean consumeStyleUpdateRequested() {
        boolean requested = styleUpdateRequested;
        styleUpdateRequested = false;
        return requested;
    }

    public void requestNoiseScaleUpdate() {
        noiseScaleUpdateRequested = true;
    }

    public boolean consumeNoiseScaleUpdateRequested() {
        boolean requested = noiseScaleUpdateRequested;
        noiseScaleUpdateRequested = false;
        return requested;
    }

    public void requestTimeStepUpdate() {
        timeStepUpdateRequested = true;
    }

    public boolean consumeTimeStepUpdateRequested() {
        boolean requested = timeStepUpdateRequested;
        timeStepUpdateRequested = false;
        return requested;
    }

    public void requestMaxSpeedUpdate() {
        maxSpeedUpdateRequested = true;
    }

    public boolean consumeMaxSpeedUpdateRequested() {
        boolean requested = maxSpeedUpdateRequested;
        maxSpeedUpdateRequested = false;
        return requested;
    }

    public int getTrailsAmount() {
        return trailsAmount;
    }

    public void setTrailsAmount(int trailsAmount) {
        this.trailsAmount = Math.max(1, trailsAmount);
    }

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
        this.maxSpeed = Math.max(0.0f, maxSpeed);
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

    public float getStrokeWeight() {
        return strokeWeight;
    }

    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = Math.max(0.0f, strokeWeight);
    }
}
