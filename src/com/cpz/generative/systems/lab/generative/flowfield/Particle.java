package com.cpz.generative.systems.lab.generative.flowfield;

import processing.core.PVector;

/**
 * @author CPZ
 */
public final class Particle {

    private final PVector position;
    private final PVector velocity;
    private final PVector acceleration;
    private final PVector previousPosition;

    public Particle(float x, float y) {
        this.position = new PVector(x, y);
        this.velocity = new PVector();
        this.acceleration = new PVector();
        this.previousPosition = new PVector(x, y);
    }

    public void applyForce(PVector force) {
        acceleration.add(force);
    }

    public void update(float maxSpeed) {
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        position.add(velocity);
        acceleration.mult(0);
    }

    public void wrap(float width, float height) {
        boolean wrapped = false;
        if (position.x < 0) {
            position.x = width - 1;
            wrapped = true;
        } else if (position.x >= width) {
            position.x = 0;
            wrapped = true;
        }
        if (position.y < 0) {
            position.y = height - 1;
            wrapped = true;
        } else if (position.y >= height) {
            position.y = 0;
            wrapped = true;
        }
        if (wrapped) syncPreviousPosition();
    }

    public void syncPreviousPosition() {
        previousPosition.set(position);
    }

    public PVector getPosition() {
        return position;
    }

    public PVector getPreviousPosition() {
        return previousPosition;
    }
}