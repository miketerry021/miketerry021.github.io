/*
 * Programmer Name		: Michael Terry
 * Date					: 10/16/2023	
 * Class Name			: Ch6IcreamShop
 * Project Description	: This application will begin by prompting the user to select
 * 						: two menu, Sales, and Administrator. The Sales menu will allow
 * 						: user to process ice cream sales. They will select different
 * 						: portions sizes, scoops, flavors, and quantity. Orders are added
 * 						: and a total order is generated. The administrator menu will allow
 * 						: the user to add or delete different ice cream flavors.
 */

import java.awt.event.*;
import javax.swing.*;
import java.text.*;

public class Ch6IcreamShop extends JFrame implements ActionListener, ItemListener
{
	
	// Objects to enter input and display
	
	//ice cream to populate the combo boxes
	/********************************************************************************
	 * STRING ARRAY to POPULATE THE COMBOBOXES
	 ********************************************************************************/
	
	String iceCreamString [] = {"","Vanilla","Rocky Road","Chocolate","Strawberry",
            "Coconut", "Mango"};
	
	/********************************************************************************
	 * OBJECTS to enter on a  ENTER PANEL
	 ********************************************************************************/
	
	JRadioButton guestRadioButton = new JRadioButton("Sales");
	JRadioButton adminRadioButton = new JRadioButton("Adminstrator");
	JRadioButton invisibleRadioButton = new JRadioButton("");
	ButtonGroup userButtonGroup = new ButtonGroup();
	
	/*******************************************************************************
	 * OBJECTS to enter on a  INPUT PANEL
	 *******************************************************************************/
	JRadioButton cupRadioButton = new JRadioButton("Cup");
	JRadioButton coneRadioButton = new JRadioButton("Cone");
	JRadioButton tubRadioButton = new JRadioButton("Tub");
	JRadioButton invisibleTypeRadioButton = new JRadioButton("");
	ButtonGroup typeButtonGroup = new ButtonGroup();
	
	JTextField quantityTextField = new JTextField(10);
	JRadioButton oneScoopRadioButton = new JRadioButton("One Scoop");
	JRadioButton twoScoopsRadioButton = new JRadioButton("Two Scoops");
	JRadioButton invisibleScoopRadioButton = new JRadioButton("");
	ButtonGroup scoopButtonGroup = new ButtonGroup();
	
	JComboBox iceCreamComboBox = new JComboBox(iceCreamString);
	JButton checkOutButton = new JButton("   Add to Shopping Cart    ");
	JButton summaryButton = new JButton("        Summary        ");
	JButton backGuestButton = new JButton("Back <");
	JTextArea invoiceTextArea = new JTextArea("TYPE" + '\t'+ "SCOOP"+ '\t'+"QUANTITY"
			+ '\t'+ "TOTAL"+'\n',10,20);
	
	/*******************************************************************************
	 * OBJECTS to enter on a  ADMIN PANEL
	 *******************************************************************************/
	
	JComboBox adminIceCreamComboBox = new JComboBox(iceCreamString);
	JTextField iceCreamTextField = new JTextField(10);
	
	JButton addButton = new JButton("Add an Item");
	JButton deleteButton = new JButton("Delete an Item");
	JButton totalButton = new JButton("Total Items");
	JButton backButton = new JButton("Back");
	
	
	
	/******************************************************************************
	 * PANEL OBJECTS AND THE  MAIN PANEL
	 *****************************************************************************/
	JPanel enterPanel = new JPanel();
	JPanel inputPanel = new JPanel();
	JPanel adminPanel = new JPanel();
	JPanel mainPanel = new JPanel();

	/******************************************************************************
	 * INSTANCE VARIABLES
	 * typeString - stores either cup, cone or tub whichever the user chooses
	 * scoopString - stores one scoop or two scoops whichever the user chooses
	 * priceFloat - is the price stored if the user chooses one scoop, two scoops or tub
	 *****************************************************************************/
	String typeString, scoopString;
	float priceFloat, totalCostFloat;

