#ifndef CREATE_SHAPE_H
#define CREATE_SHAPE_H

#include <vector>
using namespace std;

class CreateShape {
	public:
		//static void UBuildPyramid(GLMesh& mesh);

		//static void UBuildCube(GLMesh& mesh);

		//static void UBuildCone(GLMesh& mesh);

		static void CreateCylinder(vector<float> &cylinder, vector<float> &color, float radius,
			float height, float numSides);


};
#endif