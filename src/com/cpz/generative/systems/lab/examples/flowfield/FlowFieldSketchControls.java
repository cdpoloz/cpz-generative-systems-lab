package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldTrailConfig;
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
import processing.event.MouseEvent;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

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
    private Map<String, Control> controls;
    private final FlowFieldControlsViewModel viewModel;

    public FlowFieldSketchControls(FlowFieldConfig flowFieldConfig, FlowFieldTrailConfig flowFieldTrailConfig) {
        this.viewModel = new FlowFieldControlsViewModel(flowFieldConfig, flowFieldTrailConfig);
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
        // window configuration
        frameRate(Integer.parseInt(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("sketch.fps")));
        getSurface().setTitle(FLOW_FIELD_SKETCH_CONTROLS_PROPS.getProperty("window.title"));
        getSurface().setLocation(1920 - width - 10, 1080 - height - 75);
        // loading controls from JSON file
        ControlConfigLoader loader = new ControlConfigLoader(this);
        controls = loader.load(CONFIG_PATH);
        // input manager
        inputManager = new InputManager();
        inputManager.registerLayer(new SliderInputLayer(0,
                slider("sldForceMagnitude"),
                slider("sldAngleFactor"),
                slider("sldAlphaMax"),
                slider("sldNoiseScale"),
                slider("sldTimeStep"),
                slider("sldMaxSpeed")
        ));
        // binding & initial value
        viewModel.setListener(this::updateLabels);
        bindSlider(slider("sldForceMagnitude"), viewModel::setForceMagnitude);
        bindSlider(slider("sldAngleFactor"), viewModel::setAngleFactor);
        bindSlider(slider("sldAlphaMax"), viewModel::setAlphaMax);
        bindSlider(slider("sldNoiseScale"), viewModel::setNoiseScale);
        bindSlider(slider("sldTimeStep"), viewModel::setTimeStep);
        bindSlider(slider("sldMaxSpeed"), viewModel::setMaxSpeed);
        updateLabels();
    }

    public void draw() {
        background(128);
        controls.values().forEach(Control::draw);
    }

    private Slider slider(String code) {
        Control c = controls.get(code);
        if (!(c instanceof Slider sld)) throw new IllegalStateException("Slider not found or invalid: " + code);
        return sld;
    }

    private Label label(String code) {
        Control c = controls.get(code);
        if (!(c instanceof Label lbl)) throw new IllegalStateException("Label not found or invalid: " + code);
        return lbl;
    }

    private void bindSlider(Slider slider, Consumer<BigDecimal> setter) {
        Runnable apply = () -> setter.accept(new BigDecimal(slider.getFormattedValue()));
        slider.setChangeListener(value -> apply.run());
        apply.run();
    }

    private void updateLabels() {
        label("lblForceMagnitude").setText(viewModel.getForceMagnitudeLabelText());
        label("lblAngleFactor").setText(viewModel.getAngleFactorLabelText());
        label("lblAlphaMax").setText(viewModel.getAlphaMaxLabelText());
        label("lblNoiseScale").setText(viewModel.getNoiseScaleLabelText());
        label("lblTimeStep").setText(viewModel.getTimeStepLabelText());
        label("lblMaxSpeed").setText(viewModel.getMaxSpeedLabelText());
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
