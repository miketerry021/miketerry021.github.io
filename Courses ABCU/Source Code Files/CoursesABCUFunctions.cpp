#include "CoursesABCUFunctions.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <iomanip>
using namespace std;

/*****************************************************************************************
*                      Display Menu - Displays user selection menu                       *
******************************************************************************************/
int displayMenu() {

    // integer for user selection
    int menuSelection;

    // output for user selection menu
    cout << setfill('=') << setw(27) << " Menu ";
    cout << setfill('=') << setw(23) << "" << endl;
    cout << "1. Load Course Information" << endl;
    cout << "2. Display All Courses" << endl;
    cout << "3. Display Individual Course" << endl;
    cout << "4. Exit Program" << endl;
    cout << endl;
    cout << "Enter a Selection: ";
    cin >> menuSelection;

    // return user selection
    return menuSelection;
}

/*****************************************************************************************
*             Display All Courses - Displays all courses in alphabetical order           *
******************************************************************************************/
void displayAllCourses(vector<vector<string>>& n_courses) {
    // if data file has not be loaded
    if (n_courses.size() == 0) {
        cout << "Data file not loaded. Please load data file" << endl;
    }
    // display all courses
    else {
        cout << endl;
        cout << setfill('-') << setw(27) << " Courses ";
        cout << setfill('-') << setw(23) << "" << endl;
        cout << endl;

        for (int i = 0; i < n_courses.size(); ++i) {
            cout << n_courses[i][0] << ", " << n_courses[i][1] << endl;
        }
    }

    // menu format
    cout << setfill('-') << setw(50) << "" << endl;
    cout << endl;
}

/*****************************************************************************************
*                           Capitalize Course Name                                       *
*       Course Input will be capitalized to match file data and remove case sensitiviy   *
******************************************************************************************/
string capitalizeCourseName(string& n_courseSearch) {
    // char and string for case sensitive user input
    char a;
    string course = n_courseSearch;
    string upper;    

    // loop to capitalize lower case letter
    for (int k = 0; k < course.length(); ++k) {
        a = course.at(k);
        if (!isdigit(a)) {
            upper = toupper(course.at(k));
            course.replace(k, 1, upper);
            upper.clear();
        }
    }

    return course;
}

/*****************************************************************************************
*                           Course & Prerequsites                                        *
*               Returns course & prereqs with Binary Search Tree Algorithm               *
******************************************************************************************/
void coursePrereq(string& n_courseSearch, vector<vector<string>>& n_courses) {

    // boolean for course validation
    bool courseFound = false;  

    // string and integers for BST algorithm
    string course = n_courseSearch;
    int low, mid, high;

    //vector to display prerequsites
    vector<string> tempVect;

    // menu format
    cout << endl;
    cout << setfill('-') << setw(37) << " Course and Prerequsites ";
    cout << setfill('-') << setw(13) << "" << endl;
    cout << endl;

    // low and high point for BST algorithm
    low = 0;
    high = n_courses.size() - 1;

    // Binary Search Tree Algorithm
    while (high >= low) {
        mid = (high + low) / 2;
        if (course.compare(n_courses[mid][0]) == 0) {
            //display cousre ID and course name
            cout << n_courses[mid][0] << ", " << n_courses[mid][1] << endl;
            cout << "\tPrerequisites: ";

            //Move vector into temp vector for iteration
            tempVect = n_courses.at(mid);

            //if vector contains prerequisites
            if (tempVect.size() > 2) {
            //display prerequsites
                for (int j = 2; j < tempVect.size(); ++j) {
                    cout << tempVect.at(j) << " ";
                }
            }
            // set boolean to true and clears input string
            courseFound = true;
            course.clear();
            break;
        }
        // if course input is greater the midpoint
        else if (course.compare(n_courses[mid][0]) > 0) {
            low = mid + 1;
        }
        // if course input is less than midpoint
        else if (course.compare(n_courses[mid][0]) < 0) {
            high = mid - 1;
        }

    }
    // output is user input is not found
    if (!courseFound) {
        cout << "Course not found. Try search again." << endl;
    }

    // menu format
    cout << endl;
    cout << setfill('-') << setw(50) << "" << endl;
    cout << endl;
}

/*****************************************************************************************
*                 Selection Sort - Sorts courses in alphabetical order                   *
******************************************************************************************/
void selectionSort(vector<vector<string>>& n_courses) {
    //define  variables for loops and min index
    int i, j, min;

    string courseIdOne, courseIdTwo, temp;
    //vector<string> temp;

    // outter for loop to determine min index
    for (i = 0; i < n_courses.size() - 1; ++i) {                  

        //
        courseIdOne = n_courses[i][0];                              

        // set min with current minimum index
        min = i;                                                    

        // inner loop to compare indexes 
        for (j = i + 1; j < n_courses.size(); ++j) {                    
            /* current index and compared to current minimum.
            if current index is smaller, min is set to current index */
            //
            courseIdTwo = n_courses[j][0];                         

            if (courseIdTwo.compare(courseIdOne) < 0) {             
                min = j;
                courseIdOne = courseIdTwo;
            }
        }

        // swap the current minimum with smaller one found
        temp = n_courses[min][0];                              
        n_courses[min][0] = n_courses[i][0];                    
        n_courses[i][0] = temp;                                 
    }
}

/*****************************************************************************************
*                     File Parser - Parses data file for course info                     *
******************************************************************************************/
void fileParser(string n_fileName, vector<vector<string>>& n_courses) {
    // strings for file name and parsing
    string fileName = n_fileName;
    string line, courseID, courseName, prerequisiteCourse;

    // vector to parse courses
    vector<string> temp;

    // stream to read file
    ifstream fileRead;

    // try block 
    try {
        // open file in stream
        fileRead.open(fileName);
        // if file is open
        if (fileRead.is_open())
        {
            // loop to run until the end of file is reached
            while (!fileRead.eof()) {
                // read course ID and name into stream
                getline(fileRead, line);
                stringstream ss(line);
                getline(ss, courseID, ',');
                getline(ss, courseName, ',');

                // add course iD and name to vector                 
                temp.push_back(courseID);
                temp.push_back(courseName);

                // read possible prerequsite course
                getline(ss, prerequisiteCourse, ',');

                // read possible other prerequsite courses
                while (prerequisiteCourse.size() > 0) {
                    temp.push_back(prerequisiteCourse);
                    prerequisiteCourse.clear();
                    getline(ss, prerequisiteCourse, ',');
                }
                // add vector to V of V               
                n_courses.push_back(temp);

                // clear strings and vector
                courseID.clear();
                courseName.clear();
                temp.clear();
            }
            // close file
            fileRead.close();

            //delete last blank element
            n_courses.pop_back();

            // call selection sort
            selectionSort(n_courses);

            // confirm file was parsed and sorted
            cout << "File Succesfully Loaded & Sorted" << endl;
            cout << setfill('-') << setw(50) << "" << endl;
            cout << endl;
        }
        else
            throw runtime_error("File failed to open");
    }
    catch (runtime_error& error) {
        cout << error.what() << endl;
        cout << endl;
    }
}