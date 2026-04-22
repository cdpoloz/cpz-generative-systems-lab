package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.config.Config;
import com.cpz.utils.color.Colors;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author CPZ
 */
public class FlowFieldSketch extends PApplet {

    private final PVector prevPos, pos, vel;
    //private float prevX, prevY, x, y, vx, vy;
    private float noiseScale, time, timeStep, maxSpeed;
    private int c1, c2;

    public FlowFieldSketch() {
        prevPos = new PVector();
        pos = new PVector();
        vel = new PVector();
    }

    public void settings() {
        Config.settings(this);
    }

    public void setup() {
        Config.setup(this, "| FlowFieldSketch");
        // noise parameters
        noiseScale = 0.001f;
        time = 0.0f;
        timeStep = 0.01f;
        // initial position values
        float x = random(width);
        float y = random(height);
        pos.set(x, y);
        prevPos.set(x, y);
        //prevX = x;
        //prevY = y;
        // speed parameters
        maxSpeed = 2.0f;
        // color parameters
        c1 = Colors.argb(255, 255,255,0);
        c2 = Colors.argb(255, 255,0,255);
        // initial background
        background(0);
    }

    @Override
    public void draw() {
        // update
        // saving previous position
        prevPos.set(pos.x, pos.y);
        //prevX = x;
        //prevY = y;
        // updating acceleration value
        float angle = noise(pos.x * noiseScale, pos.y * noiseScale, time) * TWO_PI * 4.0f;
        float ax = cos(angle) * 0.1f;
        float ay = sin(angle) * 0.1f;
        // updating velocity value
        float vx = vel.x;
        float vy = vel.y;
        vx += ax;
        vy += ay;
        float speed = sqrt(vx * vx + vy * vy);
        if (speed > maxSpeed) {
            vx = (vx / speed) * maxSpeed;
            vy = (vy / speed) * maxSpeed;
        }
        vel.set(vx, vy);
        // updating position
        float x = pos.x;
        float y = pos.y;
        x += vx;
        y += vy;
        // checking if position is inside the frame
        boolean particleInsideFrame = (x > 0 && x < width) && (y > 0 && y < height);
        if (x < 0) x = width - 1;
        else if (x >= width) x = 0;
        if (y < 0) y = height - 1;
        else if (y >= height) y = 0;
        pos.set(x, y);
        // updating time/step
        time += timeStep;
        // updating color depending on x position
        int c = Colors.lerpColor(c1, c2, x/width);
        // draw
        stroke(red(c), green(c), blue(c), 80);
        if (particleInsideFrame) line(prevPos.x, prevPos.y, pos.x, pos.y);
    }

}
