package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldConfig;

import java.math.BigDecimal;

/**
 * @author CPZ
 */
public class FlowFieldControlsViewModel {

    private final FlowFieldConfig flowFieldConfig;
    private BigDecimal forceMagnitude;
    private BigDecimal angleFactor;

    public FlowFieldControlsViewModel(FlowFieldConfig flowFieldConfig) {
        if (flowFieldConfig == null) throw new IllegalArgumentException("flowFieldConfig must not be null");
        this.flowFieldConfig = flowFieldConfig;
        this.forceMagnitude = BigDecimal.valueOf(flowFieldConfig.getForceMagnitude());
        this.angleFactor = BigDecimal.valueOf(flowFieldConfig.getAngleFactor());
    }

    public void setForceMagnitude(BigDecimal value) {
        if (value == null) return;
        this.forceMagnitude = value;
        flowFieldConfig.setForceMagnitude(value.floatValue());
    }

    public void setAngleFactor(BigDecimal value) {
        if (value == null) return;
        this.angleFactor = value;
        flowFieldConfig.setAngleFactor(value.floatValue());
    }

    public BigDecimal getForceMagnitude() {
        return forceMagnitude;
    }

    public BigDecimal getAngleFactor() {
        return angleFactor;
    }

    public String getForceMagnitudeLabelText() {
        return forceMagnitude.toPlainString() + "\n\nForce\nmagnitude";
    }

    public String getAngleFactorLabelText() {
        return angleFactor.toPlainString() + "\n\nAngle\nfactor";
    }

}