	/******************************************************************************
	 * main METHOD
	 * INSTANTIATES THE CLASS 
	 * ALLOWS THE WINDOW TO BE CLOSED WITH X BUTTON
	 *****************************************************************************/
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Ch6IcreamShop myIcecream = new Ch6IcreamShop();
		myIcecream.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/******************************************************************************
	 * ChIceCreamShop METHOD - CONSTRUCTOR
	 * CALLS THE DESIGNENTERPANEL METHOD TO ADD COMPONENTS ON THE ENTER PANEL 
	 * CALLS THE DESIGNINPUTPANEL METHOD TO ADD COMPONENTS ON THE INPUT PANEL
	 * CALLS THE DESIGNADMINPANEL METHOD TO ADD COMPONENTS ON THE ADMIN PANEL
	 * CALLS THE ADDLISTENERS METHOD TO ADD LISTENERS TO THE COMPONENTS
	 * ADD THE ENTER PANEL TO THE FRAME
	 * SET THE FRAME VISIBILITY TO TRUE
	 *****************************************************************************/
	
	
	public Ch6IcreamShop()
	{
		//constructor 
		//call the methods to add components to the other panels
		//to add the enterPanel
		//to the frame
		designEnterPanel();
		designInputPanel();
		designAdminPanel();
		addListeners();
		
		add(enterPanel);
		setSize(200,100);
		setVisible(true);
		
	}
	
	/******************************************************************************
	 * designEnterPanel METHOD -
	 * ADDS THE GUESTRADIOBUTTON, ADMINRADIOBUTTON AND INVISIBLERADIOBUTTON TO USERBUTTONGROUP FOR
	 * THEM TO BE MUTUALLY EXCLUSIVE
	 * ADDS THE RADIO BUTTONS TO THE ENTERPANEL
	 *****************************************************************************/
	
	
	public void designEnterPanel()
	{
		//add components to the enterPanel
		//the radio buttons to the button group
		userButtonGroup.add(guestRadioButton);
		userButtonGroup.add(adminRadioButton);
		userButtonGroup.add(invisibleRadioButton);
		
		enterPanel.add(guestRadioButton);
		enterPanel.add(adminRadioButton);
		
	}
	
	/******************************************************************************
	 * designAdminPanel METHOD -
	 * ADDS THE LABEL FOR ADMINS ONLY TO THE ADMINPANEL
	 * ADDS ADMINICECREAM COMBOBOX TO THE ADMINPANEL
	 * ADDS THE LABEL AND TEXTFIELD FOR THE USER TO ENTER A NEW ICECREAM TO THE ADMINPANEL
	 * ADDS ADD BUTTON TO THE ADMINPANEL TO ALLOW THE ADMIN TO ADD A NEW FLAVOR
	 * ADDS DELETE BUTTON TO THE ADMINPANEL TO ALLOW THE ADMIN TO DELETE A FLAVOR
	 * ADDS TOTAL BUTTON TO THE ADMINPANEL TO ALLOW THE ADMIN TO DISPLAY TOTAL # OF FLAVORS
	 * ADDS BACK BUTTON TO THE ADMINPANEL TO ALLOW THE ADMIN TO RETURN TO THE ENTER PANEL
	 *****************************************************************************/
	public void designAdminPanel()
	{
		//add components to the adminPanel
		adminPanel.add(new JLabel("            ADMINS ONLY          "));
		adminPanel.add(adminIceCreamComboBox);
		adminPanel.add(new JLabel("New Ice Cream"));
		adminPanel.add(iceCreamTextField);
		adminPanel.add(addButton);
		adminPanel.add(deleteButton);
		adminPanel.add(totalButton);
		adminPanel.add(backButton);
	}
	
