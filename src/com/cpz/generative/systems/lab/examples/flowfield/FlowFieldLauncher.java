package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldConfig;
import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldTrailConfig;
import processing.core.PApplet;

/**
 * @author CPZ
 */
public class FlowFieldLauncher {

    public static void runSketches() {
        FlowFieldConfig sharedFlowFieldConfig = new FlowFieldConfig();
        FlowFieldTrailConfig sharedFlowFieldTrailConfig = new FlowFieldTrailConfig();

        FlowFieldSketch mainSketch = new FlowFieldSketch(sharedFlowFieldConfig, sharedFlowFieldTrailConfig);
        FlowFieldSketchControls controlsSketch = new FlowFieldSketchControls(sharedFlowFieldConfig, sharedFlowFieldTrailConfig);

        PApplet.runSketch(new String[]{"FlowFieldSketch"}, mainSketch);
        PApplet.runSketch(new String[]{"FlowFieldSketchControls"}, controlsSketch);
    }
}


