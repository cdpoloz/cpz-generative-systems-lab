# CPZ Generative Systems Lab
![Java](https://img.shields.io/badge/Java-25+-orange)
![Processing](https://img.shields.io/badge/Processing-4.5.x-blue)
![Status](https://img.shields.io/badge/status-active-brightgreen)
![License](https://img.shields.io/badge/license-MIT-lightgrey)
[![GitHub](https://img.shields.io/badge/GitHub-cdpoloz-181717?logo=github)](https://github.com/cdpoloz)

Explorations of generative and interactive systems using Processing.

This repository acts as a laboratory for experimenting with how simple rules and interactions can produce complex behaviors and structures in real time.

The focus is not purely aesthetic. These sketches are designed to explore concepts related to:

- Dynamic systems  
- Emergent behavior  
- Interaction modeling  
- Real-time visualization of complex structures  

The project follows a modular structure where simulation logic is decoupled from rendering and interaction.

Each example explores a specific system or behavior, typically composed of one or more sketches and supporting components.

## Purpose

This project complements my work in software architecture and simulation systems by providing a space to explore:

- How systems evolve over time  
- How local rules generate global patterns  
- How complex structures can be represented visually  

## Technologies

- Java  
- Processing  

## Structure

```text
src/com/cpz/generative/systems/lab/
  examples/
    flowfield/
      FlowFieldLauncher.java
      FlowFieldSketch.java
      FlowFieldSketchControls.java
      controls/
        FlowFieldControlsViewModel.java
        ViewModelListener.java

  generative/
    flowfield/
      FlowField.java
      FlowFieldConfig.java
      FlowFieldTrailConfig.java
      Particle.java
      ParticleTrail.java
      ParticleTrailElement.java
      ParticleStyle.java
      ParticleTrailFactory.java
      ParticleTrailElementFactory.java
      NoiseSource3D.java

docs/
  flow-field.md
```
---

## Current examples

- FlowField (see `docs/flow-field.md`)

---

## Notes

- Each example is launched through a dedicated Launcher class.
- Visual logic (sketches) is separated from simulation/domain logic.
- Controls are implemented in a separate sketch using a ViewModel-based approach.
- Shared configuration objects allow real-time interaction between sketches.

---

## License


`cpz-generative-systems-lab` is released under the MIT License. See [LICENSE](LICENSE).

---

## Author

**Carlos Polo Zamora**  
GitHub: https://github.com/cdpoloz  
Alias: CPZ / cepezeta / cdpoloz
