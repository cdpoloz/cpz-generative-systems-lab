# Flow Field Example

## Purpose

This example explores a generative flow-field system using particles, trails, and a vector field derived from noise. Each visible trail is built from a leading particle that is accelerated by the field and a sequence of following particles that reuse previous positions to create a continuous path.

## How to run

Run `FlowFieldLauncher` to start the example. The launcher creates shared configuration objects and starts two Processing sketches:

- `FlowFieldSketch`: the main visual sketch that renders the particle trails.
- `FlowFieldSketchControls`: the controls sketch that exposes runtime sliders in a separate Processing window.

The application entry point in `Launcher` currently calls `FlowFieldLauncher.runSketches()`.

## Architecture overview

The Flow Field example is split into example code and reusable simulation code.

The main visual sketch lives in `examples.flowfield` and owns the Processing render loop. It creates the flow field, initializes particle trails, updates trail state, and draws line segments.

The control sketch also lives in the example area. Its ViewModel and listener are in `examples.flowfield.controls` because they are specific to this control surface. The sketch loads UI controls from JSON, binds sliders to the ViewModel, and dispatches pointer input through the controls library.

Shared configuration objects connect both sketches:

- `FlowFieldConfig` stores field-level parameters such as force magnitude and angle factor.
- `FlowFieldTrailConfig` stores trail-related parameters such as alpha, noise scale, time step, and max speed.

The simulation and domain classes live in `generative.flowfield`. These classes do not depend on the controls sketch and can be reused by other examples.

## Main classes

- `FlowFieldLauncher`: creates shared config instances, constructs both sketches, and launches them with `PApplet.runSketch`.
- `FlowFieldSketch`: renders the visual system, initializes trails, applies runtime config updates, updates trails with `parallelStream()`, and draws the resulting line segments.
- `FlowFieldSketchControls`: loads slider controls from JSON, registers input layers, binds sliders to the ViewModel, and updates labels.
- `FlowFieldControlsViewModel`: receives slider values, writes them into shared config objects, and requests trail updates when existing particles or styles need to be refreshed.
- `FlowField`: converts 3D noise samples into force vectors using angle factor and force magnitude.
- `FlowFieldConfig`: shared configuration for field-level behavior.
- `FlowFieldTrailConfig`: shared configuration for trail construction and trail runtime updates.
- `Particle`: stores position, previous position, velocity, noise sampling parameters, speed limit, and discontinuity state.
- `ParticleTrail`: stores ordered trail elements and updates the leading particle and following particles.
- `ParticleTrailElement`: record that groups a `Particle` with its `ParticleStyle`.
- `ParticleStyle`: stores color interpolation endpoints, current color, and stroke weight.
- `ParticleTrailFactory`: creates a complete trail from `FlowFieldTrailConfig`.
- `ParticleTrailElementFactory`: creates configured particle/style pairs for a trail.
- `NoiseSource3D`: functional interface used by `FlowField` to sample noise.

## Runtime controls

Current sliders:

- `forceMagnitude`: updates `FlowFieldConfig` immediately. The next field sample uses the new force magnitude.
- `angleFactor`: updates `FlowFieldConfig` immediately. The next field sample uses the new angle multiplier.
- `alphaMax`: updates `FlowFieldTrailConfig` and requests a style update for existing trail elements.
- `noiseScale`: updates `FlowFieldTrailConfig` and requests propagation to existing particles.
- `timeStep`: updates `FlowFieldTrailConfig` and requests propagation to existing particles.
- `maxSpeed`: updates `FlowFieldTrailConfig` and requests propagation to existing particles.

`forceMagnitude` and `angleFactor` are read directly by `FlowField`, so they affect new force calculations without rebuilding trails. Trail style and particle-level settings are copied into particles/styles during trail creation, so the sketch consumes update flags from `FlowFieldTrailConfig` and applies those changes to existing trail elements.

## Input and controls

Controls are loaded from `data/config/flow-field/flow-field-controls.json` through `ControlConfigLoader`.

`SliderInputLayer` supports multiple sliders. The controls sketch registers one layer containing all current Flow Field sliders.

`FlowFieldSketchControls` dispatches Processing pointer events through `InputManager`. Mouse movement, dragging, press/release, and wheel events are converted into `PointerEvent` instances and sent to the registered input layers.

## Flow field behavior

For each trail, the leading particle samples the flow field at its current position. The field samples 3D noise using `x`, `y`, `noiseScale`, and particle time. That noise value becomes an angle, and the angle is converted into an acceleration vector.

The leading particle integrates that acceleration into velocity, applies the max speed limit, and wraps around screen bounds. Each following particle moves to the previous position of the particle ahead of it, which creates the trail.

Wrapping can produce a large jump from one side of the screen to the other. `Particle.updateSkipDraw()` compares the current segment length with a discontinuity threshold derived from max speed. If the jump is too large, `skipDraw` prevents drawing that segment and avoids long wrap-around lines.

## Performance notes

Trail updates use `parallelStream()` so particle state updates can run across multiple worker threads.

Rendering remains on the Processing main thread. The sketch updates all trails first and then draws them sequentially.

Color updates are throttled and currently happen every 4 frames. This reduces the cost of repeatedly recalculating interpolated colors.

Rendering cost is mostly related to drawing many line segments and changing stroke color for each trail element.

## Current limitations / future ideas

- Add additional controls for trail count, trail length, stroke weight, and colors.
- Add presets for known visual configurations.
- Save and load full Flow Field configurations.
- Group controls by category as the control surface grows.
- Support alternative noise sources.
- Run multiple flow-field systems at the same time.
- Add reset and randomize controls to the controls sketch.
