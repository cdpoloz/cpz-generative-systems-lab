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

    public static void settings(PApplet sketch) {
        LOG.info("Starting settings");
        String propertiesPath = "data" + File.separator
                + "config" + File.separator
                + "flow-field" + File.separator
                + "config_flow-field-sketch.properties";
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            FLOW_FIELD_SKETCH_PROPS.load(fis);
        } catch (IOException e) {
            LOG.severe(LogMessage.fileLoadError(propertiesPath));
            System.exit(1);
        }

        PJOGL.setIcon("data" + File.separator + "img" + File.separator + FLOW_FIELD_SKETCH_PROPS.getProperty("window.icon"));

        DisplayInfo screen = DisplayDetector.resolveTargetDisplay(true);
        int screenWidth = screen.width();
        int screenHeight = screen.height();
        float screenScaleFactor = Float.parseFloat(FLOW_FIELD_SKETCH_PROPS.getProperty("screen.scale.factor"));

        sketch.size((int) (screenWidth * screenScaleFactor), (int) (screenHeight * screenScaleFactor), P2D);
        sketch.smooth(Integer.parseInt(FLOW_FIELD_SKETCH_PROPS.getProperty("sketch.smoothing")));
        LOG.info("Finished settings");
    }

    public static void initialSetup(PApplet sketch) {
        LOG.info("Starting initial setup");
        sketch.frameRate(Integer.parseInt(FLOW_FIELD_SKETCH_PROPS.getProperty("sketch.fps")));
        sketch.getSurface().setTitle(FLOW_FIELD_SKETCH_PROPS.getProperty("window.title"));
        sketch.getSurface().setLocation(0, 0);
        LOG.info("Finished initial setup");
    }

}
