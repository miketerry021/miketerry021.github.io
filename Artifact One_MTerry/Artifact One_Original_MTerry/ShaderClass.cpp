#include"shaderClass.h"
#include <glad/glad.h>
#include <glm/glm.hpp>

#include <iostream>
#include <fstream>
using namespace std;

// Reads a text file and outputs a string with everything in the text file
string get_file_contents(const char* filename) {

	std::ifstream myFile;
	string shader = "";
	string line = "";

	try {
		myFile.open(filename);
		if (myFile.is_open())
		{
			while (getline(myFile, line)) {
				shader += line + "\n";
			}
			myFile.close();
		}

		return(shader);
	}
	catch (std::ifstream::failure e) {
		cout << "ERROR::SHADER::FILE_NOT_SUCCESFULLY_READ" << endl;
	}
}

// Constructor that build the Shader Program from 2 different shaders
Shader::Shader(const char* vertexFile, const char* fragmentFile) {
	// Read vertexFile and fragmentFile and store the strings
	string vertexCode = get_file_contents(vertexFile);
	string fragmentCode = get_file_contents(fragmentFile);

	// Convert the shader source strings into character arrays
	const char* vertexSource = vertexCode.c_str();
	const char* fragmentSource = fragmentCode.c_str();

	// Vertex Shader
	unsigned int vertexShader = glCreateShader(GL_VERTEX_SHADER);
	glShaderSource(vertexShader, 1, &vertexSource, NULL);
	glCompileShader(vertexShader);

	// Check Vertex Shader for Errors
	int success;
	char infoLog[512];
	glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &success);
	if (!success)
	{
		glGetShaderInfoLog(vertexShader, 512, NULL, infoLog);
		cout << "ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" << infoLog << endl;
	}

	// Fragment Shader
	unsigned int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
	glShaderSource(fragmentShader, 1, &fragmentSource, NULL);
	glCompileShader(fragmentShader);

	// Check Fragment Shader for Errors
	glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &success);
	if (!success)
	{
		glGetShaderInfoLog(fragmentShader, 512, NULL, infoLog);
		cout << "ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" << infoLog << endl;
	}

	// Link Vertex and Fragment Shaders
	shaderID = glCreateProgram();
	glAttachShader(shaderID, vertexShader);
	glAttachShader(shaderID, fragmentShader);
	glLinkProgram(shaderID);

	// Check for Linking Errors
	glGetProgramiv(shaderID, GL_LINK_STATUS, &success);
	if (!success) {
		glGetProgramInfoLog(shaderID, 512, NULL, infoLog);
		cout << "ERROR::SHADER::PROGRAM::LINKING_FAILED\n" << infoLog << endl;
	}

	// delete shader objects
	glDeleteShader(vertexShader);
	glDeleteShader(fragmentShader);

}

// Activates the Shader Program
void Shader::Activate()
{
	glUseProgram(shaderID);
}

// Deletes the Shader Program
void Shader::Delete()
{
	glDeleteProgram(shaderID);
}

void Shader::setFloat(const std::string& name, float value) const {
	glUniform1f(glGetUniformLocation(shaderID, name.c_str()), value);
}

void Shader::setVec3(const string& name, const glm::vec3& value) const {
	glUniform3fv(glGetUniformLocation(shaderID, name.c_str()), 1, &value[0]);
}
void Shader::setVec4(const string& name, const glm::vec3& value) const {
	glUniform4fv(glGetUniformLocation(shaderID, name.c_str()), 1, &value[0]);
}

void Shader::setMat4(const string &name, const glm::mat4 &mat) const {
	glUniformMatrix4fv(glGetUniformLocation(shaderID, name.c_str()), 1, GL_FALSE, &mat[0][0]);
}
