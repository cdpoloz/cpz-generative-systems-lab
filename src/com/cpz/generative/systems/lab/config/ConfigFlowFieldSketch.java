package com.cpz.generative.systems.lab.config;

import com.cpz.generative.systems.lab.logging.LogMessage;
import com.cpz.generative.systems.lab.window.DisplayDetector;
import com.cpz.generative.systems.lab.window.DisplayInfo;
import processing.core.PApplet;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.cpz.generative.systems.lab.examples.flowfield.FlowFieldSketch.FLOW_FIELD_SKETCH_PROPS;
import static com.cpz.generative.systems.lab.main.Launcher.LOG;
import static processing.core.PConstants.P2D;

/**
 * @author CPZ
 */
public class ConfigFlowFieldSketch {

    public static void settings(PApplet sk) {
        LOG.info("Starting settings");
        // properties
        String propertiesPath = "data" + File.separator + "config_flow-field-sketch.properties";
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            FLOW_FIELD_SKETCH_PROPS.load(fis);
        } catch (IOException e) {
            LOG.severe(LogMessage.fileLoadError(propertiesPath));
            System.exit(1);
        }
        // window icon
        PJOGL.setIcon("data" + File.separator + "img" + File.separator + FLOW_FIELD_SKETCH_PROPS.getProperty("window.icon"));
        // screen size
        DisplayInfo screen = DisplayDetector.resolveTargetDisplay(true);
        int screenWidth = screen.width();
        int screenHeight = screen.height();
        // screen scale
        float screenScaleFactor = Float.parseFloat(FLOW_FIELD_SKETCH_PROPS.getProperty("screen.scale.factor"));
        // window size
        sk.size((int) (screenWidth * screenScaleFactor), (int) (screenHeight * screenScaleFactor), P2D);
        // antialiasing
        sk.smooth(Integer.parseInt(FLOW_FIELD_SKETCH_PROPS.getProperty("sketch.smoothing")));
        LOG.info("Finished settings");
    }

    public static void initialSetup(PApplet sketch) {
        LOG.info("Starting initial setup");
        // frames per second
        sketch.frameRate(Integer.parseInt(FLOW_FIELD_SKETCH_PROPS.getProperty("sketch.fps")));
        // window title
        sketch.getSurface().setTitle(FLOW_FIELD_SKETCH_PROPS.getProperty("window.title"));
        LOG.info("Finished initial setup");
    }

}
