package com.cpz.generative.systems.lab.main;

import com.cpz.generative.systems.lab.examples.flowfield.FlowFieldSketch;
import com.cpz.generative.systems.lab.logging.Log;
import com.cpz.generative.systems.lab.logging.LogMessage;
import processing.core.PApplet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * Bootstrap entry point ({@code main} package) responsible for starting the application.
 * <p>
 * Loads configuration, prepares the main {@link Sketch}, and launches the Processing runtime.
 * This class contains no MVVM logic.
 * </p>
 *
 * @author CPZ
 */
public class Launcher {

    public static final Log LOG = new Log(Launcher.class.getName());
    public static final Properties PROPS = new Properties();

    /**
     * Application entry point.
     * <p>
     * Loads properties, configures logging shutdown, creates the {@link Sketch}, initializes it,
     * and runs Processing on a dedicated thread.
     * </p>
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("en-US"));
        // Shutdown hook to close handlers when the program exits.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (var handler : LOG.getHandlers()) handler.close();
        }));
        // you can run one of the available examples
        PApplet.main(FlowFieldSketch.class);
    }

}
