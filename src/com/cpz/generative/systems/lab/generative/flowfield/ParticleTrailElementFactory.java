package com.cpz.generative.systems.lab.generative.flowfield;

/**
 * @author CPZ
 */
public final class ParticleTrailElementFactory {

    private ParticleTrailElementFactory() {
    }

    public static ParticleTrailElement createParticleTrailElement(
            float x,
            float y,
            float noiseScale,
            float time,
            float timeStep,
            float maxSpeed,
            int color1,
            int color2,
            float strokeWeight
    ) {
        Particle particle = new Particle(x, y);
        particle.setNoiseScale(noiseScale);
        particle.setTime(time);
        particle.setTimeStep(timeStep);
        particle.setMaxSpeed(maxSpeed);
        ParticleStyle style = new ParticleStyle();
        style.setColors(color1, color2);
        style.setStrokeWeight(strokeWeight);
        return new ParticleTrailElement(particle, style);
    }

}
