#ifndef SHADER_CLASS_H
#define SHADER_CLASS_H

#include<glad/glad.h>
#include <glad/glad.h>
#include <glm/glm.hpp>

#include<string>
#include<fstream>
#include<sstream>
#include<iostream>
#include<cerrno>

using namespace std;

string get_file_contents(const char* filename);

class Shader {
public:
	// Reference ID of the Shader Program
	unsigned int shaderID;

	// Constructor that build the Shader Program from 2 different shaders
	Shader(const char* vertexFile, const char* fragmentFile);

	// Activates the Shader Program
	void Activate();
	// Deletes the Shader Program
	void Delete();

	//
	void setFloat(const std::string& name, float value) const;

	//
	void setVec3(const string& name, const glm::vec3& value) const;

	//
	void setVec4(const string& name, const glm::vec3& value) const;

	//
	void setMat4(const string& name, const glm::mat4 &mat) const;
};
#endif