	/******************************************************************************
	 * designInputPanel METHOD -
	 * ADDS THE CONERADIOBUTTON, CUPRADIOBUTTON, TUBRADIONBUTTON AND INVISIBLERADIOBUTTON 
	 * TO TYPEBUTTONGROUP FOR THEM TO BE MUTUALLY EXCLUSIVE
	 * ADDS THE ONESCOOPRADIOBUTTON, TWOSCOOPSRADIOBUTTON AND INVISIBLESCOOPRADIOBUTTON 
	 * TO SCOOPBUTTONGROUP FOR THEM TO BE MUTUALLY EXCLUSIVE
	 * ADDS THE LABEL ICECREAM STOP TO THE INPUTPANEL
	 * ADD THE CONERADIOBUTTON, CUPRADIOBUTTON, AND TUBRADIONBUTTON TO THE INPUTPANEL
	 * ADD THE ONESCOOPRADIOBUTTON, AND TWOSCOOPSRADIOBUTTON TO THE INPUTPANEL
	 * ADDS ICECREAM COMBOBOX TO THE INPUTPANEL
	 * ADDS THE LABEL AND TEXTFIELD TO THE INPUTPANEL FOR THE USER TO ENTER A QUANTITY OF ICECREAM 
	 * ADDS CHECKOUT BUTTON TO THE INPUTPANEL TO ALLOW THE USER TO CHECKOUT AN ITEM BOUGHT
	 * ADDS INVOICETEXTAREA TO THE INPUTPANEL TO ALLOW THE USER TO SEE THE CHECKOUT ITEM AND ITS COST
	 * ADDS SUMMARY BUTTON TO THE INPUTPANEL TO ALLOW THE USER TO DISPLAY TOTAL OF ALL THE CHECKOUT ITEMS
	 * ADDS BACKGUEST BUTTON TO THE INPUTPANEL TO ALLOW THE ADMIN TO RETURN TO THE ENTER PANEL
	 *****************************************************************************/
	public void designInputPanel()
	{
		//add the container group to the radio buttons
		typeButtonGroup.add(coneRadioButton);
		typeButtonGroup.add(cupRadioButton);
		typeButtonGroup.add(tubRadioButton);
		typeButtonGroup.add(invisibleTypeRadioButton);
		
		//add the scoop group to the radio buttons
		scoopButtonGroup.add(oneScoopRadioButton);
		scoopButtonGroup.add(twoScoopsRadioButton);
		scoopButtonGroup.add(invisibleScoopRadioButton);
		
		//disable the scoop radio buttons
		oneScoopRadioButton.setEnabled(false);
		twoScoopsRadioButton.setEnabled(false);
		inputPanel.add(new JLabel("                         "));
		inputPanel.add(new JLabel("               ICE CREAM STOP           "));
		inputPanel.add(new JLabel("                         "));
		inputPanel.add(coneRadioButton);
		inputPanel.add(cupRadioButton);
		inputPanel.add(tubRadioButton);
		inputPanel.add(oneScoopRadioButton);
		inputPanel.add(twoScoopsRadioButton);
		inputPanel.add(new JLabel("                                     "));
		inputPanel.add(iceCreamComboBox);
		inputPanel.add(new JLabel("                                                "));
		inputPanel.add(new JLabel(" Quantity     "));
		inputPanel.add(quantityTextField);
		inputPanel.add(new JLabel("                                "));
		inputPanel.add(checkOutButton);
		inputPanel.add(invoiceTextArea);
		inputPanel.add(summaryButton);
		inputPanel.add(backGuestButton);
		
		
		
	}
	/******************************************************************************
	 * addListeners METHOD -
	 * ADDS THE ACTIONLISTENER TO THE ADD, DELETE, TOTAL, BACK, BACKGUEST, CHECKOUT, SUMMARY BUTTONS
	 * ADDS THE ACTIONLISTENER TO THE ICECREAM, ADMINICECREAM COMBOBOXES AND QUANTITY TEXTFIELD
	 * ADDS THE ITEMLISTENER TO THE CONE, CUP, TUB, ONE SCOOP, TWO SCOOPS RADIO BUTTONS
	 * ADDS THE ITEMLISTENER TO THE GUEST AND ADMIN RADIO BUTTONS
	 *****************************************************************************/
	public void addListeners()
	{
		//add the listeners to the respective components 
		addButton.addActionListener(this);
		deleteButton.addActionListener(this);
		totalButton.addActionListener(this);
		backButton.addActionListener(this);
		backGuestButton.addActionListener(this);
		checkOutButton.addActionListener(this);
		summaryButton.addActionListener(this);
		
		
		guestRadioButton.addItemListener(this);
		adminRadioButton.addItemListener(this);
		
		coneRadioButton.addItemListener(this);
		cupRadioButton.addItemListener(this);
		tubRadioButton.addItemListener(this);
		
		oneScoopRadioButton.addItemListener(this);
		twoScoopsRadioButton.addItemListener(this);
		
		adminIceCreamComboBox.addActionListener(this);
		iceCreamComboBox.addActionListener(this);
		quantityTextField.addActionListener(this);
		
	}
	/******************************************************************************
	 * itemStateChanged METHOD -
	 * WHEN THE ITEMLISTENER TO THE GUEST AND ADMIN RADIO BUTTONS FIRES AN EVENT
	 * ***GUEST RADIO BUTTON - DISPLAYS THE INPUTPANEL
	 * ***ADMIN RADIO BUTTON - DISPLAY THE ADMINPANEL
	 * WHEN CONE, CUP, TUB, ONE SCOOP, TWO SCOOPS RADIO BUTTONS FIRES AN EVENT
	 * ***CONE RADIO BUTTON - ONE SCOOP,AND TWO SCOOPS RADIO BUTTONS ARE 
	 *                        ENABLED AND TYPESTRING IS SET TO CONE
	 * ***CUP RADIO BUTTON - ONE SCOOP,AND TWO SCOOPS RADIO BUTTONS ARE 
	 *                        ENABLED AND TYPESTRING IS SET TO CUP
	 * ***TUB RADIO BUTTON - TYPESTRING IS SET TO TUB AND PRICEFLOAT IS SET TO TUB PRICE PER UNIT
	 * ***ONE SCOOP RADIO BUTTON - TYPESTRING IS SET TO ONE SCOOP AND 
	 *                             PRICEFLOAT IS SET TO ONE SCOOP PRICE PER UNIT   
	 * ***TWO SCOOPS RADIO BUTTON - TYPESTRING IS SET TO TWO SCOOPS AND 
	 *                             PRICEFLOAT IS SET TO TWO SCOOPS PRICE PER UNIT                    
	 *****************************************************************************/
	
