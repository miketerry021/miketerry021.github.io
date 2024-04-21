#include"VAO.h"

// Constructor that generates a VAO ID
VAO::VAO()
{
	glGenVertexArrays(1, &arrayID);
}

// Links a VBO to the VAO using a certain layout
void VAO::LinkVBO(VBO& VBO, unsigned int layout, unsigned int indexSize, unsigned int stride, unsigned int offset)
{
	VBO.Bind();
	glVertexAttribPointer(layout, indexSize, GL_FLOAT, GL_FALSE, stride * sizeof(float), (void*)(offset * sizeof(float)));
	glEnableVertexAttribArray(layout);
	VBO.Unbind();
}

// Binds the VAO
void VAO::Bind()
{
	glBindVertexArray(arrayID);
}

// Unbinds the VAO
void VAO::Unbind()
{
	glBindVertexArray(0);
}

// Deletes the VAO
void VAO::Delete()
{
	glDeleteVertexArrays(1, &arrayID);
}