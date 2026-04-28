package com.cpz.generative.systems.lab.generative.flowfield;


import com.cpz.utils.color.Colors;

/**
 * @author CPZ
 */
public class ParticleTrailFactory {

    private ParticleTrailFactory() {
    }

    public static ParticleTrail createParticleTrail(
            float x,
            float y,
            FlowFieldTrailConfig config
    ) {
        float noiseScale = config.getNoiseScale();
        float time = config.getTime();
        float timeStep = config.getTimeStep();
        float maxSpeed = config.getMaxSpeed();
        float alphaMax = config.getAlphaMax();
        int red1 = Colors.red(config.getColor1());
        int green1 = Colors.green(config.getColor1());
        int blue1 = Colors.blue(config.getColor1());
        int red2 = Colors.red(config.getColor2());
        int green2 = Colors.green(config.getColor2());
        int blue2 = Colors.blue(config.getColor2());
        float strokeWeightMax = config.getStrokeWeight();
        ParticleTrail pt = new ParticleTrail();
        ParticleTrailElement leadingElement = ParticleTrailElementFactory.createParticleTrailElement(
                x,
                y,
                noiseScale,
                time,
                timeStep,
                maxSpeed,
                Colors.argb((int) alphaMax, red1, green1, blue1),
                Colors.argb((int) alphaMax, red2, green2, blue2),
                strokeWeightMax
        );
        pt.addElement(leadingElement);
        int particlesAmount = config.getParticlesAmount();
        for (int j = 0; j < particlesAmount - 1; j++) {
            float t = (j + 1f) / (particlesAmount - 1);
            float strokeWeight = strokeWeightMax * (1 - t);
            float alpha = alphaMax * (1 - t);
            ParticleTrailElement followingElement = ParticleTrailElementFactory.createParticleTrailElement(
                    leadingElement.particle().getPreviousX(),
                    leadingElement.particle().getPreviousY(),
                    noiseScale,
                    time,
                    timeStep,
                    maxSpeed,
                    Colors.argb((int) alpha, red1, green1, blue1),
                    Colors.argb((int) alpha, red2, green2, blue2),
                    strokeWeight
            );
            pt.addElement(followingElement);
        }
        return pt;
    }

}
