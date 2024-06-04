/* Programmer Name		: Michael Terry
 * Date					: 12/04/23
 * Class Name			: BankingApplication
 * Project Description	: This program is a banking application. It will create 10 user accounts. The accounts will be either
 * 						: checking or savings account. The user will be able to deposit and withdraw funds from any account.
 * 						: Before new accounts are created, the program will check if the account already exists. Funds can only
 * 						: be added or withdrawn from existing accounts. An overdrafts of account funds will not be processed
  */

import java.awt.event.*;   	//for ActionListener
import javax.swing.*;		//for swing components
import java.awt.*;			//for Font
import java.text.*;			//for DecimalFormat class

public class BankingApplication extends JFrame 
	implements ActionListener
{
	//added components for first panel	
	//Array for the combo box
	String choiceString [] = {"","Create Account","Withdraw or Deposit","Show all Accounts"};	
	JLabel companyLabel = new JLabel ("  Friendly Neighborhood Bank   ");
	JLabel selectLabel = new JLabel ("Please Select an Action: ");
	JComboBox selectComboBox = new JComboBox(choiceString);
	JButton goButton = new JButton("Go");
	JLabel programmerNameLabel = new JLabel ("programmed by Michael Terry");
		
	//added components for second panel	
	JLabel companyLabel2 = new JLabel ("     Friendly Neighborhood Bank      ");
	JLabel firstNameLabel = new JLabel ("        First name          ");
	JTextField firstNameTextField = new JTextField(10);
	JLabel lastNameLabel = new JLabel ("                  Last name                      ");
	JTextField lastNameTextField = new JTextField(10);
	JLabel newPinLabel = new JLabel ("                         PIN                          ");
	JTextField newPinTextField = new JTextField(10);
	String typeAccountString [] = {"","Checking", "Savings"};
	JComboBox typeAccountComboBox = new JComboBox(typeAccountString);
	JButton processNewAccountButton = new JButton("Process");
	JButton backFromNewAccountButton = new JButton("Back");
	JTextArea accountOutputTextArea = new JTextArea(10,15);
	JScrollPane accountScrollPane = new JScrollPane(accountOutputTextArea);
		
	//added components for third panel	
	JLabel companyLabel3 = new JLabel ("               Friendly Neighborhood Bank            ");
	JRadioButton withdrawRadioButton = new JRadioButton("  Withdraw                                       ");
	JRadioButton depositRadioButton = new JRadioButton("   Deposit                                        ");
	JRadioButton invisibleRadioButton = new JRadioButton("");
	ButtonGroup transactionButtonGroup = new ButtonGroup();
	JLabel confirmPinLabel = new JLabel("                                                   PIN: ");
	JTextField confirmPinTextField = new JTextField(30);
	JLabel amountLabel = new JLabel("                                  Enter amount: ");
	JTextField amountTextField = new JTextField(30);
	JButton processTransactionButton = new JButton("Process");
	JButton backFromTransactionButton = new JButton("Back");
	JTextArea transactionOutputTextArea = new JTextArea(10,15);
	JScrollPane transactionScrollPane = new JScrollPane(transactionOutputTextArea);
		
	//Added JTextArea for displaying all accounts
	JTextArea displayAllTextArea = new JTextArea(10, 42);
			
	JPanel mainPanel = new JPanel();
	JPanel accountPanel = new JPanel();
	JPanel transactionPanel = new JPanel();
	Font taFont = new Font("Courier", Font.PLAIN, 12);
	Font companyFont = new Font ("Sans Serif", Font.BOLD, 20);
	Font programmerNameFont = new Font ("Sans Serif", Font.ITALIC, 10);
	
	// instance objects and variables
	Account [] myAccount = new Account[10];
	
	int lastAccountInteger = -1;
	final int MAX_ACCOUNTS_INTEGER = 9;
		
	String typeString;
		
		

	// the main method will create an object of itself and 
	//set the default close operation
	public static void main(String[] args) 
	{
		BankingApplication myApplication = new BankingApplication();
		myApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	//constructor 
	//call the methods to add components to the multiple panels
	//also sets size and visibility	
	public BankingApplication()
	{		
		designMainPanel();
		designAccountPanel();
		designTransactionPanel();
		addListeners();		
		add(mainPanel);
		setSize(400,200);
		setVisible(true);		
		}	
			
	//this method will create the main panel
	public void designMainPanel()
	{
		//add components to the mainPanel
		companyLabel.setFont(companyFont);
		mainPanel.add(companyLabel);
		mainPanel.add(selectLabel);
		mainPanel.add(selectComboBox);
		mainPanel.add(goButton);
		programmerNameLabel.setFont(programmerNameFont);
		mainPanel.add(programmerNameLabel);		
	}
		
		
	//this method creates the account panel
	public void designAccountPanel()
	{
		//add components to the adminPanel
		accountPanel.setLayout(new FlowLayout(FlowLayout.CENTER,60,15));
		companyLabel2.setFont(companyFont);
		accountPanel.add(companyLabel2);
		accountPanel.add(firstNameLabel);
		accountPanel.add(firstNameTextField);
		accountPanel.add(lastNameLabel);
		accountPanel.add(lastNameTextField);
		accountPanel.add(newPinLabel);
		accountPanel.add(newPinTextField);
		accountPanel.add(new JLabel("     Type of Account"));
		accountPanel.add(typeAccountComboBox);
		accountPanel.add(processNewAccountButton);
		accountPanel.add(backFromNewAccountButton);
		accountPanel.add(accountOutputTextArea);
			
		String formattedTitleString = String.format("%-20s%-6s%-10s%-10s%n",
				"Name","PIN","Account","Balance");
		accountOutputTextArea.setFont(taFont);
		accountOutputTextArea.setText(formattedTitleString);
	}
		
		
	//this method creates the transaction panel
	public void designTransactionPanel()
	{
		//add the container group to the radio buttons
		transactionButtonGroup.add(depositRadioButton);
		transactionButtonGroup.add(withdrawRadioButton);
		transactionButtonGroup.add(invisibleRadioButton);
		companyLabel3.setFont(companyFont);
		transactionPanel.add(companyLabel3);
		transactionPanel.add(depositRadioButton);
		transactionPanel.add(withdrawRadioButton);
		transactionPanel.add(confirmPinLabel);
		transactionPanel.add(confirmPinTextField);
		transactionPanel.add(amountLabel);
		transactionPanel.add(amountTextField);
		transactionPanel.add(processTransactionButton);
		transactionPanel.add(backFromTransactionButton);
		transactionPanel.add(transactionOutputTextArea);
		
		String formattedTitleString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n",
				"PIN","Name","Account Type", "Trans Type", "Trans Amt","Charge","Balance" );
		transactionOutputTextArea.setFont(taFont);
		transactionOutputTextArea.setText(formattedTitleString);
			
	}
	
	//this method puts action listeners on all the desired buttons
	public void addListeners()
	{
		//add the listeners to the respective components 
		selectComboBox.addActionListener(this);
		goButton.addActionListener(this);
		processNewAccountButton.addActionListener(this);
		backFromNewAccountButton.addActionListener(this);
		processTransactionButton.addActionListener(this);
		backFromTransactionButton.addActionListener(this);
			
	}
	
	//this method assigns the actions and methods to the correct button
	public void actionPerformed(ActionEvent evt)
	{
		//buttons fire this event
		Object sourceObject = evt.getSource();
		
		if (sourceObject == goButton)
		{
			if(selectComboBox.getSelectedIndex() == 1)  //Add an account option
			{
				remove(mainPanel);
				invisibleRadioButton.setSelected(true);
				remove(transactionPanel);
				add(accountPanel);
				setSize(350,600);
				setVisible(true);
				firstNameTextField.requestFocus();
			}
			else if (selectComboBox.getSelectedIndex() == 2) //Transaction option
			{
				remove(mainPanel);
				invisibleRadioButton.setSelected(true);
				remove(accountPanel);
				add(transactionPanel);
				setSize(600,400);
				setVisible(true);
			}
			else if(selectComboBox.getSelectedIndex() == 3)  //Display all transactions option
			{				
				displayAllAccountInfo();
			}
			else
				JOptionPane.showMessageDialog(null, "Please make a selection in the combo box before pressing Go.");
		}
			
		if(sourceObject == processNewAccountButton)
		{
			if(validationNewAccountFields())
				processNewAccount();
		}
		else if(sourceObject == processTransactionButton)
		{
			if(validationTransRadioButtons())
				processTransaction();
			else 
				JOptionPane.showMessageDialog(null, "Please select either withdraw or deposit");			
		}
			
		else if(sourceObject == backFromNewAccountButton)
		{
			remove(accountPanel);
			invisibleRadioButton.setSelected(true);
			remove(transactionPanel);
			selectComboBox.setSelectedIndex(-1);
			add(mainPanel);
			setSize(400,300);
			setVisible(true);
		}
		else if(sourceObject == backFromTransactionButton)
		{
			remove(transactionPanel);
			remove(accountPanel);
			selectComboBox.setSelectedIndex(-1);
			add(mainPanel);
			setSize(400,200);
			setVisible(true);
		}		
			
	}//end of actionPerformed method
	
	//validates if something has been entered into the three text fields 
	public boolean validationNewAccountFields()
	{
	    boolean validationBoolean = false;
		    
	    if(!(firstNameTextField.getText()).equals(""))
	    {
	    	if(!(lastNameTextField.getText()).equals(""))
	    	{
	    		if(!(newPinTextField.getText()).equals(""))
	    		{      		   
	    			validationBoolean = true; 
	    		}
	    		   
	    		else
	    		{
	    			JOptionPane.showMessageDialog(null, "Please enter a PIN");
	    			newPinTextField.requestFocus();
	    			validationBoolean = false;
	    		}
	    	}
		    
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "Please enter Last Name");
	    		lastNameTextField.requestFocus();
	    		validationBoolean = false;}
			}
	    else
	    {
	    	JOptionPane.showMessageDialog(null, "Please enter First name");
	    	firstNameTextField.requestFocus();
	    	validationBoolean = false;
	    }
		    
	    return validationBoolean;
		
	}
	
	//this method will validate if a radio button was selected
	public boolean validationTransRadioButtons()
	{
		boolean validationBoolean = false;
			
		if(depositRadioButton.isSelected() || withdrawRadioButton.isSelected())
			validationBoolean = true;
		else
			validationBoolean = false;
		return validationBoolean;
	}
		
	//this method will make a new account
	public void processNewAccount()
	{
		//Create an object balanceFormat from the DecimalFormat Class to
		// be used to format balance amounts
		DecimalFormat balanceFormat = new DecimalFormat(" $#,###.00");
			
		//Create local Strings and variables to be used to process new accounts
		String nameString, pinString, accountString;	//Will hold account name and pin input for verification of length 
		int pinInteger, verifyPinInteger, comboBoxSelectionInteger;	//Will hold pin input and verification of duplicate of pin
		float transAmtFloat = 0f;	//Will hold transaction amount
		final float NEW_CHECKING_FLOAT = 100f, NEW_SAVINGS_FLOAT = 500;	//Will hold funds for new checking and savings accounts
		boolean insufficientFundsBoolean = true;	//Will hold boolean value for insufficient funds
			
		//Get user input for account name and pin
		nameString = firstNameTextField.getText() + " " + lastNameTextField.getText();
		pinString = newPinTextField.getText();
			
		//Your create an account code should go here!
		try
		{
			lastAccountInteger++;	//Will raise the lastAccountInteger by 1 value
			if(lastAccountInteger < myAccount.length)	//If lastAccountInteger is less than myAccount array length
			{	
				if(pinString.length() == 4)	//Verify pin is at least 4 digits
				{	
					verifyPinInteger = pinVerify();	//Retrieve pin verification from pinVerify method to verify pin is not duplicate							
					if(verifyPinInteger == -1)	//If entered pin is long enough and not a duplicate
					{	
						pinInteger = Integer.parseInt(newPinTextField.getText()); //User pin input is placed into variable pinInteger
							
						//Index for user account type is placed in comboBoxSdelectionInteger
						comboBoxSelectionInteger = typeAccountComboBox.getSelectedIndex();
						//Get the text from the selected combo box item and place in typeString String
						accountString  = (String) typeAccountComboBox.getItemAt(comboBoxSelectionInteger);
						if(comboBoxSelectionInteger == 1)
						{	
							myAccount[lastAccountInteger] = new CheckingAccount(nameString, pinInteger, NEW_CHECKING_FLOAT, 
									transAmtFloat, accountString, typeString, insufficientFundsBoolean);
							
							//formattedDisplayString object is created from String Class. 
							String formattedDisplayString = String.format("%-20s%-6s%-10s%-10s%n", myAccount[lastAccountInteger].getName(),
									myAccount[lastAccountInteger].getPin(), myAccount[lastAccountInteger].getAccountType(),
									balanceFormat.format(myAccount[lastAccountInteger].getNewBalance()));
							
							//formattedDisplayString is added to the accountOutputTextArea for display
							accountOutputTextArea.append(formattedDisplayString);
							clearNewAccount();	//Clear new account textFields
						}
						else if(comboBoxSelectionInteger == 2)
						{
							myAccount[lastAccountInteger] = new SavingsAccount(nameString, pinInteger, NEW_SAVINGS_FLOAT, 
									transAmtFloat, accountString, typeString, insufficientFundsBoolean);
								
							//formattedDisplayString object is created from String Class. 
							String formattedDisplayString = String.format("%-20s%-6s%-10s%-10s%n", myAccount[lastAccountInteger].getName(),
									myAccount[lastAccountInteger].getPin(), myAccount[lastAccountInteger].getAccountType(), 
									balanceFormat.format(myAccount[lastAccountInteger].getNewBalance()));
							
							//formattedDisplayString is added to the accountOutputTextArea for display
							accountOutputTextArea.append(formattedDisplayString);
							clearNewAccount();	//Clear new account textFields
						}
						else
						{
							JOptionPane.showMessageDialog(null, "PLease Select Account Type", "Account Error",
									JOptionPane.ERROR_MESSAGE);
							lastAccountInteger--;
							typeAccountComboBox.requestFocus();
						}
					}
					else
					{	//Displays message if pin is a duplicate
						newPinTextField.selectAll();
						JOptionPane.showMessageDialog(null, "PIN Not Valid, Account Already Exist", "PIN Error",
								JOptionPane.ERROR_MESSAGE);
							
						lastAccountInteger--;
						newPinTextField.setText("");
						newPinTextField.requestFocus();
					}
				}	
				else
				{	//Displays message if pin is not long enough
					newPinTextField.selectAll();
					JOptionPane.showMessageDialog(null, "PIN Not Valid, Doesn't Meet PIN Requirements", "PIN Error",
							JOptionPane.ERROR_MESSAGE);
								
					lastAccountInteger--;
					newPinTextField.setText("");
					newPinTextField.requestFocus();
				}
			}
			else
			{	//Displays message if accounts cannot be created
				JOptionPane.showMessageDialog(null, "Cannot Add New Account", "Account Error", JOptionPane.ERROR_MESSAGE);
				clearNewAccount();
			}
		}
		catch(ArrayIndexOutOfBoundsException err)
		{	//Displays message if accounts cannot be created
			JOptionPane.showMessageDialog(null, "Cannot Add New Account", "Account Error", JOptionPane.ERROR_MESSAGE);
			clearNewAccount();
		}
	}//end of processNewAccount method
	
	//this method checks if the entered pin is correct and then withdraws or deposits depending on the radio button
	public void processTransaction()
	{
		//Create an object balanceFormat from the DecimalFormat Class to
		// be used to format balance amounts
		DecimalFormat balanceFormat = new DecimalFormat(" $#,###.00");
			
		//Create local Strings and variables to be used to process new accounts
		String pinString, nameString, accountString;	//Will hold account name and pin input for verification of length
		float transAmtFloat = 0f;
		float NEW_CHECKING_FLOAT, NEW_SAVINGS_FLOAT;	//Will hold transaction amount and current account balance
		int confirmBoxInteger, confirmPinInteger, pinInteger, amountTextFieldCheckInteger,	//Will hold confirm dialog box selection, pin
				amountBoxConfirmInteger;	 //verification for existing account, and account pin															
		boolean insufficientFundsBoolean = true;	//Will hold boolean value for insufficient funds
		
		//Your process a transaction (deposit or withdrawal) should go here!
		if(depositRadioButton.isSelected())
		{
			typeString = "Deposit";	//If depositRadioButton is selected, typeString will equal Deposit
		}
		if(withdrawRadioButton.isSelected())
		{
			typeString = "Withdraw";	//If withdrawRadioButton is selected, typeString will equal Withdraw	
		}
			
		//Get user input for transaction amount
		transAmtFloat = Float.parseFloat(amountTextField.getText());
				
		//Get user input for account pin
		pinString = confirmPinTextField.getText();
		if(pinString.length() == 4)	//Verify pin is at least 4 digits
		{	
			confirmPinInteger = pinCheck();	//Retrieve pin verification from pinCheck method to verify account exists									
			if(confirmPinInteger != -1)	//If entered pin is long enough and account exists
			{
				//Ask user do they want to proceed with transaction
				confirmBoxInteger = JOptionPane.showConfirmDialog(null, "Do You Wish to " + typeString
						+ balanceFormat.format(transAmtFloat) + " to this Account?");
				if(confirmBoxInteger == JOptionPane.YES_OPTION)	//If yes option is selected
				{
					if(depositRadioButton.isSelected())	//If depositRadioButton is selected, funds are added to account
					{	
						//Add user input to the array matching the verified account
						accountString = myAccount[confirmPinInteger].getAccountType();
						if(accountString == "Checking")
						{	
							nameString = myAccount[confirmPinInteger].getName();
							pinInteger = myAccount[confirmPinInteger].getPin();
							NEW_CHECKING_FLOAT = myAccount[confirmPinInteger].getNewBalance();
							myAccount[confirmPinInteger] = new CheckingAccount(nameString, pinInteger, NEW_CHECKING_FLOAT,
									transAmtFloat, accountString, typeString, insufficientFundsBoolean);
							
							//formattedDepositDisplayString object is created from String Class to display new funds
							String formattedDepositDisplayString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n", 
									myAccount[confirmPinInteger].getPin(), myAccount[confirmPinInteger].getName(),
									myAccount[confirmPinInteger].getAccountType(), myAccount[confirmPinInteger].getTransType(), 
									balanceFormat.format(myAccount[confirmPinInteger].getDeposit()),
									balanceFormat.format(myAccount[confirmPinInteger].getCharge()),
									balanceFormat.format(myAccount[confirmPinInteger].getNewBalance()));
							
							//formattedDepositDisplayString is added to transactionOutputTextArea and displayed
							transactionOutputTextArea.append(formattedDepositDisplayString);
							clearTransaction();	//Clear transactions textFields
						}
						if(accountString == "Savings")
						{
							nameString = myAccount[confirmPinInteger].getName();
							pinInteger = myAccount[confirmPinInteger].getPin();
							NEW_SAVINGS_FLOAT = myAccount[confirmPinInteger].getNewBalance();
							myAccount[confirmPinInteger] = new SavingsAccount(nameString, pinInteger, NEW_SAVINGS_FLOAT,
									transAmtFloat, accountString, typeString, insufficientFundsBoolean);
							
							//formattedDepositDisplayString object is created from String Class to display new funds
							String formattedDepositDisplayString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n", 
									myAccount[confirmPinInteger].getPin(), myAccount[confirmPinInteger].getName(),
									myAccount[confirmPinInteger].getAccountType(), myAccount[confirmPinInteger].getTransType(), 
									balanceFormat.format(myAccount[confirmPinInteger].getDeposit()),
									balanceFormat.format(myAccount[confirmPinInteger].getCharge()),
									balanceFormat.format(myAccount[confirmPinInteger].getNewBalance()));
							
							//formattedDepositDisplayString is added to transactionOutputTextArea and displayed
							transactionOutputTextArea.append(formattedDepositDisplayString);
							clearTransaction();	//Clear transactions textFields
						}
					}
					if(withdrawRadioButton.isSelected()) //If withdrawRadioButton is selected, funds are subtracted from account
					{
						//Add user input to the array matching the verified account
						accountString = myAccount[confirmPinInteger].getAccountType();
						if(accountString == "Checking")
						{	
							amountTextFieldCheckInteger = Integer.parseInt(amountTextField.getText());
							if(amountTextFieldCheckInteger > 750)
							{
								amountBoxConfirmInteger = JOptionPane.showConfirmDialog(null, "There is a charge of $2.00 for withdrawls "
										+ "over $750. Do you wish to continue?");
								if(amountBoxConfirmInteger == JOptionPane.YES_OPTION)
								{	
									nameString = myAccount[confirmPinInteger].getName();
									pinInteger = myAccount[confirmPinInteger].getPin();
									NEW_CHECKING_FLOAT = myAccount[confirmPinInteger].getNewBalance();
								
									myAccount[confirmPinInteger] = new CheckingAccount(nameString, pinInteger, NEW_CHECKING_FLOAT,
											transAmtFloat, accountString, typeString, insufficientFundsBoolean);
										
									insufficientFundsBoolean = myAccount[confirmPinInteger].getInsufficientFunds();
									if(insufficientFundsBoolean)	//If insufficientFundsBoolean is true
									{
										//formattedDepositDisplayString object is created from String Class to display new funds
										String formattedDepositDisplayString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n", 
												myAccount[confirmPinInteger].getPin(), myAccount[confirmPinInteger].getName(),
												myAccount[confirmPinInteger].getAccountType(), myAccount[confirmPinInteger].getTransType(), 
												balanceFormat.format(myAccount[confirmPinInteger].getWithdraw()),
												balanceFormat.format(myAccount[confirmPinInteger].getCharge()),
												balanceFormat.format(myAccount[confirmPinInteger].getNewBalance()));

										//formattedDepositDisplayString is added to transactionOutputTextArea and displayed
										transactionOutputTextArea.append(formattedDepositDisplayString);
										clearTransaction();	//Clear transactions textFields
									}
									else
									{	//Will show error message if there are insufficient funds						
										JOptionPane.showMessageDialog(null, "Insufficient Funds. Withdrawl Not Processed"
												, "Withdrawl Error", JOptionPane.ERROR_MESSAGE);
										amountTextField.setText(""); //Select the amountTextField
										amountTextField.requestFocus();
									}
								}
								else if(amountBoxConfirmInteger == JOptionPane.NO_OPTION)
								{
									//formattedDepositDisplayString object is created from String Class to display new funds
									String formattedDepositDisplayString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n", 
											myAccount[confirmPinInteger].getPin(), myAccount[confirmPinInteger].getName(),
											myAccount[confirmPinInteger].getAccountType(), myAccount[confirmPinInteger].getTransType(), 
											balanceFormat.format(myAccount[confirmPinInteger].getWithdraw()),
											balanceFormat.format(myAccount[confirmPinInteger].getCharge()),
											balanceFormat.format(myAccount[confirmPinInteger].getNewBalance()));

									//formattedDepositDisplayString is added to transactionOutputTextArea and displayed
									transactionOutputTextArea.append(formattedDepositDisplayString);
									clearTransaction();	//Clear transactions textFields
										
									JOptionPane.showMessageDialog(null, "Funds were not Withdrawn from Account");
								}
							}
							else
							{
								nameString = myAccount[confirmPinInteger].getName();
								pinInteger = myAccount[confirmPinInteger].getPin();
								NEW_CHECKING_FLOAT = myAccount[confirmPinInteger].getNewBalance();
							
								myAccount[confirmPinInteger] = new CheckingAccount(nameString, pinInteger, NEW_CHECKING_FLOAT,
										transAmtFloat, accountString, typeString, insufficientFundsBoolean);
								
								insufficientFundsBoolean = myAccount[confirmPinInteger].getInsufficientFunds();
								if(insufficientFundsBoolean)	//If insufficientFundsBoolean is true
								{
									//formattedDepositDisplayString object is created from String Class to display new funds
									String formattedDepositDisplayString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n", 
											myAccount[confirmPinInteger].getPin(), myAccount[confirmPinInteger].getName(),
											myAccount[confirmPinInteger].getAccountType(), myAccount[confirmPinInteger].getTransType(), 
											balanceFormat.format(myAccount[confirmPinInteger].getWithdraw()),
											balanceFormat.format(myAccount[confirmPinInteger].getCharge()),
											balanceFormat.format(myAccount[confirmPinInteger].getNewBalance()));

									//formattedDepositDisplayString is added to transactionOutputTextArea and displayed
									transactionOutputTextArea.append(formattedDepositDisplayString);
									clearTransaction();	//Clear transactions textFields
								}
								else
								{	//Will show error message if there are insufficient funds						
									JOptionPane.showMessageDialog(null, "Insufficient Funds. Withdrawl Not Processed"
											, "Withdrawl Error", JOptionPane.ERROR_MESSAGE);
									amountTextField.selectAll(); //Select the amountTextField
								}
							}
						}
						if(accountString == "Savings")
						{
							amountTextFieldCheckInteger = Integer.parseInt(amountTextField.getText());
							if(amountTextFieldCheckInteger > 2000)
							{	
								amountBoxConfirmInteger = JOptionPane.showConfirmDialog(null, "There is a charge of $2.50 for withdrawls "
										+ "over $2000. Do you wish to continue?");
								if(amountBoxConfirmInteger == JOptionPane.YES_OPTION)
								{	
									nameString = myAccount[confirmPinInteger].getName();
									pinInteger = myAccount[confirmPinInteger].getPin();
									NEW_SAVINGS_FLOAT = myAccount[confirmPinInteger].getNewBalance();
								
									myAccount[confirmPinInteger] = new SavingsAccount(nameString, pinInteger, NEW_SAVINGS_FLOAT,
											transAmtFloat, accountString, typeString, insufficientFundsBoolean);
										
									insufficientFundsBoolean = myAccount[confirmPinInteger].getInsufficientFunds();
									if(insufficientFundsBoolean)	//If insufficientFundsBoolean is true
									{
										//formattedDepositDisplayString object is created from String Class to display new funds
										String formattedDepositDisplayString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n", 
												myAccount[confirmPinInteger].getPin(), myAccount[confirmPinInteger].getName(),
												myAccount[confirmPinInteger].getAccountType(), myAccount[confirmPinInteger].getTransType(), 
												balanceFormat.format(myAccount[confirmPinInteger].getWithdraw()),
												balanceFormat.format(myAccount[confirmPinInteger].getCharge()),
												balanceFormat.format(myAccount[confirmPinInteger].getNewBalance()));

										//formattedDepositDisplayString is added to transactionOutputTextArea and displayed
										transactionOutputTextArea.append(formattedDepositDisplayString);
										clearTransaction();	//Clear transactions textFields
									}
									else
									{	//Will show error message if there are insufficient funds						
										JOptionPane.showMessageDialog(null, "Insufficient Funds. Withdrawl Not Processed"
												, "Withdrawl Error", JOptionPane.ERROR_MESSAGE);
										amountTextField.setText(""); //Select the amountTextField
										amountTextField.requestFocus();
									}
								}
								else if(amountBoxConfirmInteger == JOptionPane.NO_OPTION)
								{	
									//formattedDepositDisplayString object is created from String Class to display new funds
									String formattedDepositDisplayString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n", 
											myAccount[confirmPinInteger].getPin(), myAccount[confirmPinInteger].getName(),
											myAccount[confirmPinInteger].getAccountType(), myAccount[confirmPinInteger].getTransType(), 
											balanceFormat.format(myAccount[confirmPinInteger].getWithdraw()),
											balanceFormat.format(myAccount[confirmPinInteger].getCharge()),
											balanceFormat.format(myAccount[confirmPinInteger].getNewBalance()));
										
									//formattedDepositDisplayString is added to transactionOutputTextArea and displayed
									transactionOutputTextArea.append(formattedDepositDisplayString);
									clearTransaction();	//Clear transactions textFields
										
									JOptionPane.showMessageDialog(null, "Funds were not Widrawn from Account");

								}
							}
							else
							{
								nameString = myAccount[confirmPinInteger].getName();
								pinInteger = myAccount[confirmPinInteger].getPin();
								NEW_CHECKING_FLOAT = myAccount[confirmPinInteger].getNewBalance();
							
								myAccount[confirmPinInteger] = new SavingsAccount(nameString, pinInteger, NEW_CHECKING_FLOAT,
										transAmtFloat, accountString, typeString, insufficientFundsBoolean);
									
								insufficientFundsBoolean = myAccount[confirmPinInteger].getInsufficientFunds();
								if(insufficientFundsBoolean)	//If insufficientFundsBoolean is true
								{
									//formattedDepositDisplayString object is created from String Class to display new funds
									String formattedDepositDisplayString = String.format("%-6s%-15s%-13s%-12s%-12s%-10s%-10s%n", 
											myAccount[confirmPinInteger].getPin(), myAccount[confirmPinInteger].getName(),
											myAccount[confirmPinInteger].getAccountType(), myAccount[confirmPinInteger].getTransType(),
											balanceFormat.format(myAccount[confirmPinInteger].getWithdraw()),
											balanceFormat.format(myAccount[confirmPinInteger].getCharge()),
											balanceFormat.format(myAccount[confirmPinInteger].getNewBalance()));

									//formattedDepositDisplayString is added to transactionOutputTextArea and displayed
									transactionOutputTextArea.append(formattedDepositDisplayString);
									clearTransaction();	//Clear transactions textFields
								}
								else
								{	//Will show error message if there are insufficient funds						
									JOptionPane.showMessageDialog(null, "Insufficient Funds. Withdrawl Not Processed"
											, "Withdrawl Error", JOptionPane.ERROR_MESSAGE);
									amountTextField.setText(""); //Select the amountTextField
									amountTextField.requestFocus();
								}
							}	
						}																	
					}	
				}
			}	
			else
			{	//Will show error message is pin is not valid
				JOptionPane.showMessageDialog(null, "PIN Not Valid, Account Doesn't Exist", "PIN/Account Error",
						JOptionPane.ERROR_MESSAGE);
				confirmPinTextField.setText(""); //Select the confirmPinTextField
				confirmPinTextField.requestFocus();
			}
		}
		else
		{	//Will show an error message is pin is not valid
			JOptionPane.showMessageDialog(null, "PIN Not Valid, PIN not Long Enough", "PIN Error", 
				JOptionPane.ERROR_MESSAGE);
			confirmPinTextField.setText(""); //Select the confirmPinTextField
			confirmPinTextField.requestFocus();
		}
			
	}//end of processTransaction method	
	
	//this method shows the account information in a JOption pane
	public void displayAllAccountInfo()
	{
		//Create an object balanceFormat from the DecimalFormat Class to
		// be used to format balance amounts
		DecimalFormat balanceFormat = new DecimalFormat(" $#,###.00");
			
		//formattedTotalsDisplayString object is created from String Class
		String formattedTotalsDisplayString = String.format("%-20s%-6s%10s%n", "Name", "Pin", "Balance");
		JTextArea displayTextArea = new JTextArea(10, 15); //displayTextArea object created from JTextArea Class
		int accountIndex = 0; //accountIndex local variable created for loop calculation
			
		displayTextArea.setFont(taFont); //set taFont object to displayTextArea object
		displayTextArea.setText(formattedTotalsDisplayString); //set formattedTotalsDisplayString to displayTextArea
				
		//Your code for displaying all accounts goes here!
		while(accountIndex <= lastAccountInteger)
		{
			//formattedTotalsAccountString object is created from String Class to display all accounts
			String formattedTotalsAccountString = String.format("%-20s%-6s%10s%n", myAccount[accountIndex].getName(),
					myAccount[accountIndex].getPin(), 
					balanceFormat.format(myAccount[accountIndex].getNewBalance()));
						
			//formattedTotalsAccountString is added to displayTextArea and displayed
			displayTextArea.append(formattedTotalsAccountString);
			accountIndex++; //accountIndex is increased by one value
		}
			//Display the displayTextArea in a message box
			JOptionPane.showMessageDialog(null, displayTextArea, "Accounts", JOptionPane.INFORMATION_MESSAGE);
			
	}//end of displayAllAccountInfo method	
	
	public int pinVerify()
	{
		//Create local variables
		int verifyPinInteger, arrayPinInteger, pinIndexInteger = 0;
		boolean pinBoolean = false;
		
		//Get text from newPinTextField
		verifyPinInteger = Integer.parseInt(newPinTextField.getText());
		
		//Compare verifyPinInteger to already used pins in arrays. If pin is not already being used,
		//return -1 value. If pin is being used, return index of used pin
		while(!pinBoolean && pinIndexInteger < lastAccountInteger)	
		{
			arrayPinInteger = myAccount[pinIndexInteger].getPin();
			if(verifyPinInteger == arrayPinInteger)
			{
				pinBoolean = true;
			}
			else
			{
				pinIndexInteger++;
			}
		}
		if(pinBoolean)
		{
			return pinIndexInteger;
		}
		else
		{
			return -1;
		}
	}

	public int pinCheck()
	{
		//Create local variable
		int confirmPinInteger, arrayPinInteger, pinIndexInteger = 0;
		boolean pinBoolean = false;
		
		//Get text from confirmPinTextField
		confirmPinInteger = Integer.parseInt(confirmPinTextField.getText());
		
		//Compare confirmPinInteger to already used pins in arrays. If pin is not already being used,
		//return -1 value. If pin is being used, return index of used pin
		while(!pinBoolean && pinIndexInteger <= lastAccountInteger)
		{
			arrayPinInteger = myAccount[pinIndexInteger].getPin();
			if(confirmPinInteger == arrayPinInteger)
			{
				pinBoolean = true;
			}
			else
			{
				pinIndexInteger++;
			}
		}
		if(pinBoolean)
		{
			return pinIndexInteger;
		}
		else
		{
			return -1;
		}
	}
	
	public void clearNewAccount()
	{
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		newPinTextField.setText("");
		firstNameTextField.requestFocus();
		typeAccountComboBox.setSelectedIndex(0);
	}
	
	public void clearTransaction()
	{
		confirmPinTextField.setText("");
		amountTextField.setText("");
		confirmPinTextField.requestFocus();
		invisibleRadioButton.setSelected(true);	
	}	
			
}
