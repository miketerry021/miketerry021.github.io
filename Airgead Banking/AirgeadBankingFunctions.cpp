#include "AirgeadBankingFunctions.h"
#include <string>
#include <iomanip>
#include <iostream>
using namespace std;

/* financialInput function that will gather the values used for calculating the compound interest
* investment plan. After gather the user data values, function call will be made to other functions 
* that calculate the investmet plan totals.
*/
void financialInput() {
    //Declare variables used for user input
    int years;
    double initialAmt, monthlyDeposit, interestRate;
    bool invalidInput = false;

    
    //Formatted output for program presentation
    cout << setfill('-') << setw(44) << "" << endl;
    cout << setfill('>') << setw(10) << left << "" << " Compound Interest Plan ";
    cout << setfill('<') << setw(10) << right << "" << endl;

    //Prompt user for initial investment amount
    cout << "Initial Investment Amount: $";
    cin >> initialAmt;

    //Prompt user for monthly deposit amount
    cout << "Monthly Deposit: $";
    cin >> monthlyDeposit;

    //Prompt user for annual interest 
    cout << "Annual Interest: %";
    cin >> interestRate;

    //Prompt user for years of investment value
    cout << "Number of Years: ";
    cin >> years;

    //Clear cin stream buffer to allow Enter key to be pressed in next step
    cin.ignore();

    /*
    * Do While loop that will verify if the user pressed the enter key to continue the program and show the calculated
    * investment plan. One the enter key has been verified, function call will be made to balanceWithoutMonthlyDeposit
    * and balanceWithMonthlyDeposit which will calulate the investment plan with and without the monthly deposit entered
    * by the user.
    */
    do {
        cout << "Press Enter key to continue...";
        
        if (cin.get() == '\n') {
            cout << endl;
            balanceWithoutMonthlyDeposit(years, initialAmt, interestRate);
            balanceWithMonthlyDeposit(years, initialAmt, interestRate, monthlyDeposit);
        }
        else {
            cout << "Invalid input..." << endl;
            invalidInput = true;
            cout << endl;
        }

    } while (invalidInput);

    cout << endl;

    
}

/*
*  balanceWithoutMonthlyDeposit will calculate the compound interest investment plan without the user
*  entered monthly deposti value
*/
void balanceWithoutMonthlyDeposit(int t_years, double t_initialAmt, double t_interestRate) {
   
    //Declare variable used for calculations
    int years = t_years;
    double ytdInterestEarned = 0.0;
    double ytdBalance = t_initialAmt;
    
    //Formatted output for program presentation
    cout << setfill('-') << setw(76) << "" << endl;
    cout << "          Balance and Interest Without Additional Monthly Deposits" << endl;
    cout << setfill('=') << setw(76) << "" << endl;
    cout << setfill(' ') << setw(5) << "" << left << "Year";
    cout << "     |";
    cout << setfill(' ') << setw(5) << "" << left << "Year End Balance";
    cout << "     |";
    cout << setfill(' ') << setw(5) << "" << left << "Year End Earned Interest" << endl;

    //For loop and nested for loop will calculate investment plan over the years specified by the user
    for (int i = 1; i <= years; ++i) {
        for (int j = 0; j < 12; ++j) {
            ytdInterestEarned += ytdBalance * ((t_interestRate / static_cast<double>(100) / static_cast < double>(12)));
            ytdBalance += ytdBalance * ((t_interestRate / static_cast < double>(100) / static_cast < double>(12)));
        }

        //Function call for displaying final output
        displayCalculatedOutput(i, ytdInterestEarned, ytdBalance);
        
    }

}

/*
*  balanceWithMonthlyDeposit will calculate the compound interest investment plan with the user
*  entered monthly deposti value
*/
void balanceWithMonthlyDeposit(int t_years, double t_initialAmt, double t_interestRate, double t_monthlyDeposit) {
    
    //Declare variable used for calculations
    int years = t_years;
    double ytdInterestEarned = 0.0;
    double ytdBalance = t_initialAmt;
    double monthlyDeposit = t_monthlyDeposit;

    //Formatted output for program presentation
    cout << setfill('-') << setw(76) << "" << endl;
    cout << "            Balance and Interest With Additional Monthly Deposits" << endl;
    cout << setfill('=') << setw(76) << "" << endl;
    cout << setfill(' ') << setw(5) << "" << left << "Year";
    cout << "     |";
    cout << setfill(' ') << setw(5) << "" << left << "Year End Balance";
    cout << "     |";
    cout << setfill(' ') << setw(5) << "" << left << "Year End Earned Interest" << endl;

    //For loop and nested for loop will calculate investment plan over the years specified by the user
    for (int i = 1; i <= years; ++i) {
        for (int j = 0; j < 12; ++j) {
            ytdBalance += monthlyDeposit;
            ytdInterestEarned += ytdBalance * ((t_interestRate / static_cast<double>(100) / static_cast <double>(12)));  
            ytdBalance += ytdBalance * ((t_interestRate / static_cast <double>(100) / static_cast <double>(12)));
        }

        //Function call for displaying final output
        displayCalculatedOutput(i, ytdInterestEarned, ytdBalance);
        
    }
}

/*
*  displayCalculatedOutput function will output the final statment after the compound interest investment
*  plan is finished calculating. 
*/
void displayCalculatedOutput(int t_years, double t_ytdInterestEarned, double t_ytdBalance) {
    // declare variables used for display
    int year = t_years;
    double ytdBalance = t_ytdBalance;
    double ytdInterestEarned = t_ytdInterestEarned;

    // output for if the year is leass than 10
    if (year < 10) {
        cout << setfill(' ') << setw(6) << "" << left << "0" << year;
        cout << setfill(' ') << setw(16) << "" << left << fixed << setprecision(2) << "$" << ytdBalance;
        cout << setfill(' ') << setw(24) << "" << left << fixed << setprecision(2) << "$" << ytdInterestEarned << endl;
        cout << endl;
    }
    // output for if the year is greater than 10
    else {
        cout << setfill(' ') << setw(6) << "" << left << year;
        cout << setfill(' ') << setw(16) << "" << left << fixed << setprecision(2) << "$" << ytdBalance;
        cout << setfill(' ') << setw(24) << "" << left << fixed << setprecision(2) << "$" << ytdInterestEarned << endl;
        cout << endl;
    }
}
