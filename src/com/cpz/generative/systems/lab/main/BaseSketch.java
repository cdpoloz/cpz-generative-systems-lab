package com.cpz.generative.systems.lab.main;

import processing.core.PApplet;

/**
 * @author CPZ
 */
public class BaseSketch extends PApplet {

    protected void disableEscapeKey() {
        if (key == ESC) key = 0;
    }

}
