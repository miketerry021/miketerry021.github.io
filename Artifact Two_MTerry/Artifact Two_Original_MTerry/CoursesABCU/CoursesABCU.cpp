/*****************************************************************************************
* Program       : ABCU Course Infromation                                                *
* Author        : Michael Terry                                                          *
* Created Date  : 6/24/2023                                                              *
* Modified Date : 4/2/2024                                                               *
* Description   : This program will start by displaying a menu of options. The user      *
*               : will first need to load a data file that contains a list or courses.   *
*               : by typing it's filename. The file name is                              *
*               : "ABCU_Advising_Program_Input.csv" Once the data is load, the user can  *
*               : then view the course in alphabetical order, or search a specific       *
*               : course ID to view its prerequsite courses.                             *
* Version 1.0   : This version of this program contains a Selection Sort and Linear      *
*               : Search algorithms                                                      *
******************************************************************************************/

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
    cout << "1. Load Course Infromation" << endl;
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
*           Display Course Prerequsites - Displays a course and its prerequsites         *
******************************************************************************************/
void coursePrereq(string& n_courseSearch, vector<vector<string>>& n_courses) {
    
    // boolean for course validation
    bool courseFound = false;

    // char and string for case sensitive user input
    char a;
    string course = n_courseSearch;
    string upper;

    //vector to display prerequsites
    vector<string> tempVect;
    
    // loop to capitalize lower case letter
    for (int k = 0; k < course.length(); ++k) {
        a = n_courseSearch.at(k);
        if (!isdigit(a)) {
            upper = toupper(course.at(k));
            n_courseSearch.replace(k, 1, upper);
            upper.clear();
        }
    } 

    // menu format
    cout << endl;
    cout << setfill('-') << setw(37) << " Course and Prerequsites ";
    cout << setfill('-') << setw(13) << "" << endl;
    cout << endl;

    // outter loop to match user input with course ID in V of V
    for (int i = 0; i < n_courses.size(); ++i) {

        // if user input is found
        if (course.compare(n_courses[i][0]) == 0) {
            
            //display cousre ID and course name
            cout << n_courses[i][0] << ", " << n_courses[i][1] << endl;
            cout << "\tPrerequisites: ";

            //Move vector into temp vector for iteration
            tempVect = n_courses.at(i);

            //if vector contains prerequisites
            if (tempVect.size() > 2) {
                //display prerequsites
                for (int j = 2; j < tempVect.size(); ++j) {
                    cout << tempVect.at(j) << " ";
                }
            }
            //
            courseFound = true;
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
    for (i = 0; i < n_courses.size() - 1; ++i) {                    // O(N)

        //
        courseIdOne = n_courses[i][0];                              // O(1)

        // set min with current minimum index
        min = i;                                                    // O(1)

        // inner loop to compare indexes 
        for (j = i + 1; j < n_courses.size(); ++j) {                    // O(N)
            /* current index and compared to current minimum.
            if current index is smaller, min is set to current index */
            //
            courseIdTwo = n_courses[j][0];                          // O(1)

            if (courseIdTwo.compare(courseIdOne) < 0) {             // O(1 + 2)
                min = j;
                courseIdOne = courseIdTwo;
            }
        }

        // swap the current minimum with smaller one found
        temp = n_courses[min][0];                               // O(1)
        n_courses[min][0] = n_courses[i][0];                    // O(1)
        n_courses[i][0] = temp;                                 // O(1)
    }

    // Big O Analysis Calculation
    // f(N) = O(N) + O(1) + O(1) + O(N) + O(1) + O(1 + 2) + O(1) + O(1) + O(1)
    // Big O = O(N^2)
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

/*****************************************************************************************
*                               Main Function Program Driver                             *
******************************************************************************************/
int main() {

    //Display welcome message
    cout << "Welcome to the ABCU Course Planner!" << endl;
    cout << "Begin by Selecting a Menu Option" << endl;
    cout << endl;
    
    // call display menu
    // initialize userSelection with its return value
    int userSelection = displayMenu();

    // strings for loading data file and course lookup
    string fileName, courseSearch;

    // vector of vectors to hold all courses and prerequsites
    vector<vector<string>> courses;

    // while loop that is the driver of the program
    // loop contains a switch statement with options based on user input
    while (userSelection != 4) {
        
        switch (userSelection) {

        case 1:
            // user input for file containing data             
            cout << "Enter file name and extension: ";
            cin >> fileName;

            // fileParser function
            fileParser(fileName, courses);
            
            // calls displayMenu function to continue program loop
            userSelection = displayMenu();
            break;
        case 2:
            // display all courses
            displayAllCourses(courses);

            // calls displayMenu function to continue program loop
            userSelection = displayMenu();
            break;
        case 3:
            // user input for individual course and prerequsites
            cout << "Enter Course ID: ";
            cin >> courseSearch;

            //display individual course
            coursePrereq(courseSearch, courses);
            courseSearch.clear();

            // calls displayMenu function to continue program loop
            userSelection = displayMenu();
            break;
        default:
            // default case for invalid user input
            cout << "Invalid selection. Please select a menu option" << endl;
            cout << setfill('-') << setw(50) << "" << endl;
            cout << endl;

            // calls displayMenu function to continue program loop
            userSelection = displayMenu();
            break;
        }
    }
    // display program end message
    cout << "Ending program. Thank you" << endl;    
}

