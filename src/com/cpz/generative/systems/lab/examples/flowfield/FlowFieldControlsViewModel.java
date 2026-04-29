package com.cpz.generative.systems.lab.examples.flowfield;

import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldConfig;
import com.cpz.generative.systems.lab.generative.flowfield.FlowFieldTrailConfig;
import com.cpz.generative.systems.lab.generative.ViewModelListener;

import java.math.BigDecimal;

/**
 * @author CPZ
 */
public class FlowFieldControlsViewModel {

    private final FlowFieldConfig flowFieldConfig;
    private final FlowFieldTrailConfig flowFieldTrailConfig;
    private BigDecimal forceMagnitude, angleFactor, alphaMax, noiseScale, timeStep, maxSpeed;
    private ViewModelListener listener;

    public FlowFieldControlsViewModel(FlowFieldConfig flowFieldConfig, FlowFieldTrailConfig flowFieldTrailConfig) {
        if (flowFieldConfig == null) throw new IllegalArgumentException("flowFieldConfig must not be null");
        if (flowFieldTrailConfig == null) throw new IllegalArgumentException("flowFieldTrailConfig must not be null");
        this.flowFieldConfig = flowFieldConfig;
        this.flowFieldTrailConfig = flowFieldTrailConfig;
        this.forceMagnitude = BigDecimal.valueOf(flowFieldConfig.getForceMagnitude());
        this.angleFactor = BigDecimal.valueOf(flowFieldConfig.getAngleFactor());
        this.alphaMax = BigDecimal.valueOf(flowFieldTrailConfig.getAlphaMax());
        this.noiseScale = BigDecimal.valueOf(flowFieldTrailConfig.getNoiseScale());
        this.timeStep = BigDecimal.valueOf(flowFieldTrailConfig.getTimeStep());
        this.maxSpeed = BigDecimal.valueOf(flowFieldTrailConfig.getMaxSpeed());
    }

    public void setForceMagnitude(BigDecimal value) {
        if (value == null) return;
        this.forceMagnitude = value;
        flowFieldConfig.setForceMagnitude(value.floatValue());
        notifyChanged();
    }

    public void setAngleFactor(BigDecimal value) {
        if (value == null) return;
        this.angleFactor = value;
        flowFieldConfig.setAngleFactor(value.floatValue());
        notifyChanged();
    }

    public void setAlphaMax(BigDecimal value) {
        if (value == null) return;
        alphaMax = value;
        flowFieldTrailConfig.setAlphaMax(value.floatValue());
        flowFieldTrailConfig.requestStyleUpdate();
        notifyChanged();
    }

    public void setNoiseScale(BigDecimal value) {
        if (value == null) return;
        noiseScale = value;
        flowFieldTrailConfig.setNoiseScale(value.floatValue());
        flowFieldTrailConfig.requestNoiseScaleUpdate();
        notifyChanged();
    }

    public void setTimeStep(BigDecimal value) {
        if (value == null) return;
        timeStep = value;
        flowFieldTrailConfig.setTimeStep(value.floatValue());
        flowFieldTrailConfig.requestTimeStepUpdate();
        notifyChanged();
    }

    public void setMaxSpeed(BigDecimal value) {
        if (value == null) return;
        maxSpeed = value;
        flowFieldTrailConfig.setMaxSpeed(value.floatValue());
        flowFieldTrailConfig.requestMaxSpeedUpdate();
        notifyChanged();
    }

    public BigDecimal getAlphaMax() {
        return alphaMax;
    }

    public BigDecimal getForceMagnitude() {
        return forceMagnitude;
    }

    public BigDecimal getAngleFactor() {
        return angleFactor;
    }

    public String getForceMagnitudeLabelText() {
        return String.format("%.2f", forceMagnitude) + "\n\nForce\nmagnitude";
    }

    public String getAngleFactorLabelText() {
        return String.format("%.2f", angleFactor) + "\n\nAngle\nfactor";
    }

    public String getAlphaMaxLabelText() {
        return String.format("%.0f", alphaMax) + "\n\nMax.\nalpha";
    }

    public String getNoiseScaleLabelText() {
        return String.format("%.3f", noiseScale) + "\n\nNoise\nscale";
    }

    public String getTimeStepLabelText() {
        return String.format("%.2f", timeStep) + "\n\nTime\nstep";
    }

    public String getMaxSpeedLabelText() {
        return String.format("%.1f", maxSpeed) + "\n\nMax.\nspeed";
    }

    public void setListener(ViewModelListener listener) {
        this.listener = listener;
    }

    private void notifyChanged() {
        if (listener != null) listener.onViewModelChanged();
    }

}
