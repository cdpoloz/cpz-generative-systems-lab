package com.cpz.generative.systems.lab.examples.flowfield.flowfield;

@FunctionalInterface
public interface NoiseSource3D {
    float noise(float x, float y, float z);
}
