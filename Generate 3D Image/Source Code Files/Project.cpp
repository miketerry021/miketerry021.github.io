/*
*   Programmer      :   Michael Terry
*   Course          :   CS 330 - Computational Graphics and Visualization
*   Date Created    :   November 2023
*   Description     :   This program is designed to created a 3D scene from a selected image* 
*
*   Modified date   :   March 2024 - Modifications will add texture, lighting effect, and camera controls
*/

#include <glad/glad.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/vec3.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include"ShaderClass.h"
#include "VAO.h"
#include "VBO.h"
#include "EBO.h"
#include "Camera.h"
#include "Texture.h"
#include "CreateShape.h"

#include <iostream>
#include <vector>
using namespace std;

void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void processInput(GLFWwindow* window);
void mouse_callback(GLFWwindow* window, double xpos, double ypos);
void scroll_callback(GLFWwindow* window, double xoffset, double yoffset);

/***************************************************************************
*                              Global Variables
****************************************************************************/

// window settings
const unsigned int WINDOW_WIDTH = 1200;
const unsigned int WINDOW_HEIGHT = 800;

// matrices for translations
glm::mat4 rotation = glm::mat4(1.0f);
glm::mat4 scale = glm::mat4(1.0f);
glm::mat4 translation = glm::mat4(1.0f);
glm::mat4 model = glm::mat4(1.0f);
glm::mat4 view = glm::mat4(1.0f);
glm::mat4 projection = glm::mat4(1.0f);

// camera
Camera camera(glm::vec3(0.0f, 3.0f, 5.0f));
float lastX = WINDOW_WIDTH / 2.0f;
float lastY = WINDOW_HEIGHT / 2.0f;
bool firstMouse = true;

// timing
float deltaTime = 0.0f;	// time between current frame and last frame
float lastFrame = 0.0f;

// lighting
glm::vec3 lightSourcePos(12.0f, 8.0f, 10.0f);
glm::vec3 lightSourceColor(0.2f, 0.6f, 0.2f);

