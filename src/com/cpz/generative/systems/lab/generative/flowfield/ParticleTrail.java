package com.cpz.generative.systems.lab.generative.flowfield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author CPZ
 */
public class ParticleTrail {

    private final List<ParticleTrailElement> particleTrailElements;

    public ParticleTrail() {
        particleTrailElements = new ArrayList<>();
    }

    public void addElement(ParticleTrailElement element) {
        particleTrailElements.add(element);
    }

    public void updateWithLeaderForce(float width, float height, float ax, float ay) {
        // updating the leading particle trail element position
        ParticleTrailElement leadingElement = particleTrailElements.getFirst();
        leadingElement.particle().updatePositionByAcceleration(width, height, ax, ay);
        // updating the following particle trail elements' position
        for (int i = 1; i < particleTrailElements.size(); i++) {
            ParticleTrailElement followingElement = particleTrailElements.get(i);
            float previousX = particleTrailElements.get(i - 1).particle().getPreviousX();
            float previousY = particleTrailElements.get(i - 1).particle().getPreviousY();
            followingElement.particle().followPosition(previousX, previousY);
        }
        // updating the flag to skip drawing the particle trail element
        for (ParticleTrailElement trailElement : particleTrailElements) trailElement.particle().updateSkipDraw();
        // updating the particles trail elements' style
        for (ParticleTrailElement element : particleTrailElements) element.style().updateCurrentColor(element.particle().getX() / width);
    }

    public List<ParticleTrailElement> getParticleTrailElements() {
        return Collections.unmodifiableList(particleTrailElements);
    }

    public ParticleTrailElement getLeadingElement() {
        return particleTrailElements.getFirst();
    }
}

