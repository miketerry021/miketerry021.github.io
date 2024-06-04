#include "DisplayMenuFunction.h"
#include "InventoryOutput.h"
#include <iostream>
#include <iomanip>
#include <string>
#include <vector>
#include <stdexcept>
using namespace std;

/*
*	The displayMenu function is the driving function of the program. It display the menu option,
*	accepts the users input, and calls all processing function releated to the users input.
*/
void displayMenu() {
	// groceries object is created from InventoryOutput class. It then calls class function FileReadWriteFunction
	InventoryOutput groceries;	
	groceries.FileReadWriteFunction();	

	// declare user input variables
	int numSelection;
	string groceryItem;
	char endProgram = 'N';

	// output heading for user selection menu
	cout << endl;
	cout << "Corner Grocer Inventory Report" << endl;
	cout << "Please Select Menu Option to Print On-Screen Report" << endl;
	cout << endl;

	/* 
	*	do while loop that will display the user selection menu. loop contains exception handling that validates
	*	if the user enters valid inputs. program functions will be call based on user input
	*/
	do {
		try {
			cout << setfill('*') << setw(18) << "" << " Menu Options ";
			cout << setfill('*') << setw(18) << "" << endl;
			cout << "*     1. Individual Item Frequency               *" << endl;
			cout << "*     2. List All Items Frequency (Numbered)     *" << endl;
			cout << "*     3. List All Items Frequency (Chart)        *" << endl;
			cout << "*     4. End Program                             *" << endl;
			cout << setfill('*') << setw(50) << "" << endl;
			cout << endl;

			cout << "Enter Selection: ";
			cin >> numSelection;

			if (cin.fail()) {
				throw logic_error("Invalid Input. Enter Valid Menu Option");		//exception for not entering a number
			}
			if ((numSelection < 1) || (numSelection > 4)) {
				throw runtime_error("Invalid Input. Enter Valid Menu Option");		//exception for entering out-of-range number
			}
			else {
				if (numSelection == 1) {							//selection for individual item
					cout << "Enter Item Name: ";
					cin >> groceryItem;
					InventoryOutput individualItem(groceryItem);
					individualItem.PrintOneItem();
				} 
				if (numSelection == 2) {							//selection for all items quantity
					cout << endl;
					InventoryOutput printItems;
					printItems.PrintAllItems(numSelection);
				}
				if (numSelection == 3) {							//selection for all items histogram
					cout << endl;
					InventoryOutput printItems;
					printItems.PrintAllItems(numSelection);
				}
				if (numSelection == 4) {							//selection to end the program
					cout << endl;
					cout << "Would You Like To End Program? (Enter Y) ";
					cin >> endProgram;
				}
			}				
		}
		catch (logic_error& nonNumErr) {			//handler for not entering a number
			cin.clear();
			cin.ignore();
			cout << nonNumErr.what() << endl;
		}
		catch (runtime_error& wrongNumErr) {		//handler for out-of-range number
			cout << wrongNumErr.what() << endl;
		}

		cout << endl;

	} while (endProgram == 'N');
}