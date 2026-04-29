package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldConfig;
import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldTrailConfig;
import processing.core.PApplet;

/**
 * @author CPZ
 */
public class FlowFieldLauncher {

    public static void runSketches() {
        // creating the common objects between sketches
        FlowFieldConfig sharedFlowFieldConfig = new FlowFieldConfig();
        FlowFieldTrailConfig sharedFlowFieldTrailConfig = new FlowFieldTrailConfig();
        // creating the sketches
        FlowFieldSketch mainSketch = new FlowFieldSketch(sharedFlowFieldConfig, sharedFlowFieldTrailConfig);
        FlowFieldSketchControls controlsSketch = new FlowFieldSketchControls(sharedFlowFieldConfig, sharedFlowFieldTrailConfig);
        // launching the sketches
        PApplet.runSketch(new String[]{"FlowFieldSketch"}, mainSketch);
        PApplet.runSketch(new String[]{"FlowFieldSketchControls"}, controlsSketch);
    }
}


