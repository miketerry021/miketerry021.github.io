#include "InventoryOutput.h"
#include <string>
#include <cstring>
#include <cctype>
#include <fstream>
#include <vector>
#include <iostream>
#include <list>
using namespace std;

//default constructor 
InventoryOutput::InventoryOutput() {
	groceryItem = "none";
	itemQuantity = 0;
}

//overloaded constructor with string parameter
InventoryOutput::InventoryOutput(string n_groceryItem) {
	groceryItem = n_groceryItem;
	itemQuantity = 0;
}

/*
*	FileReadWriteFunction will read the grocery item file into a vector. That vector will then be used to write
*	a file showing the frequency of the items on the grocery item file
*/
void InventoryOutput::FileReadWriteFunction() {
	//declare file streams
	ifstream inputFile;
	ofstream outputFile;

	//declares variables to read and write files
	vector<InventoryOutput> groceryList;
	InventoryOutput vectorInput;
	string listItem;
	int quantity;
	bool itemFound;

	//code segment used to read the grocery items file into a vector
	inputFile.open("CS210_Project_Three_Input_File.txt");
	if (inputFile.is_open()) {
		inputFile >> listItem;
		quantity = 1;
		vectorInput.SetItemAndQuantity(listItem, quantity);
		groceryList.push_back(vectorInput);
		while (!inputFile.eof()) {
			itemFound = false;
			inputFile >> listItem;
			for (int i = 0; i < groceryList.size(); ++i) {				
				if (listItem == groceryList.at(i).GetItem()) {
					quantity = groceryList.at(i).GetQuantity();
					vectorInput.SetItemAndQuantity(listItem, quantity + 1);
					groceryList.at(i) = vectorInput;
					itemFound = true;
					break;
				}
			}
			if (!itemFound) {
				quantity = 1;
				vectorInput.SetItemAndQuantity(listItem, quantity);
				groceryList.push_back(vectorInput);
			}
		}
	}

	//close input file stream
	inputFile.close();

	//code segment used to write vector into frequency file
	outputFile.open("Frequency.txt");
	if (outputFile.is_open()) {
		for (int i = 0; i < groceryList.size(); ++i) {
			if (i < groceryList.size() - 1){
				outputFile << groceryList.at(i).GetItem() << " " << groceryList.at(i).GetQuantity() << endl;
			}
			else {
				outputFile << groceryList.at(i).GetItem() << " " << groceryList.at(i).GetQuantity();
			}
		}
	}

	//close output file stream
	outputFile.close();

	//display message confirming output file creation.
	cout << "Item Frequency File Successfully Created." << endl;
	cout << "File Download Feature Coming Soon." << endl;
	cout << endl;
}

// The PrintOneItem function will display for one item indicated by the user.
void InventoryOutput::PrintOneItem() {
	//declare input file stream
	ifstream inputFile;

	//declare variables for displaying individual items
	string listItem, lowercase, currItem, test;
	int quantity;
	bool itemFound;

	//insrt object groceryItem into temp string
	currItem = this->GetItem();	

	//Uppercase grocery item conversion	
	lowercase = toupper(currItem.at(0));
	currItem.replace(0, 1, lowercase);

	// call ItemPlural funtion with desired item
	currItem = ItemsPlural(currItem);

	//while loop that will display the quantity for one item
	inputFile.open("Frequency.txt");
	if (inputFile.is_open()) {
		while (!inputFile.eof()) {
			itemFound = false;
			inputFile >> listItem;
			inputFile >> quantity;
			if (listItem.compare(currItem) == 0)  {
				cout << "Grocery Item: " << listItem << " - " << quantity << endl;
				itemFound = true;
				break;
			}
		}
		if (!itemFound) {
			cout << endl;
			cout << "Item not found. Enter another item." << endl;
		}
	}

	//inputFile stream close
	inputFile.close();
}

// The PrintAllItems function will display all grocery items and their quantity or all items as a histogram
void InventoryOutput::PrintAllItems(int n_numSelection) {
	//declare variables used for displaying all item quantity or all items histogram
	string listItem;
	int numSelection = n_numSelection;
	int quantity;
	
	//declare inputFile stream
	ifstream inputFile;

	//will display all items quantity or all items histogram based on user input
	if (numSelection == 2) {
		inputFile.open("Frequency.txt");
		cout << "\tGrocery Items Total" << endl;
		if (inputFile.is_open()) {
			while (!inputFile.eof()) {
				inputFile >> listItem;
				inputFile >> quantity;
				if (listItem.size() > 7) {
					cout << listItem << "\t" << quantity << endl;
				}
				else {
					cout << listItem << "\t\t" << quantity << endl;
				}
			}
		}

		inputFile.close();
	}
	if (numSelection == 3) {
		inputFile.open("Frequency.txt");
		cout << "\tGrocery Items Histogram Chart" << endl;
		if (inputFile.is_open()) {
			while (!inputFile.eof()) {
				inputFile >> listItem;
				inputFile >> quantity;
				if (listItem.size() > 7) {
					cout << listItem << "\t";
					for (int i = 1; i <= quantity; ++i) {
						cout << "*";
					}
					cout << endl;
				}
				else {
					cout << listItem << "\t\t";
					for (int i = 1; i <= quantity; ++i) {
						cout << "*";
					}
					cout << endl;
				}
			}
		}

		//inputFile stream close
		inputFile.close();
	}
}

// The ItemsPLural function will return a grocery items plural name, which is how they're named in the data file.
// This feature is input validation and will allow the user to enter an item in singular form
string InventoryOutput::ItemsPlural(string n_groceryItem) {
	// list for grocery items and their plural names
	list<pair<string, string>> plurals{ {"Radish", "Radishes"}, {"Pea", "Peas"}, {"Cranberry","Cranberries"}, {"Potato", "Potatoes"},
		{"Cucumber", "Cucumbers"}, {"Peach", "Peaches"}, {"Beet", "Beets"}, {"Onion", "Onions"}, {"Yam", "Yams"}, {"Apple", "Apples"},
		{"Lime", "Limes"}, {"Pumpkin", "Pumpkins"}, {"Pear", "Pears"} };
	
	// string for desired grocery item
	string groceryItem = n_groceryItem;

	// iterate through list and return plural grocery item
	for (pair<string, string> pluralItem : plurals) {
		if (groceryItem.compare(pluralItem.first) == 0) {
			return pluralItem.second;
		}
	}

	// return if item is not found
	return groceryItem;	
}

//sets the object grocery item and quantity
void InventoryOutput::SetItemAndQuantity(string n_groceryItem, int n_itemQuantity) {
	this->groceryItem = n_groceryItem;
	this->itemQuantity = n_itemQuantity;
}

//returns the object grocery item 
string InventoryOutput::GetItem() const {
	return this->groceryItem;
}

//returns the object item quantity
int InventoryOutput::GetQuantity() const {
	return this->itemQuantity;
}