
#ifndef TEXTURE_CLASS_H
#define TEXTURE_CLASS_H


#include<glad/glad.h>

#include"shaderClass.h"

class Texture
{
public:
	unsigned int textureID;
	GLenum type;
	Texture(const char* image, GLenum texType, GLenum unitSlot, GLenum format, GLenum pixelType);

	// Assigns a texture unit to a texture
	void texUnit(Shader& shader, const char* uniform, unsigned int unit);
	// Binds a texture
	void Bind();
	// Unbinds a texture
	void Unbind();
	// Deletes a texture
	void Delete();
};
#endif