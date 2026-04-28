package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldConfig;
import processing.core.PApplet;

/**
 * @author CPZ
 */
public class FlowFieldLauncher {

    public static void runSketches() {
        // creating the common objects between sketches
        FlowFieldConfig sharedFlowFieldConfig = new FlowFieldConfig();
        // creating the sketches
        FlowFieldSketch mainSketch = new FlowFieldSketch(sharedFlowFieldConfig);
        FlowFieldSketchControls controlsSketch = new FlowFieldSketchControls(sharedFlowFieldConfig);
        // launching the sketches
        PApplet.runSketch(new String[]{"FlowFieldSketch"}, mainSketch);
        PApplet.runSketch(new String[]{"FlowFieldSketchControls"}, controlsSketch);
    }
}