	public void itemStateChanged(ItemEvent evt)
	{
		//the radio buttons fire the event
		Object sourceObject = evt.getSource();
		
		//constants for the different rates for icecream
		final float ONE_SCOOP_RATE_FLOAT = 0.99F;
		final float TWO_SCOOPS_RATE_FLOAT = 1.50F;
		final float TUB_RATE_FLOAT = 4.99F;
		
		//to display the input panel
		if(sourceObject == guestRadioButton)
		{
			if(guestRadioButton.isSelected())
			{
				remove(enterPanel);
				invisibleRadioButton.setSelected(true);
				remove(adminPanel);
				add(inputPanel);
				setSize(400,400);
				setVisible(true);
			}
		} //to display the admin panel
		else if(sourceObject == adminRadioButton )
		{
			if(adminRadioButton.isSelected())
			{
			
				remove(enterPanel);
				invisibleRadioButton.setSelected(true);
				remove(inputPanel);
				add(adminPanel);
				setSize(200,300);
				setVisible(true);
			}
		}
		
		if(coneRadioButton.isSelected())
		{
			//enable the scoop radio buttons
			oneScoopRadioButton.setEnabled(true);
			twoScoopsRadioButton.setEnabled(true);
			typeString = "Cone";
			
		}
		else if (cupRadioButton.isSelected())
		{
			//enable the scoop radio buttons
			oneScoopRadioButton.setEnabled(true);
			twoScoopsRadioButton.setEnabled(true);
			typeString = "Cup";
			
		}
		else if (tubRadioButton.isSelected())
		{
			//set the rate for the tub
			typeString = "Tub";
			priceFloat = TUB_RATE_FLOAT;
			
		}
		
		if(oneScoopRadioButton.isSelected())
		{
			//set the rate for the one scoop
			scoopString = "One Scoop";
			priceFloat = ONE_SCOOP_RATE_FLOAT;
		}
		else if (twoScoopsRadioButton.isSelected())
		{
			//set the rate for the two scoops
			scoopString = "Two Scoops";
			priceFloat = TWO_SCOOPS_RATE_FLOAT;
			
		}
		else
		{
			scoopString = "NONE";
		}
		
		
	}
	/******************************************************************************
	 * actionPerformed METHOD -
	 * WHEN THE ACTIONLISTENER TO THE ADD, DELETE, TOTAL, AND BACK BUTTONS FIRES AN EVENT 
	 *                    FROM THE ADMINPANEL
	 * ***ADD BUTTON - CALLS THE ADDITEM METHOD 
	 * ***DELETE BUTTON - CALLS THE DELETEITEM METHOD
	 * ***TOTAL BUTTON - CALLS THE TOTAL METHOD
	 * ***BACK BUTTON - REMOVES ADMINPANEL,ADDS ENTERPANEL, AND RESIZES THE WINDOW, SETS VISIBILITY
	 * WHEN THE ACTIONLISTENER TO THE CHECKOUT, SUMMARY, AND BACKGUEST BUTTONS FIRES AN EVENT 
	 *                    FROM THE INPUTPANEL
	 * ***CHECKOUT BUTTON - CALLS THE CHECKOUTITEM METHOD 
	 * ***SUMMARY BUTTON - CALLS THE SUMMARY METHOD 
	 * ***BACKGUEST BUTTON - REMOVES INPUTPANEL,ADDS ENTERPANEL, AND RESIZES THE WINDOW, SETS VISIBILITY                 
	 *****************************************************************************/
	
