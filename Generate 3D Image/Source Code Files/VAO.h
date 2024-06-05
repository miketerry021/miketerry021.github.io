#ifndef VAO_CLASS_H
#define VAO_CLASS_H

#include<glad/glad.h>
#include"VBO.h"

class VAO
{
public:
	// ID reference for the Vertex Array Object
	unsigned int arrayID;
	// Constructor that generates a VAO ID
	VAO();

	// Links a VBO to the VAO using a certain layout
	void LinkVBO(VBO& VBO, unsigned int layout, unsigned int indexSize, unsigned int stride, unsigned int offset);
	// Binds the VAO
	void Bind();
	// Unbinds the VAO
	void Unbind();
	// Deletes the VAO
	void Delete();
};
#endif