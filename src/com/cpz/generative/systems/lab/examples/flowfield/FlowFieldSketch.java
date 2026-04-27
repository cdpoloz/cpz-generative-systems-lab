package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.config.ConfigFlowFieldSketch;
import com.cpz.generative.systems.lab.generative.flowfield.*;
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

    private final FlowFieldTrailConfig flowFieldTrailConfig;
    private final List<ParticleTrail> particleTrails;
    private int trailsAmount;

    private FlowField flowField;

    public FlowFieldSketch() {
        LOG.info("Starting sketch constructor: " + this.getClass().getSimpleName());
        flowFieldTrailConfig = new FlowFieldTrailConfig();
        particleTrails = new ArrayList<>();
        LOG.info("Finishing sketch constructor: " + this.getClass().getSimpleName());
    }

    public void settings() {
        ConfigFlowFieldSketch.settings(this);
    }

    public void setup() {
        ConfigFlowFieldSketch.setup(this);
        LOG.info("Starting final setup");
        // flow field
        flowField = new FlowField(this, 4.0f, 0.1f);
        // particle trails
        flowFieldTrailConfig.setAlphaMax(255f);
        flowFieldTrailConfig.setStrokeWeightMax(0.5f);
        flowFieldTrailConfig.setNoiseScale(0.001f);
        flowFieldTrailConfig.setTime(0.0f);
        flowFieldTrailConfig.setTimeStep(0.02f);
        flowFieldTrailConfig.setMaxSpeed(2.5f);
        flowFieldTrailConfig.setColor1(Colors.argb(255, 255, 204, 0));
        flowFieldTrailConfig.setColor2(Colors.argb(255, 255, 0, 102));
        trailsAmount = 5000;
        initializeTrails(flowFieldTrailConfig, trailsAmount);
        LOG.info("Finishing final setup");
    }

    @Override
    public void draw() {
        background(0);
        // particles
        particleTrails.forEach(pt -> updateTrail(flowField, pt));
        particleTrails.forEach(this::drawParticleTrail);
    }

    private void initializeTrails(FlowFieldTrailConfig flowFieldTrailConfig, int trailsAmount) {
        particleTrails.clear();
        for (int i = 0; i < trailsAmount; i++) {
            float x = random(width);
            float y = random(height);
            ParticleTrail pt = ParticleTrailFactory.createParticleTrail(x, y, flowFieldTrailConfig);
            particleTrails.add(pt);
        }
    }

    private void updateTrail(FlowField flowField, ParticleTrail particleTrail) {
        // updating the leading particle trail element position
        ParticleTrailElement leadingElement = particleTrail.getLeadingElement();
        float x = leadingElement.particle().getX();
        float y = leadingElement.particle().getY();
        float noiseScale = leadingElement.particle().getNoiseScale();
        float time = leadingElement.particle().getTime();
        float ax = flowField.getForceX(x, y, noiseScale, time);
        float ay = flowField.getForceY(x, y, noiseScale, time);
        particleTrail.updateWithLeaderForce(width, height, ax, ay);
    }

    private void drawParticleTrail(ParticleTrail particleTrail) {
        pushStyle();
        List<ParticleTrailElement> particleTrailElements = particleTrail.getParticleTrailElements();
        for (ParticleTrailElement element : particleTrailElements) drawElement(element);
        popStyle();
    }

    private void drawElement(ParticleTrailElement element) {
        // checking if the particle must be drawn or not
        if (element.particle().isSkipDraw()) return;
        // drawing element on screen
        float previousX = element.particle().getPreviousX();
        float previousY = element.particle().getPreviousY();
        float x = element.particle().getX();
        float y = element.particle().getY();
        strokeWeight(element.style().getStrokeWeight());
        stroke(element.style().getColor());
        line(previousX, previousY, x, y);
    }

    @Override
    public void keyReleased() {
        if (key == 'r') initializeTrails(flowFieldTrailConfig, trailsAmount);
    }

}
