/*
* Author:       Michael Terry
* Date:         2/4/23
* Description:  This program calculate a compound interest investment plan based on the initial deposit, monthly deposit,
*               interest rate, and amount of years of investment entered by the user
*/

#include "AirgeadBankingFunctions.h"
#include <iostream>
#include <iomanip>
#include <string>
using namespace std;

int main()
{
    // Declare char used to continue program and create another investment plan
    char continueChar;   

    /*
    * Do while loop that will call a function to start the program to calculate the compound interest investment plan
    * After the program finishes, the user will be asked weather they wish to create another investment plan or end
    * the program.
    */
    do {

        financialInput();
        cout << "Would you like to view another investment plan?" << endl;
        cout << "Enter Y or y to continue. Any other letter will end program: ";
        cin >> continueChar;
        cout << endl;

    } while ((continueChar == 'Y') || (continueChar == 'y'));

    cout << "End Program" << endl;
    cout << "Thank you for banking with Airgead Banking. Have a wonderful day!" << endl;

     return 0;
}