int main()
{
    // glfw: initialize and configure
    // ------------------------------
    glfwInit();
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    // glfw window creation
    // --------------------
    GLFWwindow* window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Final Project", NULL, NULL);
    if (window == NULL)
    {
        cout << "Failed to create GLFW window" << endl;
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);
    glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
    glfwSetCursorPosCallback(window, mouse_callback);
    glfwSetScrollCallback(window, scroll_callback);

    // tell GLFW to capture our mouse
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

    // glad: load all OpenGL function pointers
    // ---------------------------------------
    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
    {
        cout << "Failed to initialize GLAD" << endl;
        return -1;
    }

    // global enable Z-depth.
    glEnable(GL_DEPTH_TEST);

    /****************************************************************************************
    *                                    Shader Programs
    *****************************************************************************************/
    // Generates Shader objects 
    Shader shapeShader("default.vert", "default.frag");
    Shader lightShader("light.vert", "light.frag");
    Shader materialsShader("materials.vert", "materials.frag");

    /*****************************************************************************************
    *                           Plane Shape - Coordinates and Buffer Data
    ******************************************************************************************/
    float planeVertices[] = {
        // indices - begin at 0     // color                    // texture      // normals
        -0.5f, 0.5f, 0.0f,          0.1f, 0.4f, 0.1f, 1.0f,     0.0f, 1.0f,     -0.25f, 0.25f, 0.0f,
         0.5f, 0.5f, 0.0f,          0.1f, 0.4f, 0.1f, 1.0f,     1.0f, 1.0f,      0.25f, 0.25f, 0.0f,
        -0.5f, -0.5f, 0.0f,         0.1f, 0.4f, 0.1f, 1.0f,     0.0f, 0.0f,     -0.25f, -0.25f, 0.0f,
         0.5f, -0.5f, 0.0f,         0.1f, 0.4f, 0.1f, 1.0f,     1.0f, 0.0f,      0.25f, -0.25f, 0.0f
    };

    float sideWallVertices[] = {
        // indices - begin at 0     // color                    // texture      // normals
        -0.5f, 0.5f, 0.0f,          0.4f, 0.3f, 0.0f, 1.0f,     0.0f, 1.0f,     -0.25f, 0.25f, 0.0f,
         0.4f, 0.5f, 0.0f,          0.4f, 0.3f, 0.0f, 1.0f,     1.0f, 1.0f,      0.25f, 0.25f, 0.0f,
        -0.5f, -0.1f, 0.0f,         0.4f, 0.3f, 0.0f, 1.0f,     0.0f, 0.0f,     -0.25f, -0.25f, 0.0f,
         0.4f, -0.1f, 0.0f,         0.4f, 0.3f, 0.0f, 1.0f,     1.0f, 0.0f,      0.25f, -0.25f, 0.0f
    };

    unsigned int planeIndices[] = {
        0, 1, 2,    // First Triangle
        1, 2, 3     // Second Triangle

    };

    VAO planeVAO;                       // generate turf plane vector array object and bind
    planeVAO.Bind();
    VBO planeVBO(planeVertices, sizeof(planeVertices));     // generate planeVBO & planeEBO and binds data
    EBO planeEBO(planeIndices, sizeof(planeIndices));
    planeVAO.LinkVBO(planeVBO, 0, 3, 12, 0);                // link VBO with VAO.         
    planeVAO.LinkVBO(planeVBO, 1, 4, 12, 3);                // parameters - (VBO name, layout, vetor size, stride, offset)
    planeVAO.LinkVBO(planeVBO, 2, 2, 12, 7);
    planeVAO.LinkVBO(planeVBO, 3, 3, 12, 9);
    planeVAO.Unbind();                                      // unbinds buffers and array object
    planeVBO.Unbind();
    planeEBO.Unbind();

    VAO sidePlaneVAO;                   // generate side wall vector array object and bind
    sidePlaneVAO.Bind();
    VBO sidePlaneVBO(sideWallVertices, sizeof(sideWallVertices));   // generate sidePlaneVBO & sidePlaneEBO and binds data
    EBO sidePlaneEBO(planeIndices, sizeof(planeIndices));
    sidePlaneVAO.LinkVBO(sidePlaneVBO, 0, 3, 12, 0);                // link VBO with VAO.
    sidePlaneVAO.LinkVBO(sidePlaneVBO, 1, 4, 12, 3);                // parameters - (VBO name, layout, vetor size, stride, offset)
    sidePlaneVAO.LinkVBO(sidePlaneVBO, 2, 2, 12, 7);
    sidePlaneVAO.LinkVBO(sidePlaneVBO, 3, 3, 12, 9);
    sidePlaneVAO.Unbind();                                          // unbinds buffers and array object
    sidePlaneVBO.Unbind();
    sidePlaneEBO.Unbind();

    /**************************************************************************************
    *                  Cube Coordinates - Coordinates and Buffer Data
    ***************************************************************************************/
    float cubeVertices[] = {
        // indices              // color                    // texture
        -1.0f, 0.5f, 0.5f,      0.0f, 0.0f, 0.0f, 1.0f,     0.0f, 5.0f,
         1.0f, 0.5f, 0.5f,      0.0f, 0.0f, 0.0f, 1.0f,     5.0f, 5.0f,
        -1.0f, -0.7f, 0.5f,     0.0f, 0.0f, 0.0f, 1.0f,     0.0f, 0.0f,
         1.0f, -0.7f, 0.5f,     0.0f, 0.0f, 0.0f, 1.0f,     5.0f, 0.0f,
        -1.0f, 0.5f, -0.3f,     0.0f, 0.0f, 0.0f, 1.0f,     5.0f, 5.0f,
         1.0f, 0.5f, -0.3f,     0.0f, 0.0f, 0.0f, 1.0f,     0.0f, 5.0f,
        -1.0f, -0.7f, -0.3f,    0.0f, 0.0f, 0.0f, 1.0f,     5.0f, 0.0f,
         1.0f, -0.7f, -0.3f,    0.0f, 0.0f, 0.0f, 1.0f,     0.0f, 0.0f
    };

    float storageTopVertices[] = {
        // indices              // color                        // texture
        -1.0f, 0.2f, 0.5f,      1.0f, 0.984f, 0.0f, 0.941f,     0.0f, 5.0f,
         1.0f, 0.2f, 0.5f,      1.0f, 0.984f, 0.0f, 0.941f,     5.0f, 5.0f,
        -1.0f, 0.1f, 0.5f,      0.0f, 0.0f, 0.0f, 1.0f,         0.0f, 0.0f,
         1.0f, 0.1f, 0.5f,      0.0f, 0.0f, 0.0f, 1.0f,         5.0f, 0.0f,
        -1.0f, 0.2f, -0.3f,     1.0f, 0.984f, 0.0f, 0.941f,     5.0f, 5.0f,
         1.0f, 0.2f, -0.3f,     1.0f, 0.984f, 0.0f, 0.941f,     0.0f, 5.0f,
        -1.0f, 0.1f, -0.3f,     0.0f, 0.0f, 0.0f, 1.0f,         5.0f, 0.0f,
         1.0f, 0.1f, -0.3f,     0.0f, 0.0f, 0.0f, 1.0f,         0.0f, 0.0f
    };

    float coolerTopVertices[] = {
        // indices              // color                    // texture
        -1.0f, 0.5f, 0.5f,      0.9f, 0.0f, 0.0f, 1.0f,     0.0f, 5.0f,
         1.0f, 0.5f, 0.5f,      0.9f, 0.0f, 0.0f, 1.0f,     5.0f, 5.0f,
        -1.0f, 0.3f, 0.5f,      0.9f, 0.0f, 0.0f, 1.0f,     0.0f, 0.0f,
         1.0f, 0.3f, 0.5f,      0.9f, 0.0f, 0.0f, 1.0f,     5.0f, 0.0f,
        -1.0f, 0.5f, -0.3f,     0.9f, 0.0f, 0.0f, 1.0f,     5.0f, 5.0f,
         1.0f, 0.5f, -0.3f,     0.9f, 0.0f, 0.0f, 1.0f,     0.0f, 5.0f,
        -1.0f, 0.3f, -0.3f,     0.9f, 0.0f, 0.0f, 1.0f,     5.0f, 0.0f,
         1.0f, 0.3f, -0.3f,     0.9f, 0.0f, 0.0f, 1.0f,     0.0f, 0.0f
    };


    unsigned int cubeIndices[] = {
        0, 1, 2,    // First Triangle
        1, 2, 3,    // Second Triangle
        0, 5, 4,    // third triangle  
        0, 5, 1,    // fourth triangle
        0, 6, 2,    // fifth triangle
        6, 0, 4,    // sixth triangle
        1, 5, 3,    // seventh triangle
        5, 3, 7,    //eighth tringle
        6, 3, 7,    // ninth triangle
        3, 6, 2,    // tenth triangle
        4, 7, 5,    // eleventh triangle
        4, 7, 6     // twelth triangle
    };
   
    VAO cubeVAO;                        // generate storage and cooler base vector array object and bind
    cubeVAO.Bind();    
    VBO cubeVBO(cubeVertices, sizeof(cubeVertices));    // generate cubeVBO & EBO and binds data
    EBO cubeEBO(cubeIndices, sizeof(cubeIndices));    
    cubeVAO.LinkVBO(cubeVBO, 0, 3, 9, 0);               // link VBO with VAO.            
    cubeVAO.LinkVBO(cubeVBO, 1, 4, 9, 3);               // parameters - (VBO name, layout id in shader, vector size, stride, offset)
    cubeVAO.LinkVBO(cubeVBO, 2, 2, 9, 7);    
    cubeVAO.Unbind();                                   // unbinds buffers and array object
    cubeVBO.Unbind();
    cubeEBO.Unbind();
    
    VAO storageTopVAO;              // generate storage top vector array object and bind
    storageTopVAO.Bind();    
    VBO storageTopVBO(storageTopVertices, sizeof(storageTopVertices));  // generate cubeTopVBO & cubeTopEBO and binds data
    EBO storageTopEBO(cubeIndices, sizeof(cubeIndices));    
    storageTopVAO.LinkVBO(storageTopVBO, 0, 3, 9, 0);            // link VBO with VAO.
    storageTopVAO.LinkVBO(storageTopVBO, 1, 4, 9, 3);            // parameters - (VBO name, layout id in shader, vector size, stride, offset)
    storageTopVAO.LinkVBO(storageTopVBO, 2, 2, 9, 7);    
    storageTopVAO.Unbind();                                   // unbinds buffers and array object
    storageTopVBO.Unbind();
    storageTopEBO.Unbind();

    VAO coolerTopVAO;              // generate cooler top vector array object and bind
    coolerTopVAO.Bind();
    VBO coolerTopVBO(coolerTopVertices, sizeof(coolerTopVertices));  // generate coolerTopVBO & coolerTopEBO and binds data
    EBO coolerTopEBO(cubeIndices, sizeof(cubeIndices));
    coolerTopVAO.LinkVBO(coolerTopVBO, 0, 3, 9, 0);            // link VBO with VAO.
    coolerTopVAO.LinkVBO(coolerTopVBO, 1, 4, 9, 3);            // parameters - (VBO name, layout id in shader, vector size, stride, offset)
    coolerTopVAO.LinkVBO(coolerTopVBO, 2, 2, 9, 7);
    coolerTopVAO.Unbind();                                   // unbinds buffers and array object
    coolerTopVBO.Unbind();
    coolerTopEBO.Unbind();

    /************************************************************************************
    *               Pyramid Coordinates - Coordinates and Buffer Data
    *************************************************************************************/
    float pyramidVertices[] = {
        // indices              // color                    // texture
         0.0f, 0.5f, 0.0f,      1.0f, 0.6f, 0.0f, 1.0f,     2.5f, 5.0f,
        -0.5f, -0.5f, 0.5f,     1.0f, 0.6f, 0.0f, 1.0f,     0.0f, 0.0f,
         0.5f, -0.5f, 0.5f,     1.0f, 0.6f, 0.0f, 1.0f,     5.0f, 0.0f,
        -0.5f, -0.5f, -0.5f,    1.0f, 0.6f, 0.0f, 1.0f,     5.0f, 0.0f,
         0.5f, -0.5f, -0.5f,    1.0f, 0.6f, 0.0f, 1.0f,     0.0f, 0.0f
    };
    unsigned int pyramidIndices[] = {
        0, 1, 2,  // First Triangle
        0, 1, 3,  // Second Triangle
        0, 2, 4,
        0, 3, 4,
        1, 4, 3,
        1, 4, 2
    };
    // generate vector array object and bind
    VAO pyramidVAO;
    pyramidVAO.Bind();

    // generate VBO1 & EBO1 and binds data
    VBO pyramidVBO(pyramidVertices, sizeof(pyramidVertices));
    EBO pyramidEBO(pyramidIndices, sizeof(pyramidIndices));
    
    // link VBO with VAO. 
    // LinkVBO - (VBO name, layout id in shader, vector size, stride, offset)
    pyramidVAO.LinkVBO(pyramidVBO, 0, 3, 9, 0);
    pyramidVAO.LinkVBO(pyramidVBO, 1, 4, 9, 3);
    pyramidVAO.LinkVBO(pyramidVBO, 2, 2, 9, 7);

    // unbinds buffers and array object
    pyramidVAO.Unbind();
    pyramidVBO.Unbind();
    pyramidEBO.Unbind();

    /********************************************************************************
   *                        Create Cylinder and Cone Objects
   *********************************************************************************/
    // CreateCylinder parameter - (vector reference for data, vector color reference, radius, height, number of sides)
    CreateShape plasticCylinder;
    vector<float> cylinder;
    vector<float> cylinderColor = {0.7f, 0.7f, 0.7f, 1.0f};    
    plasticCylinder.CreateCylinder(cylinder, cylinderColor, 0.2f, 0.6f, 144.f);

    /********************************************************************************
    *                               Texture Objects
    *********************************************************************************/
    // Parameters - (picture, texture type, unit slot, texture format, pixel)
         
    Texture turfTex("turf texture.jpeg", GL_TEXTURE_2D, 0, GL_RGB, GL_UNSIGNED_BYTE);       // turf texture. texture unit 0    
    Texture blackTex("black plastic texture.jpg", GL_TEXTURE_2D, 1, GL_RGB, GL_UNSIGNED_BYTE);      // black plastic texture. texture unit 1    
    Texture yellowTex("yellow plastic texture.jpeg", GL_TEXTURE_2D, 2, GL_RGB, GL_UNSIGNED_BYTE);   // yellow texture. unit texture 2    
    Texture redTex("red plastic texture.jpg", GL_TEXTURE_2D, 3, GL_RGB, GL_UNSIGNED_BYTE);      // red plastic texture. unit texture 3    
    Texture blueTex("blue plastic texture.jpeg", GL_TEXTURE_2D, 4, GL_RGB, GL_UNSIGNED_BYTE);    // blue plastic texture. unit texture 4    
    Texture orangeTex("orange rubber texture.jpg", GL_TEXTURE_2D, 5, GL_RGB, GL_UNSIGNED_BYTE);     // orange plastic texture. unit texture 5    
    Texture stuccoTex("stucco texture.jpeg", GL_TEXTURE_2D, 6, GL_RGB, GL_UNSIGNED_BYTE);       // stucco wall texture. unit texture 6    
    Texture woodTex("wood texture.jpg", GL_TEXTURE_2D, 7, GL_RGB, GL_UNSIGNED_BYTE);        // wood fence texture. unit texture 7    
    Texture dirtTex("dirt texture.jpeg", GL_TEXTURE_2D, 8, GL_RGB, GL_UNSIGNED_BYTE);       // dirt texture. unit texture 8  
    Texture easton2Tex("Easton 2.png", GL_TEXTURE_2D, 9, GL_RGB, GL_UNSIGNED_BYTE);       // easton texture. unit texture 9

    // render loop
    while (!glfwWindowShouldClose(window)) {
        // per-frame time logic
        // --------------------
        float currentFrame = static_cast<float>(glfwGetTime());
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        // input
        processInput(window);        

        // Clear the frame, color buffer, and Z buffer.
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);        

       /**********************************************************************************
       *                                    Draw Planes
       ***********************************************************************************/
        // ---------------------- Draw Turf Plane -------------------//
        // activate shape shader
        shapeShader.Activate();

        // bind array vertex for plane data
        planeVAO.Bind();

        // bind turf texture. setshader unifrom to texture unit 0
        glActiveTexture(GL_TEXTURE0);
        turfTex.Bind();
        turfTex.texUnit(shapeShader, "texUniform", 0);

        // turf model translation. 
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(-90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(5.0f, 5.0f, 5.0f));
        model = scale * rotation;

        // view translation        
        view = camera.GetViewMatrix();

        // projection translation        
        projection = glm::perspective(glm::radians(camera.Zoom), 
            (float)WINDOW_WIDTH / (float)WINDOW_HEIGHT, 0.1f, 100.0f);

        // set translatons to uniforms for turf plane
        shapeShader.setMat4("modelUniform", model);
        shapeShader.setMat4("viewUniform", view);
        shapeShader.setMat4("projectionUniform", projection);

        /*
        // set the light source color and position
        shapeShader.setVec3("lightColor", lightSourceColor);
        shapeShader.setVec3("lightPos", lightSourcePos);
        shapeShader.setVec3("viewPos", camera.Position);

        //material properties - simulates light relflecting off a substance
        materialsShader.setVec3("material.ambient", glm::vec3(0.0f, 0.05f, 0.0f));
        materialsShader.setVec3("material.diffuse", glm::vec3(0.4f, 0.5f, 0.4f));
        materialsShader.setVec3("material.specular", glm::vec3(0.04f, 0.7f, 0.04f));
        materialsShader.setFloat("material.shininess", 0.078125f);
        */

        // light properties - intensity
        shapeShader.setVec3("intensity.ambient", glm::vec3(0.3f, 0.3f, 0.3f));          // ambient is set between (0.1 - 1.0)
        shapeShader.setVec3("intensity.diffuse", lightSourceColor);             // diffuse matches the desired color - use this, glm::vec3(0.2f, 0.5f, 0.1f), for different color
        shapeShader.setVec3("intensity.specular", glm::vec3(0.5f, 0.5f, 0.5f));         // set to vec3(1.0) for full shine
        
        // draw turf plane
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        // unbind turf texture
        turfTex.Unbind();

        //---------------------Draw Cone Base----------------------------//
        
        // activate, bind, and set texture unit 5
        glActiveTexture(GL_TEXTURE5);
        orangeTex.Bind();
        orangeTex.texUnit(shapeShader, "texUniform", 5);

        //cone base model translation. 
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(1.0f, 0.02f, 0.4f));
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(-90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(-0.8f, -0.8f, -0.8f));
        model = translation * scale * rotation;

        // set model translation to uniform
        shapeShader.setMat4("modelUniform", model);

        // draw cone base plane
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        // unbind array vertex & texture for plane data
        planeVAO.Unbind();
        orangeTex.Unbind();

        //---------------------Draw Stucco Wall ----------------------------//
        sidePlaneVAO.Bind();

        // activate, bind, and set texture unit 6
        glActiveTexture(GL_TEXTURE6);
        stuccoTex.Bind();
        stuccoTex.texUnit(shapeShader, "texUniform", 6);

        // stucco wall model translation. 
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(-2.5f, 0.5f, -0.5f));
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(-90.0f), glm::vec3(.0f, 1.0f, 0.0f));
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(7.0f, 7.0f, 7.0f));
        model = translation * scale * rotation;

        // set model translation to uniform
        shapeShader.setMat4("modelUniform", model);

        // draw stucco wall
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        // unbind stucco texture
        stuccoTex.Unbind();

        //--------------------- Draw 2 Fence Walls------------------------------//
        // activate, bind, and set texture unit 7
        glActiveTexture(GL_TEXTURE7);
        woodTex.Bind();
        woodTex.texUnit(shapeShader, "texUniform", 7);

        // right wood fence model translation. 
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(2.5f, 0.5f, -0.5f));
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(-90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(7.0f, 7.0f, 7.0f));
        model = translation * scale * rotation;

        // set model translation to uniform
        shapeShader.setMat4("modelUniform", model);

        // draw right wood fence
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        //
        sidePlaneVAO.Unbind();
        planeVAO.Bind();

        // back wood fence model translation. 
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, 1.5f, -4.0f));
        //rotation = glm::rotate(glm::mat4(1.0f), glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(5.0f, 5.0f, 5.0f));
        model = translation * scale;

        // set model translation to uniform
        shapeShader.setMat4("modelUniform", model);

        // draw back wood fence
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        // unbind wood texture        
        woodTex.Unbind();

        //--------------------- Draw Dirt Plane ----------------------------//
        // activate, bind, and set texture unit 8
        glActiveTexture(GL_TEXTURE8);
        dirtTex.Bind();
        dirtTex.texUnit(shapeShader, "texUniform", 8);

        // dirt floor model translation. 
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, -0.1f, -1.5f));
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(-90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(5.0f, 5.0f, 5.0f));
        model = translation * scale * rotation;

        // set model translation to uniform
        shapeShader.setMat4("modelUniform", model);

        // draw stucco wall
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        // unbind dirt floor and array vertex
        planeVAO.Unbind();
        dirtTex.Unbind();

        /**********************************************************************************
        *                                      Draw Cubes
        ***********************************************************************************/

        // ---------------------- Draw Black Storage Bins -------------------------//               
        cubeVAO.Bind();             // bind array vertex for storage bin and cooler data

        glActiveTexture(GL_TEXTURE1);           // bind black plastic texture. setshader unifrom to texture unit 1
        blackTex.Bind();
        blackTex.texUnit(shapeShader, "texUniform", 1);

        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(15.0f), glm::vec3(0.0f, 1.0f, 0.0f));      // 1st storage bin translation
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(0.9f, 0.9f, 0.9f));
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(-1.0f, 0.65f, -1.0f));
        model = translation * scale * rotation;                
        shapeShader.setMat4("modelUniform", model);                     // set translatons to uniforms
        shapeShader.setMat4("viewUniform", view);
        shapeShader.setMat4("projectionUniform", projection);          
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);           // draw first storage bin

        
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(15.0f), glm::vec3(0.0f, 1.0f, 0.0f));      // 2nd storage bin translation
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(0.9f, 0.9f, 0.9f));
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(-1.0f, 1.7f, -1.0f));
        model = translation * scale * rotation;        
        shapeShader.setMat4("modelUniform", model);                 // set translatons to uniforms
        shapeShader.setMat4("viewUniform", view);
        shapeShader.setMat4("projectionUniform", projection);        
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);          // draw second storage bin

        blackTex.Unbind();                                      // unbind black plastic

        // ------------------------- Draw Blue Cooler -----------------------------//        
        glActiveTexture(GL_TEXTURE4);           // activate, bind, and set  blue plastice texture unit 3
        blueTex.Bind();
        blueTex.texUnit(shapeShader, "texUniform", 4); 

        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(-15.0f), glm::vec3(0.0f, 1.0f, 0.0f));     // model translation for cooler
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(0.8f, 0.8f, 0.8f));
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(1.3f, 0.6f, -1.4f));
        model = translation * rotation * scale;        
        shapeShader.setMat4("modelUniform", model);                 // set uniforms       
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);       // draw cooler
        
        blueTex.Unbind();       // unbind blue plastic texture
        cubeVAO.Unbind();       // unbind storage bin and cooler vertex array

        // ------------------------ Draw Storage Bin Top ------------------------//
        storageTopVAO.Bind();       // bind array vertex for storage bin tops
        
        glActiveTexture(GL_TEXTURE2);       // bind yellow plastic texture. set shader unifrom to texture unit 2
        yellowTex.Bind();
        yellowTex.texUnit(shapeShader, "texUniform", 2);
        
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(15.0f), glm::vec3(0.0f, 1.0f, 0.0f));      // 1st storage bin top translation
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(1.0f, 1.0f, 1.0f));
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(-1.0f, 1.02f, -1.0f));
        model = translation * scale * rotation;        
        shapeShader.setMat4("modelUniform", model);                         // set translatons to uniforms
        shapeShader.setMat4("viewUniform", view);
        shapeShader.setMat4("projectionUniform", projection);        
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);               // draw 1st storage bin

        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(15.0f), glm::vec3(0.0f, 1.0f, 0.0f));      // 2nd storage bin top translation
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(1.0f, 1.0f, 1.0f));
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(-1.0f, 2.0f, -1.0f));
        model = translation * scale * rotation;
        shapeShader.setMat4("modelUniform", model);                         // set translatons to uniforms
        shapeShader.setMat4("viewUniform", view);
        shapeShader.setMat4("projectionUniform", projection);
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);               // draw 2nd storage bin

        
        yellowTex.Unbind();             // unbind yellow  plastic texture
        storageTopVAO.Unbind();        // unbind storage bin tops vertex array

        // ------------------------- Draw Cooler Top -------------------------//        
        coolerTopVAO.Bind(); // 
        
        glActiveTexture(GL_TEXTURE3); // bind black plastic texture. setshader unifrom to texture unit 1
        redTex.Bind();
        redTex.texUnit(shapeShader, "texUniform", 3);
        
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(-15.0f), glm::vec3(0.0f, 1.0f, 0.0f)); // first cube model translation
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(0.8f, 0.8f, 0.8f));
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(1.3f, 0.75f, -1.4f));
        model = translation * scale * rotation;        
        shapeShader.setMat4("modelUniform", model); // set translatons to uniforms
        shapeShader.setMat4("viewUniform", view);
        shapeShader.setMat4("projectionUniform", projection);        
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0); // draw first cube

        // unbind black plastic
        redTex.Unbind();
        coolerTopVAO.Unbind();

        /*********************************************************************************
        *                                   Draw Pyramid
        **********************************************************************************/
        //
        pyramidVAO.Bind();

        //
        glActiveTexture(GL_TEXTURE5);
        orangeTex.Bind();
        orangeTex.texUnit(shapeShader, "texUniform", 5);

        //cone model translation. 
        translation = glm::translate(glm::mat4(1.0f), glm::vec3(1.0f, 0.35f, 0.4f));
        rotation = glm::rotate(glm::mat4(1.0f), glm::radians(180.0f), glm::vec3(1.0f, 0.0f, 0.0f));
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(-0.6f, -0.6f, -0.6f));
        model = translation * scale * rotation;

        shapeShader.setMat4("modelUniform", model);

        // draw cone
        glDrawElements(GL_TRIANGLES, 18, GL_UNSIGNED_INT, 0);

        // unbind array vertex & texture for cone data
        pyramidVAO.Unbind();
        orangeTex.Unbind();

        /**********************************************************************************
        *                                Draw Cylinder
        ***********************************************************************************/

        unsigned int cylinderVBO, cylinderIndices;
        VAO cylinderVAO;
        cylinderVAO.Bind();

        //VBO cylinderVBO(cylinder, sizeof(cylinder));
        // Create VBO
        glGenBuffers(1, &cylinderVBO);
        glBindBuffer(GL_ARRAY_BUFFER, cylinderVBO); // Activates the buffer

        // use vector instead of array
        glBufferData(GL_ARRAY_BUFFER, cylinder.size() * sizeof(float), &cylinder.front(),
            GL_STATIC_DRAW); // Sends vertex or coordinate data to the GPU

        //
        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 9 * sizeof(float), (void*)(0 * sizeof(float)));
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 4, GL_FLOAT, GL_FALSE, 9 * sizeof(float), (void*)(3 * sizeof(float)));
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 9 * sizeof(float), (void*)(7 * sizeof(float)));
        glEnableVertexAttribArray(2);        

        //
        cylinderIndices = cylinder.size() / 9;

        //
        glActiveTexture(GL_TEXTURE5);
        easton2Tex.Bind();
        easton2Tex.texUnit(shapeShader, "texUniform", 5);
        //

        translation = glm::translate(glm::mat4(1.0f), glm::vec3(-2.0f, 0.1f, -0.4f));
        //rotation = glm::rotate(glm::mat4(1.0f), glm::radians(45.0f), glm::vec3(0.0f, 0.0f, 1.0f));
        scale = glm::scale(glm::mat4(1.0f), glm::vec3(2.0f, 2.0f, 2.0f));
        model = translation * scale; // *rotation;

        shapeShader.setMat4("modelUniform", model);

        // draw cone
        glDrawArrays(GL_TRIANGLES, 0, cylinderIndices);

        cylinderVAO.Unbind();
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        /*******************************************************************************
        *                         Draw Light Source Cube 
        ********************************************************************************/
        lightShader.Activate();
        //
        cubeVAO.Bind();

        // translations
        model = glm::mat4(1.0f);
        translation = glm::translate(model, lightSourcePos);
        scale = glm::scale(model, glm::vec3(1.5f));
        model = translation * scale;
        // set uniforms
        lightShader.setMat4("modelUniform", model);
        lightShader.setMat4("viewUniform", view);
        lightShader.setMat4("projectionUniform", projection);
        lightShader.setVec3("lightColor", lightSourceColor);

        // draw light source
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);
        // unbind vao2
        cubeVAO.Unbind();

        

        // glfw: swap buffers and poll IO events (keys pressed/released, mouse moved etc.)
        // -------------------------------------------------------------------------------
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    // optional: de-allocate all resources once they've outlived their purpose:
    // ------------------------------------------------------------------------
    planeVAO.Delete();
    planeVBO.Delete();
    planeEBO.Delete();
    cubeVAO.Delete();
    cubeVBO.Delete();
    cubeEBO.Delete();
    shapeShader.Delete();

    // glfw: terminate, clearing all previously allocated GLFW resources.
    // ------------------------------------------------------------------
    glfwTerminate();
    return 0;
}

