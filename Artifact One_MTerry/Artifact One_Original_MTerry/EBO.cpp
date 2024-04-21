#include"EBO.h"

// Constructor that generates a Elements Buffer Object and links it to indices
EBO::EBO(unsigned int* indices, GLsizeiptr size)
{
	glGenBuffers(1, &indexID);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexID);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, size, indices, GL_STATIC_DRAW);
}

// Binds the EBO
void EBO::Bind()
{
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexID);
}

// Unbinds the EBO
void EBO::Unbind()
{
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
}

// Deletes the EBO
void EBO::Delete()
{
	glDeleteBuffers(1, &indexID);
}