#ifndef VBO_CLASS_H
#define VBO_CLASS_H

#include<glad/glad.h>
#include <vector>
using namespace std;

class VBO
{
public:
	// Reference ID of the Vertex Buffer Object
	unsigned int vertexID;
	// Constructor that generates a Vertex Buffer Object and links it to vertices
	VBO(float* vertices, GLsizeiptr size);

	// Constructor that generates a Vertex Buffer Object and links it to vertices
	//VBO(vector<float>* vertices, GLsizeiptr size);

	// Binds the VBO
	void Bind();
	// Unbinds the VBO
	void Unbind();
	// Deletes the VBO
	void Delete();
};

#endif