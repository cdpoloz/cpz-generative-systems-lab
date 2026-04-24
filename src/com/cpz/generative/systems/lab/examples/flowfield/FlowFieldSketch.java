package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.config.ConfigFlowFieldSketch;
import com.cpz.generative.systems.lab.generative.flowfield.FlowField;
import com.cpz.generative.systems.lab.generative.flowfield.ParticleTrail;
import com.cpz.generative.systems.lab.generative.flowfield.ParticleTrailElement;
import com.cpz.generative.systems.lab.generative.flowfield.ParticleTrailElementFactory;
import com.cpz.utils.color.Colors;
import processing.core.PApplet;

import java.util.List;
import java.util.Properties;

import static com.cpz.generative.systems.lab.main.Launcher.LOG;

/**
 * @author CPZ
 */
public class FlowFieldSketch extends PApplet {

    public static final Properties FLOW_FIELD_SKETCH_PROPS = new Properties();
    private ParticleTrail pt1, pt2, pt3, pt4, pt5;
    private FlowField flowField;
    private float alphaMax, strokeWeightMax;

    public FlowFieldSketch() {
        LOG.info("Starting sketch constructor: " + this.getClass().getSimpleName());
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
        pt1 = new ParticleTrail();
        pt2 = new ParticleTrail();
        pt3 = new ParticleTrail();
        pt4 = new ParticleTrail();
        pt5 = new ParticleTrail();
        // particle trail elements' values
        int particlesAmount = 2000;
        alphaMax = 64;
        strokeWeightMax = 2.5f;
        float noiseScale = 0.001f;
        float time = 0.0f;
        float timeStep = 0.02f;
        float maxSpeed = 2.5f;
        int color1 = Colors.argb(255, 255, 204, 0);
        int color2 = Colors.argb(255, 255, 0, 102);
        // particles
        float x = random(width);
        float y = random(height);
        ParticleTrailElement leadingElement = ParticleTrailElementFactory.createParticleTrailElement(
                x,
                y,
                noiseScale,
                time,
                timeStep,
                maxSpeed,
                Colors.argb((int) alphaMax, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                Colors.argb((int) alphaMax, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                strokeWeightMax
        );
        pt1.addElement(leadingElement);

        leadingElement = ParticleTrailElementFactory.createParticleTrailElement(
                x + random(-4,4),
                y + random(-4,4),
                noiseScale,
                time,
                timeStep,
                maxSpeed,
                Colors.argb((int) alphaMax, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                Colors.argb((int) alphaMax, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                strokeWeightMax
        );
        pt2.addElement(leadingElement);

        leadingElement = ParticleTrailElementFactory.createParticleTrailElement(
                x + random(-4,4),
                y + random(-4,4),
                noiseScale,
                time,
                timeStep,
                maxSpeed,
                Colors.argb((int) alphaMax, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                Colors.argb((int) alphaMax, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                strokeWeightMax
        );
        pt3.addElement(leadingElement);

        leadingElement = ParticleTrailElementFactory.createParticleTrailElement(
                x + random(-4,4),
                y + random(-4,4),
                noiseScale,
                time,
                timeStep,
                maxSpeed,
                Colors.argb((int) alphaMax, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                Colors.argb((int) alphaMax, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                strokeWeightMax
        );
        pt4.addElement(leadingElement);

        leadingElement = ParticleTrailElementFactory.createParticleTrailElement(
                x + random(-4,4),
                y + random(-4,4),
                noiseScale,
                time,
                timeStep,
                maxSpeed,
                Colors.argb((int) alphaMax, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                Colors.argb((int) alphaMax, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                strokeWeightMax
        );
        pt5.addElement(leadingElement);

        for (int i = 0; i < particlesAmount - 1; i++) {
            float strokeWeight = map(i, 0, particlesAmount - 1, strokeWeightMax, 0);
            float alpha = map(i, 0, particlesAmount - 1, alphaMax, 0);
            ParticleTrailElement followingElement = ParticleTrailElementFactory.createParticleTrailElement(
                    leadingElement.particle().getPreviousX(),
                    leadingElement.particle().getPreviousY(),
                    noiseScale,
                    time,
                    timeStep,
                    maxSpeed,
                    Colors.argb((int) alpha, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                    Colors.argb((int) alpha, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                    strokeWeight
            );
            pt1.addElement(followingElement);

            followingElement = ParticleTrailElementFactory.createParticleTrailElement(
                    leadingElement.particle().getPreviousX(),
                    leadingElement.particle().getPreviousY(),
                    noiseScale,
                    time,
                    timeStep,
                    maxSpeed,
                    Colors.argb((int) alpha, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                    Colors.argb((int) alpha, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                    strokeWeight
            );
            pt2.addElement(followingElement);

            followingElement = ParticleTrailElementFactory.createParticleTrailElement(
                    leadingElement.particle().getPreviousX(),
                    leadingElement.particle().getPreviousY(),
                    noiseScale,
                    time,
                    timeStep,
                    maxSpeed,
                    Colors.argb((int) alpha, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                    Colors.argb((int) alpha, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                    strokeWeight
            );
            pt3.addElement(followingElement);

            followingElement = ParticleTrailElementFactory.createParticleTrailElement(
                    leadingElement.particle().getPreviousX(),
                    leadingElement.particle().getPreviousY(),
                    noiseScale,
                    time,
                    timeStep,
                    maxSpeed,
                    Colors.argb((int) alpha, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                    Colors.argb((int) alpha, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                    strokeWeight
            );
            pt4.addElement(followingElement);

            followingElement = ParticleTrailElementFactory.createParticleTrailElement(
                    leadingElement.particle().getPreviousX(),
                    leadingElement.particle().getPreviousY(),
                    noiseScale,
                    time,
                    timeStep,
                    maxSpeed,
                    Colors.argb((int) alpha, Colors.red(color1), Colors.green(color1), Colors.blue(color1)),
                    Colors.argb((int) alpha, Colors.red(color2), Colors.green(color2), Colors.blue(color2)),
                    strokeWeight
            );
            pt5.addElement(followingElement);
        }
        LOG.info("Finishing final setup");
    }

    @Override
    public void draw() {
        background(0);
        // particles
        updateTrail(flowField, pt1);
        updateTrail(flowField, pt2);
        updateTrail(flowField, pt3);
        updateTrail(flowField, pt4);
        updateTrail(flowField, pt5);
        drawParticleTrail(pt1);
        drawParticleTrail(pt2);
        drawParticleTrail(pt3);
        drawParticleTrail(pt4);
        drawParticleTrail(pt5);
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
        List<ParticleTrailElement> particleTrailElements = particleTrail.getParticleTrailElements();
        for (ParticleTrailElement element : particleTrailElements) drawElement(element);
    }

    private void drawElement(ParticleTrailElement element) {
        // checking if the particle must be drawn or not
        if (element.particle().isSkipDraw()) return;
        // drawing element on screen
        float previousX = element.particle().getPreviousX();
        float previousY = element.particle().getPreviousY();
        float x = element.particle().getX();
        float y = element.particle().getY();
        pushStyle();
        strokeWeight(element.style().getStrokeWeight());
        stroke(element.style().getColor());
        line(previousX, previousY, x, y);
        popStyle();
    }

    @Override
    public void keyReleased() {
        if (key == 'r') setup();
    }

}
