package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.main.BaseSketch;
import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldConfig;
import com.cpz.generative.systems.lab.logging.LogMessage;
import com.cpz.processing.controls.controls.Control;
import com.cpz.processing.controls.controls.config.ControlConfigLoader;
import com.cpz.processing.controls.controls.label.Label;
import com.cpz.processing.controls.controls.slider.Slider;
import com.cpz.processing.controls.controls.slider.input.SliderInputLayer;
import com.cpz.processing.controls.core.input.InputManager;
import com.cpz.processing.controls.core.input.PointerEvent;
import com.cpz.processing.controls.util.Util;
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
public class FlowFieldSketchControls extends BaseSketch {

    public static final Properties FLOW_FIELD_SKETCH_CONTROLS_PROPS = new Properties();
    private static final String CONFIG_PATH
            = "data" + File.separator
            + "config" + File.separator
            + "flow-field" + File.separator
            + "flow-field-controls.json";
    private InputManager inputManager;
    private Slider sldForceMagnitude, sldAngleFactor;
    private Label lblForceMagnitude, lblAngleFactor;
    private final FlowFieldControlsViewModel viewModel;

    public FlowFieldSketchControls(FlowFieldConfig flowFieldConfig) {
        this.viewModel = new FlowFieldControlsViewModel(flowFieldConfig);
    }

    public void settings() {
        // properties
        String propertiesPath = "data" + File.separator
                + "config" + File.separator
                + "flow-field" + File.separator
                + "config_flow-field-sketch-controls.properties";
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            FLOW_FIELD_SKETCH_CONTROLS_PROPS.load(fis);
        } catch (IOException e) {
            LOG.severe(LogMessage.fileLoadError(propertiesPath));
            System.exit(1);
        }
        PJOGL.setIcon("data" + File.separator + "img" + File.separator + FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("window.icon"));
        int sketchWidth = Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.width"));
        int sketchHeight = Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.height"));
        size(sketchWidth, sketchHeight, P2D);
        smooth(Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.smoothing")));
    }

    public void setup() {
        // frames per second
        frameRate(Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.fps")));
        // window title
        getSurface().setTitle(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("window.title"));
        // position
        getSurface().setLocation(1920 - width - 10, 1080 - height - 75);
        // loading controls from JSON file
        ControlConfigLoader loader = new ControlConfigLoader(this);
        Map<String, Control> controls = loader.load(CONFIG_PATH);
        sldForceMagnitude = Util.getControl(controls, "sldForceMagnitude", Slider.class);
        sldAngleFactor = Util.getControl(controls, "sldAngleFactor", Slider.class);
        lblForceMagnitude = Util.getControl(controls, "lblForceMagnitude", Label.class);
        lblAngleFactor = Util.getControl(controls, "lblAngleFactor", Label.class);
        // input manager
        inputManager = new InputManager();
        inputManager.registerLayer(new SliderInputLayer(0, sldForceMagnitude, sldAngleFactor));
        // binding
        sldForceMagnitude.setChangeListener(value -> {
            viewModel.setForceMagnitude(new BigDecimal(sldForceMagnitude.getFormattedValue()));
            lblForceMagnitude.setText(viewModel.getForceMagnitudeLabelText());
        });
        sldAngleFactor.setChangeListener(value -> {
            viewModel.setAngleFactor(new BigDecimal(sldAngleFactor.getFormattedValue()));
            lblAngleFactor.setText(viewModel.getAngleFactorLabelText());
        });
        // initial value
        viewModel.setForceMagnitude(new BigDecimal(sldForceMagnitude.getFormattedValue()));
        viewModel.setAngleFactor(new BigDecimal(sldAngleFactor.getFormattedValue()));
        lblForceMagnitude.setText(viewModel.getForceMagnitudeLabelText());
        lblAngleFactor.setText(viewModel.getAngleFactorLabelText());
    }


    public void draw() {
        background(128);
        sldForceMagnitude.draw();
        sldAngleFactor.draw();
        lblForceMagnitude.draw();
        lblAngleFactor.draw();
    }

    @Override
    public void keyPressed() {
        disableEscapeKey();
    }

    @Override
    public void mouseMoved() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.MOVE, (float) mouseX, (float) mouseY, mouseButton));
    }

    @Override
    public void mouseDragged() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.DRAG, (float) mouseX, (float) mouseY, mouseButton));
    }

    @Override
    public void mousePressed() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.PRESS, (float) mouseX, (float) mouseY, mouseButton));
    }

    @Override
    public void mouseReleased() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.RELEASE, (float) mouseX, (float) mouseY, mouseButton));
    }

    @Override
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
