/*****************************************************************************************
* Program       : ABCU Course Infromation                                                *
* Author        : Michael Terry                                                          *
* Created Date  : 6/24/2023                                                              *
* Modified Date : 4/2/2024                                                               *
* Description   : This program will start by displaying a menu of options. The user      *
*               : will first need to load a data file that contains a list or courses    * 
*               : by typing it's filename. The file name is                              *
*               : "ABCU_Advising_Program_Input.csv". Once the data is load, the user can *
*               : then view the course in alphabetical order, or search a specific       *
*               : course ID to view its prerequsite courses.                             *
* Version 1.0   : This version of this program contains a Insertion Sort and Binary      *
*               : Search algorithms                                                      *
******************************************************************************************/

#include "CoursesABCUFunctions.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <iomanip>
using namespace std;

/*****************************************************************************************
*                               Main Function Program Driver                             *
******************************************************************************************/
int main() {

    //Display welcome message
    cout << "Welcome to the ABCU Course Planner!" << endl;
    cout << "Begin by Selecting a Menu Option" << endl;
    cout << endl;
    cout << "Use Default File Name: ABCU_Advising_Program_Input.csv" << endl;
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
            courseSearch = capitalizeCourseName(courseSearch);
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