	public void actionPerformed(ActionEvent evt)
	{
		//buttons fire this event
		Object sourceObject = evt.getSource();
		
		if(sourceObject == addButton)
		{
			//calls to add item to combobox
			addItem();
		}
		else if(sourceObject == deleteButton)
		{
			//calls to delete item to combobox
			deleteItem();
		}
		else if(sourceObject == totalButton)
		{
			//calls to find the # of items in combobox
			total();
		}
		else if(sourceObject == backButton)
		{
			//return to enterPanel from adminPanel
			remove(adminPanel);
			add(enterPanel);
			setSize(200,100);
			setVisible(true);
			
		}
		else if(sourceObject == checkOutButton )
		{
			//validation
			//calculation
			//display
			checkOutItems();
		}
		else if (sourceObject == summaryButton )
		{
			//display total of the items bought
			summary();
		}
		else if(sourceObject == backGuestButton)
		{
			//return to the enterPanel from inputPanle
			remove(inputPanel);
			add(enterPanel);
			setSize(200,100);
			setVisible(true);
		}
	
	}
	/******************************************************************************
	 * addItem METHOD -
	 * CHECK IF THE ICECREAM TEXTFIELD IS NOT BLANK 
	 * CALL CHECKINPUT METHOD IF THERE IS A DUPLICATE OR NOT PASSING THE USER INPUT BY RECEIVING A BOOLEAN
	 * CALL DISPLAY FOUND METHOD PASSING THE BOOLEAN TO DISPLAY RESULTS
	 * IF IT IS NOT BLANK CHECK IF WHAT THE USER TYPED IS ALREADY IN THE COMBOBOX
	 * IF IT IS A DUPLICATE THEN MESSAGE TO USER, SELECT THE TEXTFIELD AND FOCUS ON THE TEXTFIELD 
	 * IF IT IS NOT DUPLICATE THE ADD THE ITEM TO THE ICREAM AND ADMINICECREAM COMBOBOXES, MESSAGE TO USER                 
	 *****************************************************************************/
	
