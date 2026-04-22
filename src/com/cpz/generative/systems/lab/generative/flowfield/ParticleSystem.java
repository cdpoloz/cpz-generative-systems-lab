package com.cpz.generative.systems.lab.generative.flowfield;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author CPZ
 */
public class ParticleSystem {

    private final PApplet applet;
    private final FlowFieldConfig config;
    private final List<Particle> particles;

    public ParticleSystem(PApplet applet, FlowFieldConfig config) {
        this.applet = applet;
        this.config = config;
        this.particles = new ArrayList<>();
        initializeParticles();
    }

    private void initializeParticles() {
        particles.clear();
        for (int i = 0; i < config.particleCount; i++) {
            float x = applet.random(config.width);
            float y = applet.random(config.height);
            particles.add(new Particle(x, y));
        }
    }

    public void update(FlowField flowField, float time) {
        for (Particle particle : particles) {
            particle.syncPreviousPosition();
            float x = particle.getPosition().x;
            float y = particle.getPosition().y;
            PVector force = flowField.lookup(x, y, time);
            particle.applyForce(force);
            particle.update(config.maxSpeed);
            particle.wrap(config.width, config.height);
        }
    }

    public List<Particle> getParticles() {
        return Collections.unmodifiableList(particles);
    }

    public void reset() {
        initializeParticles();
    }

}
