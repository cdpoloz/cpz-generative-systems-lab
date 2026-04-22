package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.config.ConfigFlowFieldSketch;
import com.cpz.generative.systems.lab.generative.flowfield.Particle;
import com.cpz.generative.systems.lab.generative.flowfield.ParticleStyle;
import com.cpz.utils.color.Colors;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.cpz.generative.systems.lab.main.Launcher.LOG;

/**
 * @author CPZ
 */
public class FlowFieldSketch extends PApplet {

    public static final Properties FLOW_FIELD_SKETCH_PROPS = new Properties();

    private final List<Particle> particles;
    private final List<ParticleStyle> particleStyles;
    private int particlesAmount;
    private float fAngle;

    public FlowFieldSketch() {
        LOG.info("Starting sketch constructor: " + this.getClass().getSimpleName());
        particles = new ArrayList<>();
        particleStyles = new ArrayList<>();
        LOG.info("Finishing sketch constructor: " + this.getClass().getSimpleName());
    }

    public void settings() {
        ConfigFlowFieldSketch.settings(this);
    }

    public void setup() {
        ConfigFlowFieldSketch.setup(this);
        LOG.info("Starting final setup");
        // particles
        particlesAmount = 4000;
        for (int i = 0; i < particlesAmount; i++) {
            // particle
            Particle particle = new Particle(random(width), random(height));
            particle.setNoiseScale(0.001f);
            particle.setTime(0f);
            particle.setTimeStep(0.02f);
            particle.setMaxSpeed(2.5f);
            particles.add(particle);
            // particle style
            ParticleStyle particleStyle = new ParticleStyle();
            particleStyle.setC1(Colors.argb(255, 255, 255, 0));
            particleStyle.setC2(Colors.argb(255, 255, 0, 255));
            particleStyle.setAlpha(255);
            particleStyle.setStrokeWeight(0.5f);
            particleStyles.add(particleStyle);
        }
        fAngle = 4.0f;
        // initial background
        background(0);
        fill(0, 12);
        noStroke();
        LOG.info("Finishing final setup");
    }

    @Override
    public void draw() {
        // background
        rect(0, 0, width, height);
        // particles
        update(particles, particleStyles);
        draw(particles, particleStyles);
    }

    private void update(List<Particle> particles, List<ParticleStyle> particleStyles) {
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            updateParticle(particle);
            float fColor = particle.getX() / width;
            //float fAlpha = map(i, 0, particles.size(), 0, 1);
            float fAlpha = 0;
            float y = particle.getY();
            if (y < height * 0.5f) fAlpha = map(y, 0, height * 0.5f, 0, 1);
            else fAlpha = map(y, height * 0.5f, height, 1, 0);

            ParticleStyle particleStyle = particleStyles.get(i);
            updateParticleStyle(particleStyle, fColor, fAlpha);
        }
    }

    private void updateParticle(Particle particle) {
        // updating particle position
        float x = particle.getX();
        float y = particle.getY();
        float noiseScale = particle.getNoiseScale();
        float time = particle.getTime();
        float angle = noise(x * noiseScale, y * noiseScale, time) * TWO_PI * fAngle;
        float ax = (float) (Math.cos(angle) * 0.1f);
        float ay = (float) (Math.sin(angle) * 0.1f);
        particle.update(width, height, ax, ay);
    }

    private void updateParticleStyle(ParticleStyle particleStyle, float fColor, float fAlpha) {
        // updating particle style
        particleStyle.update(fColor, fAlpha);
    }

    private void draw(List<Particle> particles, List<ParticleStyle> particleStyles) {
        for (int i = 0; i < particles.size(); i++) {
            drawParticle(particles.get(i), particleStyles.get(i));
        }
    }

    private void drawParticle(Particle particle, ParticleStyle particleStyle) {
        // drawing particle on screen
        float previousX = particle.getPreviousX();
        float previousY = particle.getPreviousY();
        float x = particle.getX();
        float y = particle.getY();
        pushStyle();
        strokeWeight(particleStyle.getStrokeWeight());
        stroke(particleStyle.getC());
        if (!particle.isWrapped()) line(previousX, previousY, x, y);
        popStyle();
    }

}