// process all input: query GLFW whether relevant keys are pressed/released this frame and react accordingly
// ---------------------------------------------------------------------------------------------------------
void processInput(GLFWwindow* window)
{
    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
        glfwSetWindowShouldClose(window, true);

    if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
        camera.ProcessKeyboard(FORWARD, deltaTime);
    if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
        camera.ProcessKeyboard(BACKWARD, deltaTime);
    if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
        camera.ProcessKeyboard(LEFT, deltaTime);
    if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
        camera.ProcessKeyboard(RIGHT, deltaTime);
    if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS)
        camera.ProcessKeyboard(UP, deltaTime);
    if (glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS)
        camera.ProcessKeyboard(DOWN, deltaTime);
}

// glfw: whenever the window size changed (by OS or user resize) this callback function executes
// ---------------------------------------------------------------------------------------------
void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
    glViewport(0, 0, width, height);
}

// glfw: whenever the mouse moves, this callback is called
// -------------------------------------------------------
void mouse_callback(GLFWwindow* window, double xposIn, double yposIn)
{
    float xpos = static_cast<float>(xposIn);
    float ypos = static_cast<float>(yposIn);

    if (firstMouse)
    {
        lastX = xpos;
        lastY = ypos;
        firstMouse = false;
    }

    float xoffset = xpos - lastX;
    float yoffset = lastY - ypos; // reversed since y-coordinates go from bottom to top

    lastX = xpos;
    lastY = ypos;

    camera.ProcessMouseMovement(xoffset, yoffset);
}

// glfw: whenever the mouse scroll wheel scrolls, this callback is called
// ----------------------------------------------------------------------
void scroll_callback(GLFWwindow* window, double xoffset, double yoffset)
{
    camera.ProcessMouseScroll(static_cast<float>(yoffset));
}

