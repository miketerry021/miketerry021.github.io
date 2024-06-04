#ifndef COURSESABCUFUNCTIONS_H
#define COURSESABCUFUNCTIONS_H

#include <vector>
#include <string>
using namespace std;

//Function declarations for this program.
int displayMenu();
void displayAllCourses(vector<vector<string>>& n_courses);
string capitalizeCourseName(string& n_courseSearch);
void coursePrereq(string& n_courseSearch, vector<vector<string>>& n_courses);
void selectionSort(vector<vector<string>>& n_courses);
void fileParser(string n_fileName, vector<vector<string>>& n_courses);

#endif
