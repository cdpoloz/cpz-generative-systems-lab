package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.config.ConfigFlowFieldSketch;
import com.cpz.generative.systems.lab.generative.flowfield.FlowField;
import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldConfig;
import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldTrailConfig;
import com.cpz.generative.systems.lab.generative.flowfield.ParticleTrail;
import com.cpz.generative.systems.lab.generative.flowfield.ParticleTrailElement;
import com.cpz.generative.systems.lab.generative.flowfield.ParticleTrailFactory;
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
    private int counter;

    public FlowFieldSketch() {
        LOG.info("Starting sketch constructor: " + this.getClass().getSimpleName());
        flowFieldConfig = new FlowFieldConfig();
        flowFieldTrailConfig = new FlowFieldTrailConfig();
        particleTrails = new ArrayList<>();
        LOG.info("Finishing sketch constructor: " + this.getClass().getSimpleName());
    }

    public void settings() {
        ConfigFlowFieldSketch.settings(this);
    }

    public void setup() {
        ConfigFlowFieldSketch.initialSetup(this);
        LOG.info("Starting final setup");
        // flow field
        flowFieldConfig.setAngleFactor(4.0f);
        flowFieldConfig.setForceMagnitude(0.01f);
        flowField = new FlowField(this::noise, flowFieldConfig);
        // particle trails
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
        setupPointer();
        LOG.info("Finishing final setup");
    }

    @Override
    public void draw() {
        background(0);
        // updating particle trails
        particleTrails.parallelStream().forEach(pt -> updateTrail(flowField, pt));
        // rendering particle trails
        pushStyle();
        strokeWeight(particleTrails.getFirst().getLeadingElement().style().getStrokeWeight());
        particleTrails.forEach(this::drawParticleTrail);
        popStyle();
        drawPointer();
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

    private void setupPointer() {
        noCursor();
        noStroke();
        fill(255);
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
        particleTrail.updateWithLeaderForce(width, height, ax, ay, frameCount % 4 == 0);
    }

    private void drawParticleTrail(ParticleTrail particleTrail) {
        for (ParticleTrailElement element : particleTrail.getParticleTrailElements()) drawElement(element);
    }

    private void drawElement(ParticleTrailElement element) {
        // checking if the particle must be drawn or not
        if (element.particle().isSkipDraw()) return;
        // drawing element on screen
        float previousX = element.particle().getPreviousX();
        float previousY = element.particle().getPreviousY();
        float x = element.particle().getX();
        float y = element.particle().getY();
        stroke(element.style().getColor());
        line(previousX, previousY, x, y);
    }

    private void drawPointer() {
        if (mousePressed) cursor();
        else {
            noCursor();
            ellipse(mouseX, mouseY, 2.5f, 2.5f);
        }
    }

    @Override
    public void keyReleased() {
        if (key == 'r') initializeTrails(flowFieldTrailConfig);
    }

}
