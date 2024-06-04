/*
 * Class			:	CoffeeShop
 * Programmer		:	Michael Terry
 * Date 			:	11/05/2023
 * Description		: 	This Class is the driver Class for the program. When this
 * 					:	program is executed, the user will be prompted to enter a name and
 * 					: 	number of drinks wanted. The user will then select a type of drink
 * 					:	to be ordered. Once the user inputs all of the desired information,
 * 					:	the total cost of the desired drinks will be displayed for the user * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

public class CoffeeShop extends JFrame 
	implements ActionListener, ItemListener
{
	//Create objects for user input and interface
	JPanel coffeePanel = new JPanel();
	JLabel companyNameLabel = new JLabel("Morning Lift Coffee");
	JLabel programmerLabel = new JLabel("Programmed by: Michael Terry");
	JLabel customerLabel = new JLabel("Customer:");
	JLabel quantityLabel = new JLabel("Quantity:   ");
	JLabel drinkPriceLabel = new JLabel("            Price of Drink:            ");
	JTextField customerNameTextField = new JTextField(15);
	JTextField quantityTextField = new JTextField(15);
	JTextField drinkPriceTextField = new JTextField(20);
	String coffeeString[] = {" ", "Mocha","Latte", "Drip"};
	JComboBox coffeeComboBox = new JComboBox(coffeeString);
	JCheckBox decafCheckBox = new JCheckBox("Decaf");
	JButton addOrderButton = new JButton("Add to Order");
	JButton completeOrderButton = new JButton("Complete Order");
	JButton clearButton = new JButton("Clear");
	JTextArea resultsTextArea = new JTextArea(10,20);
	JScrollPane scrollbarPane = new JScrollPane(resultsTextArea);
	
	/******************************************************************************************
	 * - The main method - 
	 ******************************************************************************************/
	public static void main(String[] args)
	{
		//Create a constructor from the CoffeeShop Class
			CoffeeShop coffeePOS = new CoffeeShop();
			coffeePOS.setDefaultCloseOperation(EXIT_ON_CLOSE);			
	}
	
	/*********************************************************************************************
	 * - The CoffeeShop Constructor -
	 * This constructor will call the displayInterface method, the buttonPressed
	 * method, and it will add the JPanel to the JFrame and set its parameters.
	 *********************************************************************************************/
	public CoffeeShop()
	{			
		//Call the displayInterface method
		displayInterface();
		
		//Call the buttonPressed method
		buttonPressed();
		
		//Add the JPanel to the JFrame, and set the size and visible
		this.add(coffeePanel);
		this.setSize(300, 500);
		this.setVisible(true);
		
	}
	
	/*************************************************************************************************
	 * - The displayInterface Method -
	 * This method will add the JComponents to the JPanel. This will build the
	 * interface for the user input
	 *************************************************************************************************/
	public void displayInterface()
	{
		//Create Font object
		Font titleFont = new Font("Times News Roman", Font.PLAIN, 30);
		
		//Add the JSwing components to the JPanel
		companyNameLabel.setFont(titleFont);
		companyNameLabel.setForeground(Color.blue);
		coffeePanel.add(companyNameLabel);
		coffeePanel.add(customerLabel);
		coffeePanel.add(customerNameTextField);
		coffeePanel.add(quantityLabel);
		coffeePanel.add(quantityTextField);
		coffeePanel.add(decafCheckBox);
		coffeePanel.add(coffeeComboBox);
		coffeePanel.add(drinkPriceLabel);
		coffeePanel.add(drinkPriceTextField);
		coffeePanel.add(addOrderButton);
		coffeePanel.add(completeOrderButton);
		coffeePanel.add(clearButton);
		coffeePanel.add(scrollbarPane);
		coffeePanel.add(programmerLabel);
		
	}
	
	/****************************************************************************
	 * - The buttonPressed Method -
	 * This method will add the ActionListener to the objects addOrderButton,
	 * quantityField, completeOrderButton, and the clearButton. This will 
	 * allow these objects on the interface to trigger calculations for the user.
	 *****************************************************************************/
	public void buttonPressed()
	{
		//Add the ActionListener to the addOrderButton, completeOrderButton and quantityTextField
		//Add the ItemListener to the decafCheckBox and coffeeComboBox
		addOrderButton.addActionListener(this);
		quantityTextField.addActionListener(this);
		completeOrderButton.addActionListener(this);
		clearButton.addActionListener(this);
		decafCheckBox.addItemListener(this);
		coffeeComboBox.addItemListener(this);
		
	}
	
	/*************************************************************************************************
	 * -The itemStateChanged method-
	 * This method will execute an If Else statement based on the the user selection from
	 * the coffeeComboBox. The price of the selected drink will display in the drinkPriceTextField
	 * based on which drink the user selects
	 *************************************************************************************************/
	public void itemStateChanged(ItemEvent evt)
	{
		//Create local variables and String object to be used in calculations
		int drinkSelectIndexInteger;
		float drinkPriceFloat;
		String drinkPriceString;
		
		//Get selected index from coffeeComboBox and store in drinkSelectIndexInteger
		drinkSelectIndexInteger = coffeeComboBox.getSelectedIndex();
		
		//Create coffeeDrinkSelection object from CoffeeCalculation(drinkSelectIndexInteger) method
		CoffeeCalculation coffeeDrinkSelection = new CoffeeCalculation(drinkSelectIndexInteger);
		
		//Retrieve data from getDrinkPrice method and store in drinkPrice float
		drinkPriceFloat = coffeeDrinkSelection.getDrinkPrice();
		
		//Convert drinkPriceFloat into a String and store into drinkPriceString
		drinkPriceString = Float.toString(drinkPriceFloat);
		
		//Display drinkPriceString in the drinkPriceTextField
		drinkPriceTextField.setText("$" + drinkPriceString);
		
	}
	
	/********************************************************************************
	 * - The actionPerformed Method - 
	 * This method will create an object that will store the triggered event. The
	 * triggered event will then execute an If statement depending on which button is
	 * selected.
	 ********************************************************************************/
	public void actionPerformed(ActionEvent evt)
	{
		//Create an object that will store the triggered event 
		Object selectedOption = evt.getSource();
		
		//If the addOrderButton is pressed or the quantityTextField is triggered by the "Enter"
		//button from the user, The validation method will execute. If validation returns a true
		//value, the purchaseCoffee method will execute. If validation fails, coffeeComboBox will
		//be set to index 0. If clearButton is pressed, clear method will execute.
		//If completeOrderButton is pressed, totalSales method will execute.
		if(selectedOption ==  addOrderButton || selectedOption == quantityTextField)
		{
			if(validation())
			{	
				purchaseCoffee();
			}
			else
			{
				coffeeComboBox.setSelectedIndex(0);
			}
		}
		
		if(selectedOption == clearButton)
		{
			clear();
		}
		
		if(selectedOption == completeOrderButton)
		{
			totalSales();
		}
						
	}
		
	/***********************************************************************************
	 * - The Validation method -
	 * This method will prompt the user for input if the user does not enter any data
	 * in the text boxes on the interface or selects a drink. The program will not
	 * calculate until the user inputs all of their data
	 ***********************************************************************************/
	public boolean validation()
	{
		//Create String objects and boolean variable to calculate validation
		String drinkString, mochaString = "Mocha", latteString = "Latte",
				dripString = "Drip";
		boolean validationBoolean = false;
		
		//Store selected coffeeComboBox item in drinkString
		drinkString = coffeeComboBox.getSelectedItem().toString();
		
		/*Creates an If Else statement that states that if the isn't any user input
		 * in either the customerNameTextField, quantityTextField, or a drink is selected,
		 * then a message box will appear and remind them to enter data. The program
		 * will not calculate any further until the user inputs all data
		 */

		if(!(customerNameTextField.getText()).equals(""))
		{
			if(!(quantityTextField.getText()).equals(""))
			{
				if(drinkString.equals(mochaString) || drinkString.equals(latteString)
						|| drinkString.equals(dripString))
				{
					validationBoolean = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please Select Drink");
					coffeeComboBox.setSelectedIndex(0);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please Enter Quantity");
				quantityTextField.requestFocus();	
			}
		}
		else
		{	
			JOptionPane.showMessageDialog(null, "Please Enter Customer Name");
			customerNameTextField.requestFocus();
		}

		//Returns the value to the method	
		return validationBoolean;

	}
	

	
	/***************************************************************************
	 *  - The calculatedSales Method -
	 * This method will calculate the sales totals based on the user input.
	 * There is also an Exception Handling created to prompt the user with
	 * an error message if the user inputs any non-numeric data into the 
	 * quantityField
	 ***************************************************************************/
	public void purchaseCoffee()
	{
		//Declare local variables to store the quantity of drinks ordered,
		//the index from the coffeeComboBox, the total drink sales, and decaf selection
		int salesQuantityInteger;
		int drinkIndexInteger;
		float drinkSalesFloat;
		boolean decafBoolean;
		
		//Declare String object to store selected drink
		String drinkString, trueDrinkString;
		
		//Try Block, An Exception Handling will Catch and display a 
		//message is the user inputs any non-numeric data in the 
		//quantityTextField. 
		try
		{
			//Get text from quantityTextField and store as integer in salesQuantityInteger
			salesQuantityInteger = Integer.parseInt(quantityTextField.getText());
			
			//Get selected index from coffeeComboBox and store in drinkIndex
			drinkIndexInteger = coffeeComboBox.getSelectedIndex();
			
			//Get selected item from the coffeeComboBox and store in drinkString
			drinkString = coffeeComboBox.getSelectedItem().toString();
			
			//Get boolean value for decafCheckBox and store in decafBoolean
			decafBoolean = decafCheckBox.isSelected();
			
			//Create coffeeCalculation object in CoffeeCalculation(salesQuantityInteger, drinkIndex) constructor
			CoffeeCalculation coffeeCalculation = new CoffeeCalculation(salesQuantityInteger, drinkIndexInteger,
					drinkString, decafBoolean);
			
			//Call value from getDrinkSale method and store in drinkSalesFloat
			drinkSalesFloat = coffeeCalculation.getDrinkSale();
			trueDrinkString = coffeeCalculation.getDrinkType();
			
			//Call outDisplay method
			outputDisplay(salesQuantityInteger, drinkSalesFloat, trueDrinkString);
		}
		catch(NumberFormatException err)
		{
			JOptionPane.showMessageDialog(null, "Please Enter a Number");
			quantityTextField.selectAll();
			quantityTextField.requestFocus();
		}
		
			
	}
	
	/****************************************************************************************************
	 * - The outputDisplay Method -  
	 * This method will display output in the JTextArea based on the data taken
	 * from the user. The quantityTextField will be cleared of any data and the cursor
	 * will be placed in the quantityTextField
	 ****************************************************************************************************/
	public void outputDisplay(int salesQuantityInteger, float drinkSalesFloat,
			String trueDrinkString)
	{
		//Create DecimalFormat object to display sale total
		DecimalFormat salesFormatDecimal = new DecimalFormat("$#,###.00");
				
		//Display output in the JTextArea based on the data taken from user input
		//If decafCheckBox is selected, output will display "Decaf" for drinks
		//Else, output will display data without "Decaf"
		resultsTextArea.append("\nCustomer:\t" + customerNameTextField.getText());
		resultsTextArea.append("\nQuantity:\t" + salesQuantityInteger);
		resultsTextArea.append("\nType of Drink:\t" + trueDrinkString);
		resultsTextArea.append("\nDrink Sales:\t" + salesFormatDecimal.format(drinkSalesFloat));
		resultsTextArea.append("\n_________________");

		
		//Clears output in the quantityTextField and places the cursor in the quantityTextField
		quantityTextField.setText("");
		quantityTextField.requestFocus();
		coffeeComboBox.setSelectedIndex(0);
		decafCheckBox.setSelected(false);
	}
	
	/**********************************************************************************************
	 * - The clear Method -
	 * This method will clear the customerNameTextField and quantityTextField and 
	 * place the cursor in the customerNameTextField. The coffeeComboBox will
	 * be set to index 0. The decafCheckBox will be set to false
	 ***********************************************************************************************/
	public void clear()
	{
		//Clear the customerNameField and quantityField and place the cursor
		//in the customerNameField
		customerNameTextField.setText("");
		customerNameTextField.requestFocus();
		quantityTextField.setText("");
		coffeeComboBox.setSelectedIndex(0);
		decafCheckBox.setSelected(false);
	}
	
	/**************************************************************************
	 *  - The totalSales Method - 
	 *  This method with call the clear method and clear the JTextArea of any
	 *  previous data. A message box will then appear and display the total
	 *  sales based on the user input. 
	 **************************************************************************/
	public void totalSales()
	{	
		//Create DecimalFormat object to display sales total
		DecimalFormat salesFormatDecimal = new DecimalFormat("$#,###.00");
		
		//Create local variables to store totals
		float totalSalesFloat;
		float subTotalFloat = 0.0f;
		float salesTaxFloat = 0.0f;
		
		//Create coffeeSalesSummary object from the CoffeeCalculation constructor
		CoffeeCalculation coffeeSalesSummary = new CoffeeCalculation();
		
		//Call the getTotalSales method and store value in totalSalesFloat
		totalSalesFloat = coffeeSalesSummary.getTotalSale();
		
		//Call the getSubTotal method and store value in subTotalFloat
		subTotalFloat = coffeeSalesSummary.getSubTotal();
		
		//Call the getSalesTax method and store in salesTaxFloat
		salesTaxFloat = coffeeSalesSummary.getSalesTax();
					
		//Call the clear method and clear the JTextArea
		clear();
		resultsTextArea.setText("");
		
		//Display a message box that display the total sales output based on user
		//input
		JOptionPane.showMessageDialog(null, "Subtotal:\t\t" + salesFormatDecimal.format(subTotalFloat) +
				"\nSales Tax:\t\t" + salesFormatDecimal.format(salesTaxFloat) + "\nTotal Order Cost:\t\t" 
				+ salesFormatDecimal.format(totalSalesFloat),"Coffee Sale"
				, JOptionPane.INFORMATION_MESSAGE);
		
		
	}

}
