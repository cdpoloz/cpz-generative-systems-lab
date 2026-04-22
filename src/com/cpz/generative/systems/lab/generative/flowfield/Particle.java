package com.cpz.generative.systems.lab.generative.flowfield;

/**
 * @author CPZ
 */
public final class Particle {

    private float previousX, previousY, x, y, velX, velY;
    private float noiseScale, time, timeStep, maxSpeed;
    private boolean wrapped;

    public Particle(float x, float y) {
        this.x = x;
        this.y = y;
        this.previousX = x;
        this.previousY = y;
        velX = 0;
        velY = 0;
    }

    public void update(float width, float height, float ax, float ay) {
        // saving previous position
        previousX = x;
        previousY = y;
        // updating velocity value
        velX += ax;
        velY += ay;
        float speed = (float) Math.sqrt(velX * velX + velY * velY);
        if (speed > maxSpeed) {
            velX = (velX / speed) * maxSpeed;
            velY = (velY / speed) * maxSpeed;
        }
        // updating position
        x += velX;
        y += velY;
        // checking if position is inside the frame
        wrapped = false;
        if (x < 0) {
            x = width - 1;
            wrapped = true;
        } else if (x >= width) {
            x = 0;
            wrapped = true;
        }
        if (y < 0) {
            y = height - 1;
            wrapped = true;
        } else if (y >= height) {
            y = 0;
            wrapped = true;
        }
        // updating time/step
        time += timeStep;
    }

    public boolean isWrapped() {
        return wrapped;
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
    }

    public float getNoiseScale() {
        return noiseScale;
    }

    public float getTime() {
        return time;
    }
}