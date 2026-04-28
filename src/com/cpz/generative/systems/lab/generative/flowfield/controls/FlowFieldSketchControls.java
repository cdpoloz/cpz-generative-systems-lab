package com.cpz.generative.systems.lab.generative.flowfield.controls;

import com.cpz.generative.systems.lab.logging.LogMessage;
import com.cpz.processing.controls.controls.Control;
import com.cpz.processing.controls.controls.config.ControlConfigLoader;
import com.cpz.processing.controls.controls.label.Label;
import com.cpz.processing.controls.controls.slider.Slider;
import com.cpz.processing.controls.controls.slider.input.SliderInputLayer;
import com.cpz.processing.controls.core.input.InputManager;
import com.cpz.processing.controls.core.input.PointerEvent;
import com.cpz.processing.controls.util.Util;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static com.cpz.generative.systems.lab.main.Launcher.LOG;

/**
 * @author CPZ
 */
public class FlowFieldSketchControls extends PApplet {

    public static final Properties FLOW_FIELD_SKETCH_CONTROLS_PROPS = new Properties();
    private static final String CONFIG_PATH = "data" + File.separator + "config" + File.separator + "flow-field-controls.json";
    private InputManager inputManager;
    private Slider sldForceMagnitude;
    private Label lblForceMagnitude;
    private BigDecimal currentValue;

    public void settings() {
        // properties
        String propertiesPath = "data" + File.separator + "config_flow-field-sketch-controls.properties";
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            FLOW_FIELD_SKETCH_CONTROLS_PROPS.load(fis);
        } catch (IOException e) {
            LOG.severe(LogMessage.fileLoadError(propertiesPath));
            System.exit(1);
        }
        PJOGL.setIcon("data" + File.separator + "img" + File.separator + FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("window.icon"));
        size(
                Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.width")),
                Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.height")),
                P2D
        );
        smooth(Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.smoothing")));
    }

    public void setup() {
        frameRate(Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.fps")));
        // loading controls from JSON file
        ControlConfigLoader loader = new ControlConfigLoader(this);
        Map<String, Control> controls = loader.load(CONFIG_PATH);
        sldForceMagnitude = Util.getControl(controls, "sldForceMagnitude", Slider.class);
        lblForceMagnitude = Util.getControl(controls, "lblForceMagnitude", Label.class);
        // input manager
        inputManager = new InputManager();
        inputManager.registerLayer(new SliderInputLayer(0, sldForceMagnitude));
        // binding
        sldForceMagnitude.setChangeListener(value -> currentValue = new BigDecimal(sldForceMagnitude.getFormattedValue()));
        // initial value
        currentValue = new BigDecimal(sldForceMagnitude.getFormattedValue());
    }

    public void draw() {
        background(28);
        lblForceMagnitude.setText(currentValue.toPlainString() + "\n\nforceMagnitude");
        sldForceMagnitude.draw();
        lblForceMagnitude.draw();
    }

    public void mouseMoved() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.MOVE, (float) mouseX, (float) mouseY, mouseButton));
    }

    public void mouseDragged() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.DRAG, (float) mouseX, (float) mouseY, mouseButton));
    }

    public void mousePressed() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.PRESS, (float) mouseX, (float) mouseY, mouseButton));
    }

    public void mouseReleased() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.RELEASE, (float) mouseX, (float) mouseY, mouseButton));
    }

    public void mouseWheel(MouseEvent event) {
        inputManager.dispatchPointer(new PointerEvent(
                PointerEvent.Type.WHEEL,
                (float) mouseX,
                (float) mouseY,
                mouseButton,
                (float) event.getCount(),
                event.isShiftDown(),
                event.isControlDown()
        ));
    }
}
