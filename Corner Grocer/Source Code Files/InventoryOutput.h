#ifndef INVENTORYOUTPUT_H
#define INVENTORYOUTPUT_H

#include <string>
using namespace std;

// declare InventoryOutput class. declare class functions and variables
class InventoryOutput {
	public:		
		InventoryOutput();
		InventoryOutput(string n_groceryItem);
		void FileReadWriteFunction();	
		void PrintOneItem();
		void PrintAllItems(int n_numSelection);

	private:
		string groceryItem;
		int itemQuantity;
		string ItemsPlural(string n_groceryItem);
		void SetItemAndQuantity(string n_groceryItem, int n_itemQuantity);
		string GetItem() const;
		int GetQuantity() const;
};

#endif