	public void addItem()
	{
		String inputString; 
		boolean foundIcecreamBoolean;
		
		inputString = iceCreamTextField.getText();
		//Check if the text field is not blank
		if((!(inputString.equals(""))))
		{
			//checking for duplicate
			foundIcecreamBoolean = checkInput(inputString);
			displayFound(foundIcecreamBoolean, inputString);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Enter a New Ice Cream Flavor");
			iceCreamTextField.requestFocus();
		}
		
		
	}
	/******************************************************************************
	 * deleteItem METHOD -
	 * CHECK IF THE USER HAS SELECTED AN ICECREAM FROM THE COMBOBOX
	 * IF NOT - A MESSAGE TO CHOOSE AND ICECREAM FLAVOR
	 * IF YES - A MESSAGE TO CONFIRM TO DELETE THE ICECREAM FLAVOR
	 * IF THE CONFIRMATION IS YES THEN DELETE THE ITEM FROM THE ICECREAM AND ADMINICECREAM COMBOBOXES
	 *****************************************************************************/
	public void deleteItem()
	{
		int indexInteger, resultInteger;
		indexInteger = adminIceCreamComboBox.getSelectedIndex();
		String deleteString, outputString;
		
		if(indexInteger != -1 && indexInteger != 0)
		{
			deleteString = (String) iceCreamComboBox.getItemAt(indexInteger);
			outputString = "Do you wish to delete  " + deleteString;
			resultInteger = JOptionPane.showConfirmDialog(null,outputString,
			"Confirmation",JOptionPane.YES_NO_OPTION);
			if(resultInteger == JOptionPane.YES_OPTION)
			{
				iceCreamComboBox.removeItemAt(indexInteger);
				adminIceCreamComboBox.removeItemAt(indexInteger);
				iceCreamComboBox.setSelectedIndex(0);
				adminIceCreamComboBox.setSelectedIndex(0);
				
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Select Flavor to Remove");
			iceCreamComboBox.requestFocus();
		}
	}
	
	/******************************************************************************
	 * total METHOD - 
	 * THE TOTAL NUMBER OF FLAVORS IN THE COMBOBOX IN A JOPTIONPANE
	 *****************************************************************************/
	public void total()
	{
		JOptionPane.showMessageDialog(null,"Total Ice Cream Flavors : "
	          + iceCreamComboBox.getItemCount());
	}
	
	
	/******************************************************************************
	 * checkInput METHOD - returns a boolean (found a duplicate or not)
	 * CHECK IF WHAT THE USER TYPED IS A DUPLICATE OR NOT 
	 * IF DUPLICATE THE BOOLEAN FLAG IS TRUE ELSE IS FALSE
	 * THEN MESSAGE TO USER, SELECT THE TEXTFIELD AND FOCUS ON THE TEXTFIELD 
	 * IF IT IS NOT DUPLICATE THE ADD THE ITEM TO THE ICECREAM AND ADMINICECREAM COMBOBOXES, MESSAGE TO USER                 
	 *****************************************************************************/
	public boolean checkInput(String userInputString)
	{
		boolean foundBoolean = false;
		int indexInteger = 0;
		String iceCreamString = new String();
	
		while(!foundBoolean && indexInteger < iceCreamComboBox.getItemCount())
		{
			iceCreamString = (String) iceCreamComboBox.getItemAt(indexInteger);
			if(userInputString.equalsIgnoreCase(iceCreamString))
			{
				foundBoolean = true;
			}
			else
			{
				indexInteger++;
			}
		}
		return foundBoolean;
	}
	
	/******************************************************************************
	 * displayFound METHOD - 
	 * CHECK THE BOOLEAN FROM THE CHECKINPUT MEHOD IF IT IS A DUPLICATE OR NOT 
	 * IF DUPLICATE THE BOOLEAN FLAG IS TRUE 
	 * THEN MESSAGE TO USER, SELECT THE TEXTFIELD AND FOCUS ON THE TEXTFIELD 
	 * IF IT IS NOT DUPLICATE THE BOOLEAN FLAG IS FALSE
	 * ADD THE ITEM TO THE ICREAM AND ADMINICECREAM COMBOBOXES, MESSAGE TO USER THAT ITEM IS ADDED                
	 *****************************************************************************/
	public void displayFound(boolean icecreamBoolean, String inputString)
	{
		if(icecreamBoolean)
		{
			JOptionPane.showMessageDialog(null,"This Flavor is Already Listed");
			iceCreamTextField.selectAll();
			iceCreamTextField.requestFocus();
		}
		else
		{
			iceCreamComboBox.addItem(inputString);
			adminIceCreamComboBox.addItem(inputString);
			JOptionPane.showMessageDialog(null,"New Ice Cream Flavor has been Added");
			iceCreamTextField.setText("");
		}
	}
	
	/******************************************************************************
	 * checkOutItems METHOD - 
	 * CALL THE VALIDATION METHOD THAT WILL RETURN A BOOLEAN IF ALL FIELDS ARE VALIDATED OR NOT 
	 * CHECK THE BOOLEAN IF TRUE SEND TO CALCULATE METHOD                
	 *****************************************************************************/
	public void checkOutItems()
	{
		boolean validationBoolean;
		//check to validate all fields
		validationBoolean = validation();
		//send for calculation if all fields are validated
		if(validationBoolean)
		{
			calculate();
		}
	}
	
	/******************************************************************************
	 * validation METHOD - 
	 * CHECK IF THE CONE, CUP OR TUB IS SELECTED 
	 * IF ONE OF THEM IS SELECTED CONTINUE TO CHECK ELSE MESSAGE TO SELECT ONE OF THEM.
	 * CONTINUE TO CHECK IF THE USER SELECTED AN ICECREAM FROM ICECREAM COMBOBOX 
	 * IF SELECTED CONTINUE CHECKING ELSE MESSAGE TO SELCT AN ICECREAM
	 * CONTINUE TO CHECK IF THE QUANTITY TEXTFIELD IS NOT EMPTY
	 * IF NOT EMPTY CONTINUE TO CHECK ELSE MESSAGE FOR USER TO ENTER QUANTITY
	 * CONTINUE TO CHECK IF THE USER CHOSE CONE OR CUP ELSE VALIDATION BOOLEAN IS TRUE
	 * IF THE USER DID CHOOSE CONE OR CUP THEN CHECK IF ONE SCOOP OR TWO SCOOPS RADIO BUTTONS 
	 * 						IS SELECTED THEN VALIDATION BOOLEAN IS TRUE ELSE 
	 * 					    MESSAGE TO USER TO SELECT ONE SCOOP OR TWO SCOOPS RADIO BUTTONS
	 *  RETURN VALIDATIONBOOLEAN                          
	 *****************************************************************************/
	public boolean validation()
	{
		//validate the components
		boolean validateComponentsBoolean = false;
		int indexInteger;
		indexInteger = iceCreamComboBox.getSelectedIndex();
		
		
				
		if(cupRadioButton.isSelected()|| coneRadioButton.isSelected()||tubRadioButton.isSelected())
		{
			
				if(indexInteger != -1 && indexInteger != 0)
				{
					if(!(quantityTextField.getText().equals("")))
					{
						if((cupRadioButton.isSelected()|| coneRadioButton.isSelected()))
						{
							if(oneScoopRadioButton.isSelected()||twoScoopsRadioButton.isSelected())
							{
								validateComponentsBoolean = true;
							}
							else
							{
							JOptionPane.showMessageDialog(null, "Please Select Scoop");
							oneScoopRadioButton.requestFocus();
							}
						}
						else
						{
							validateComponentsBoolean = true;
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
					JOptionPane.showMessageDialog(null, "Please select an Ice Cream Flavor");
					iceCreamComboBox.requestFocus();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please select Cup, Cone or Tub");
				cupRadioButton.requestFocus();
			}
		
		return validateComponentsBoolean;
	}
	
	/******************************************************************************
	 * calculate METHOD - 
	 * CREATE SUBFLOAT VARIABLE TO STORE THE ITEM TOTAL
	 * CREATE QUANTITYINTEGER VARIABLE TO RETRIEVE QUANTITY FROM  USER
	 * CREATE OUTPUTSTRING TO DISPLAY IN INVOICETEXTAREA
	 * CREATE OBJECT TO FORMAT FOR CURRENCY TWO DECIMAL PLACES
	 * RETRIEVE THE QUANTITY FROM THE USER
	 * CALCULATE THE ITEM TOTAL BY MULTIPLYING THE PRICEFLOAT AND QUANTITYINTEGER AND STORE IN SUBTOTAL
	 * ACCUMULATE THE ITEM TOTALS IN TOTALCOSTFLOAT
	 * CREATE A LINE OF THE ITEM BOUGHT WITH THE ITEM TOTAL AND 
	 *                     STORE IN OUTPUT STRING (FORMAT THE SUBTOTAL)
	 * APPEND THE OUTPUTSTRING TO THE INVOICETEXTAREA
	 * CALL THE CLEARCOMPONENTS METHOD                         
	 *****************************************************************************/
	
	public void calculate()
	{
		// create variables
		float subTotalFloat;
		int quantityInteger;
		String outputString;
		
		//object to format currency
		DecimalFormat formatCurrency = new DecimalFormat("$#0.00");
		
		//quantity from user
		quantityInteger = Integer.parseInt(quantityTextField.getText());
		
		
		//total item cost and total of all items
		subTotalFloat = priceFloat * quantityInteger;
		totalCostFloat += subTotalFloat;
		
		//string to display in text area
		outputString = typeString +  '\t' + scoopString + '\t' + quantityInteger + '\t' + 
		formatCurrency.format(subTotalFloat)+'\n';
		
		//display in text area
		invoiceTextArea.append(outputString);
		clearComponents();
		
	}
	/******************************************************************************
	 * summary METHOD - 
	 * CREATE OBJECT TO FORMAT FOR CURRENCY TWO DECIMAL PLACES
	 * CREATE PURCHASESTRING TO DISPLAY TOTAL IN INVOICETEXTAREA
	 * CALL THE CLEARCOMPONENTS METHOD 
	 * CALL THE CLEARFIELDS METHOD                        
	 *****************************************************************************/
	public void summary()
	{
		//object to format currency
		DecimalFormat formatCurrency = new DecimalFormat("$#0.00");
		
		//total string to display in text area
		String purchaseString = "TOTAL\t\t\t "+ formatCurrency.format(totalCostFloat)+'\n';
		
		invoiceTextArea.append(purchaseString);
		//clear the components and the set the accumulated variables to zero
		clearComponents();
		clearFields();
	}
	
	/******************************************************************************
	 * clearComponents METHOD - 
	 * SET THE INVISIBLETYPE RADIO BUTTON TO TRUE TO DESELECT THE CUP,CONE OR TUB RADIO BUTTONS
	 * SET THE INVISIBLESCOOP RADIO BUTTON TO TRUE TO DESELECT THE ONE SCOOP OR TWO SCOOPS RADIO BUTTONS
	 * CLEAR THE QUANTITY TEXTFIELD 
	 * DESELECT THE ICECREAM COMBOBOX                       
	 *****************************************************************************/
	public void clearComponents()
	{
		//set components back to default
		invisibleTypeRadioButton.setSelected(true);
		invisibleScoopRadioButton.setSelected(true);
		quantityTextField.setText("");
		iceCreamComboBox.setSelectedIndex(0);
		
		oneScoopRadioButton.setEnabled(false);
		twoScoopsRadioButton.setEnabled(false);
		coneRadioButton.requestFocus();
		
	}
	/******************************************************************************
	 * clearFields METHOD - 
	 * SET THE TYPESTRING TO EMPTY
	 * SET THE SCOOPSTRING TO EMPTY
	 * SET THE PRICEFLOAT TO ZERO 
	 * SET THE TOTALCOSTFLOAT TO ZERO                     
	 *****************************************************************************/
	public void clearFields()
	{
		typeString = "";
		scoopString = "";
		priceFloat = 0.0f;
		totalCostFloat = 0.0f;
		
	}
}
