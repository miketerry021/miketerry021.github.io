#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTex;
layout (location = 3) in vec3 aNormal;

out vec4 color;
out vec2 texture;
out vec3 normal;
out vec3 pos;

uniform mat4 modelUniform;
uniform mat4 viewUniform;
uniform mat4 projectionUniform;

void main() {
   gl_Position = projectionUniform * viewUniform * modelUniform * vec4(aPos, 1.0);
   pos = vec3(modelUniform * vec4(aPos, 1.0));
   color = aColor;
   texture = aTex;
   normal = mat3(transpose(inverse(modelUniform))) * aNormal;
}