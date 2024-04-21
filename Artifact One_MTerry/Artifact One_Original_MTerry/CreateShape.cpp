#include <GLFW/glfw3.h>
#include <glm/glm.hpp>
#include <glm/gtx/transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <vector>
#include <cmath>

#include "CreateShape.h"

using namespace std;

void CreateShape::CreateCylinder(vector<float> &cylinder, vector<float> &color, float radius,
	float height, float numSides) {

    float r = radius;
    float h = height;
    float s = numSides;

    constexpr float PI = 3.14f;
    const float sectorStep = 2.0f * PI / s;

    for (auto i = 0; i < s; i++)
    {
        float one = 0.5f + r * cos(i * sectorStep);
        float two = 0.5f + r * sin(i * sectorStep);

        one -= 0.5f;
        one *= 2.0f;

        two -= 0.5f;
        two *= 2.0f;

        color[0] = one;
        color[2] = two;

                                                                                                         
        // triangle fan, bottom
        cylinder.insert(cylinder.end(), { 0.5f, 0.0f, 0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 0.5f, 0.5f });			// origin (0.5, 0.5) works best for textures
        cylinder.insert(cylinder.end(), { 0.5f + r * cos(i * sectorStep) ,			// x
                                        0.0f ,										// y
                                        0.5f + r * sin(i * sectorStep) ,			// z
                                        color[0], color[1], color[2], color[3],		    // color data r g b a
                                        0.5f + (0.5f * cos((i)*sectorStep)) ,		    // texture x; adding the origin for proper alignment
                                        (0.125f + (0.125f * sin((i)*sectorStep))) });		// texture y


        cylinder.insert(cylinder.end(), { 0.5f + r * cos((i + 1) * sectorStep) ,
                                        0.0f ,
                                        0.5f + r * sin((i + 1) * sectorStep) ,
                                        color[0], color[1], color[2], color[3],		    // color data r g b a
                                        0.5f + (0.5f * cos((i + 1) * sectorStep)) ,
                                        (0.125f + (0.125f * sin((i + 1) * sectorStep))) });


    }

    for (auto i = 1; i < s + 1; i++)
    {

        float one = 0.5f + r * cos(i * sectorStep);
        float two = 0.5f + r * sin(i * sectorStep);

        one -= 0.5f;
        one *= 2.0f;

        two -= 0.5f;
        two *= 2.0f;

        color[0] = one;
        color[2] = two;

        // triangle fan, top
        cylinder.insert(cylinder.end(), { 0.5f, h, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.5f, 0.5f });			// origin (0.5, 0.5) works best for textures
        cylinder.insert(cylinder.end(), { 0.5f + r * cos(i * sectorStep) ,
                                        h ,										// build this fan the 'l' value away from the other fan
                                        0.5f + r * sin(i * sectorStep) ,
                                        color[0], color[1], color[2], color[3],	    // color data r g b a
                                        0.5f + (0.5f * cos((i)*sectorStep)) ,
                                        0.875f + (0.125f * sin((i)*sectorStep)) });
        cylinder.insert(cylinder.end(), { 0.5f + r * cos((i + 1) * sectorStep) ,
                                        h ,
                                        0.5f + r * sin((i + 1) * sectorStep) ,
                                        color[0], color[1], color[2], color[3], 	// color data r g b a
                                        0.5f + (0.5f * cos((i + 1) * sectorStep)) ,
                                        0.875f + (0.125f * sin((i + 1) * sectorStep)) });
    }

    // since all side triangles have the same points as the fans above, the same calculations are used
    // to wrap the texture around the cylinder, the calculated points are used to determine which section of
    // the texture to clamp to the corresponding point.
    constexpr float x = 2.0f;       // original x = 1.0f
    float j = 1.0f / (s / x);	// for calculating texture location; change 'x' to increase or decrease how many times the texture wraps around the cylinder
    float k = 0.0f;				// for texture clamping

    // sides
    for (auto i = 0; i < s; i++)
    {
        float one = 0.5f + r * cos(i * sectorStep);
        float two = 0.5f + r * sin(i * sectorStep);

        one -= 0.5f;
        one *= 2.0f;

        two -= 0.5f;
        two *= 2.0f;

        color[0] = one;
        color[2] = two;

        cylinder.insert(cylinder.end(), { 0.5f + r * cos(i * sectorStep) ,
                                        0.0f ,
                                        0.5f + r * sin(i * sectorStep) ,
                                        color[0], color[1], color[2], color[3],			// color data r g b a
                                        k ,
                                        0.25f });

        cylinder.insert(cylinder.end(), { 0.5f + r * cos(i * sectorStep) ,
                                        h ,
                                        0.5f + r * sin(i * sectorStep) ,
                                        color[0], color[1], color[2], color[3],			// color data r g b a
                                        k ,
                                        0.75f });
        cylinder.insert(cylinder.end(), { 0.5f + r * cos((i + 1) * sectorStep) ,
                                        h ,
                                        0.5f + r * sin((i + 1) * sectorStep) ,
                                        color[0], color[1], color[2], color[3],			// color data r g b a
                                        k + j ,
                                        0.75f });

        cylinder.insert(cylinder.end(), { 0.5f + r * cos((i + 1) * sectorStep) ,
                                        h ,
                                        0.5f + r * sin((i + 1) * sectorStep) ,
                                        color[0], color[1], color[2], color[3],			// color data r g b a
                                        k + j ,
                                        0.75f });
        cylinder.insert(cylinder.end(), { 0.5f + r * cos((i + 1) * sectorStep) ,
                                        0.0f ,
                                        0.5f + r * sin((i + 1) * sectorStep) ,
                                        color[0], color[1], color[2], color[3],			// color data r g b a
                                        k + j ,
                                        0.25f });

        cylinder.insert(cylinder.end(), { 0.5f + r * cos(i * sectorStep) ,
                                        0.0f ,
                                        0.5f + r * sin(i * sectorStep) ,
                                        color[0], color[1], color[2], color[3],			// color data r g b a
                                        k,
                                        0.25f });
        k += j;
    }

}