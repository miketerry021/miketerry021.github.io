#version 330 core
layout (location = 0) in vec3 aPos;

uniform mat4 modelUniform;
uniform mat4 viewUniform;
uniform mat4 projectionUniform;

void main() {
    gl_Position = projectionUniform * viewUniform * modelUniform * vec4(aPos, 1.0);
} 