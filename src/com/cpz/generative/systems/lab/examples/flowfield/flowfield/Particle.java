package com.cpz.generative.systems.lab.examples.flowfield.flowfield;

/**
 * @author CPZ
 */
public final class Particle {

    private float previousX, previousY, x, y, velX, velY;
    private float noiseScale, time, timeStep, maxSpeed, discontinuityThreshold;
    private boolean skipDraw;

    public Particle(float x, float y) {
        this.x = x;
        this.y = y;
        this.previousX = x;
        this.previousY = y;
        velX = 0;
        velY = 0;
    }

    public void updatePositionByAcceleration(float width, float height, float ax, float ay) {
        previousX = x;
        previousY = y;

        velX += ax;
        velY += ay;
        float speed = (float) Math.sqrt(velX * velX + velY * velY);
        if (speed > maxSpeed) {
            velX = (velX / speed) * maxSpeed;
            velY = (velY / speed) * maxSpeed;
        }

        x += velX;
        y += velY;
        if (x < 0) x = width - 1;
        else if (x >= width) x = 0;
        if (y < 0) y = height - 1;
        else if (y >= height) y = 0;

        time += timeStep;
    }

    public void followPosition(float targetX, float targetY) {
        previousX = x;
        previousY = y;

        x = targetX;
        y = targetY;

        time += timeStep;
    }

    public void updateSkipDraw() {
        float dx = x - previousX;
        float dy = y - previousY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        skipDraw = distance > discontinuityThreshold;
    }

    public boolean isSkipDraw() {
        return skipDraw;
    }

    public float getPreviousX() {
        return previousX;
    }

    public float getPreviousY() {
        return previousY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setNoiseScale(float noiseScale) {
        this.noiseScale = noiseScale;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setTimeStep(float timeStep) {
        this.timeStep = timeStep;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
        this.discontinuityThreshold = maxSpeed * 2;
    }

    public float getNoiseScale() {
        return noiseScale;
    }

    public float getTime() {
        return time;
    }
}
