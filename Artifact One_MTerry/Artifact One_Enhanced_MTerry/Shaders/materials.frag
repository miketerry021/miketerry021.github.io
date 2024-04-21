#version 330 core
in vec4 color;
in vec2 texture;
in vec3 normal;
in vec3 pos;

out vec4 fragColor;


struct Material {           // used for setting an object material for light reflecion
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
};

struct Intensity {          // used for adjusting the intensity of the light sources
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    vec3 position;
};

uniform Material material;
uniform Intensity intensity;
uniform sampler2D texUniform;
uniform vec3 lightColor;
uniform vec3 lightPos;
uniform vec3 viewPos;

void main()
{
    /****** function for setting material and intensity ******/
    // ambient light
    vec3 ambient =  material.ambient * intensity.ambient;              

    //diffuse light
    vec3 norm = normalize(normal);
    vec3 lightDir = normalize(lightPos - pos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = (diff * material.diffuse) * intensity.diffuse;                   

    // specular light
    
    vec3 viewDir = normalize(viewPos - pos);                                        
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = (material.specular * spec) * intensity.specular;           

    vec3 result = specular + ambient + diffuse;
    
    fragColor = vec4(result, 1.0) * texture(texUniform, texture);   
}