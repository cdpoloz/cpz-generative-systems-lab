package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.examples.flowfield.config.ConfigFlowFieldSketch;
import com.cpz.generative.systems.lab.examples.flowfield.flowfield.FlowField;
import com.cpz.generative.systems.lab.examples.flowfield.flowfield.FlowFieldConfig;
import com.cpz.generative.systems.lab.examples.flowfield.flowfield.FlowFieldTrailConfig;
import com.cpz.generative.systems.lab.examples.flowfield.flowfield.ParticleTrail;
import com.cpz.generative.systems.lab.examples.flowfield.flowfield.ParticleTrailElement;
import com.cpz.generative.systems.lab.examples.flowfield.flowfield.ParticleTrailFactory;
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
    private final FlowFieldConfig flowFieldConfig;
    private FlowField flowField;

    public FlowFieldSketch(FlowFieldConfig flowFieldConfig, FlowFieldTrailConfig flowFieldTrailConfig) {
        LOG.info("Starting sketch constructor: " + this.getClass().getSimpleName());
        this.flowFieldConfig = flowFieldConfig;
        this.flowFieldTrailConfig = flowFieldTrailConfig;
        particleTrails = new ArrayList<>();
        LOG.info("Finishing sketch constructor: " + this.getClass().getSimpleName());
    }

    public void settings() {
        ConfigFlowFieldSketch.settings(this);
    }

    public void setup() {
        ConfigFlowFieldSketch.initialSetup(this);
        LOG.info("Starting final setup");
        flowFieldConfig.setAngleFactor(4.0f);
        flowField = new FlowField(this::noise, flowFieldConfig);

        flowFieldTrailConfig.setTrailsAmount(500);
        flowFieldTrailConfig.setParticlesAmount(100);
        flowFieldTrailConfig.setAlphaMax(255f);
        flowFieldTrailConfig.setStrokeWeight(1.0f);
        flowFieldTrailConfig.setNoiseScale(0.001f);
        flowFieldTrailConfig.setTime(0);
        flowFieldTrailConfig.setTimeStep(0.02f);
        flowFieldTrailConfig.setMaxSpeed(2.5f);
        flowFieldTrailConfig.setColor1(Colors.argb(255, 255, 204, 0));
        flowFieldTrailConfig.setColor2(Colors.argb(255, 255, 0, 102));
        initializeTrails(flowFieldTrailConfig);
        LOG.info("Finishing final setup");
    }

    @Override
    public void draw() {
        background(0);

        if (flowFieldTrailConfig.consumeStyleUpdateRequested()) updateTrailStyles(flowFieldTrailConfig);
        if (flowFieldTrailConfig.consumeNoiseScaleUpdateRequested()) updateNoiseScale(flowFieldTrailConfig);
        if (flowFieldTrailConfig.consumeTimeStepUpdateRequested()) updateTimeStep(flowFieldTrailConfig);
        if (flowFieldTrailConfig.consumeMaxSpeedUpdateRequested()) updateMaxSpeed(flowFieldTrailConfig);

        particleTrails.parallelStream().forEach(pt -> updateTrail(flowField, pt));

        pushStyle();
        strokeWeight(particleTrails.getFirst().getLeadingElement().style().getStrokeWeight());
        particleTrails.forEach(this::drawParticleTrail);
        popStyle();
    }

    private void initializeTrails(FlowFieldTrailConfig flowFieldTrailConfig) {
        particleTrails.clear();
        for (int i = 0; i < flowFieldTrailConfig.getTrailsAmount(); i++) {
            float x = random(width);
            float y = random(height);
            ParticleTrail pt = ParticleTrailFactory.createParticleTrail(x, y, flowFieldTrailConfig);
            particleTrails.add(pt);
        }
    }

    private void updateTrailStyles(FlowFieldTrailConfig config) {
        for (ParticleTrail trail : particleTrails) {
            List<ParticleTrailElement> elements = trail.getParticleTrailElements();
            int size = elements.size();
            for (int i = 0; i < size; i++) {
                float t = size == 1 ? 0f : i / (float) (size - 1);
                float alpha = config.getAlphaMax() * (1f - t);
                ParticleTrailElement element = elements.get(i);
                int color1 = config.getColor1();
                int color2 = config.getColor2();
                element.style().setColors(
                        Colors.argb((int) alpha, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                        Colors.argb((int) alpha, Colors.red(color2), Colors.green(color2), Colors.blue(color2))
                );
                element.style().updateCurrentColor(element.particle().getX() / width);
            }
        }
    }

    private void updateNoiseScale(FlowFieldTrailConfig config) {
        float newNoiseScale = config.getNoiseScale();
        for (ParticleTrail trail : particleTrails) {
            for (ParticleTrailElement element : trail.getParticleTrailElements()) element.particle().setNoiseScale(newNoiseScale);
        }
    }

    private void updateTimeStep(FlowFieldTrailConfig config) {
        float newTimeStep = config.getTimeStep();
        for (ParticleTrail trail : particleTrails) {
            for (ParticleTrailElement element : trail.getParticleTrailElements()) element.particle().setTimeStep(newTimeStep);
        }
    }

    private void updateMaxSpeed(FlowFieldTrailConfig config) {
        float newMaxSpeed = config.getMaxSpeed();
        for (ParticleTrail trail : particleTrails) {
            for (ParticleTrailElement element : trail.getParticleTrailElements()) element.particle().setMaxSpeed(newMaxSpeed);
        }
    }

    private void updateTrail(FlowField flowField, ParticleTrail particleTrail) {
        ParticleTrailElement leadingElement = particleTrail.getLeadingElement();
        float x = leadingElement.particle().getX();
        float y = leadingElement.particle().getY();
        float noiseScale = leadingElement.particle().getNoiseScale();
        float time = leadingElement.particle().getTime();
        float ax = flowField.getForceX(x, y, noiseScale, time);
        float ay = flowField.getForceY(x, y, noiseScale, time);
        particleTrail.updateWithLeaderForce(width, height, ax, ay, frameCount % 4 == 0);
    }

    private void drawParticleTrail(ParticleTrail particleTrail) {
        for (ParticleTrailElement element : particleTrail.getParticleTrailElements()) drawElement(element);
    }

    private void drawElement(ParticleTrailElement element) {
        if (element.particle().isSkipDraw()) return;

        float previousX = element.particle().getPreviousX();
        float previousY = element.particle().getPreviousY();
        float x = element.particle().getX();
        float y = element.particle().getY();
        stroke(element.style().getColor());
        line(previousX, previousY, x, y);
    }

    @Override
    public void keyPressed() {
        if (key == ESC) {
            key = 0;
            return;
        }
        if (key == 'r') initializeTrails(flowFieldTrailConfig);
    }

}
