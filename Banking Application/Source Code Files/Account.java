/*Folder/Project Name	: Project8
 * Programmer Name		: Michael Terry
 * Date					: 12/04/17	
 * Class Name			: Account
 * Project Description	: 
  */
public abstract class Account
{
	protected String newNameString, newTypeString, accountType;; 
	protected int newPinInteger;
	protected float newTransAmtFloat, newBalanceFloat, newDepositFloat, newWithdrawFloat,
		tempNewBalanceFloat, chargeFloat = 0.0f, accountFundsFloat = 0.0f;
	protected boolean newInsufficientFundsBoolean;
	
	public Account()
	{
		
		
	}
	

	/*****************************************************************************************************************************
	 * - Account(namesString, pinInteger, NEW_ACCOUNT_FLOAT, transAmtFloat, typeString, insufficientFundsBoolean) method
	 * 
	 ****************************************************************************************************************************/
	public Account(String nameString, int pinInteger, float transAmtFloat, String typeString, boolean insufficientFundsBoolean)
	{
		setName(nameString);	//Calls the setName method	
		setPin(pinInteger);		//Calls the setPin method
		setTransAmount(transAmtFloat);		//Calls the setTransAmount method
		setTransType(typeString);
		setBoolean(insufficientFundsBoolean);	//Call the setBoolean method
		setDeposit();		//Calls the setDeposit method
		setWithdraw();		//Calls the setWithdraw method
	}
	
	private void setName(String nameString)		//setName method
	{
		newNameString = nameString;		//newNameString value equals nameString
	}
	
	private void setPin(int pinInteger)		//setPin method
	{
		newPinInteger = pinInteger;		//newPinInteger value equals pinInteger
	}
	
	private void setTransAmount(float transAmtFloat)	//setTransAmount method
	{
		newTransAmtFloat = transAmtFloat;		//newTransAmtFloat equals transAmtFloat
	}
	
	private void setTransType(String typeString)
	{
		newTypeString = typeString;
	}
		
	private void setBoolean(boolean insufficientFundsBoolean)	//setBoolean method
	{
		newInsufficientFundsBoolean = insufficientFundsBoolean;		//newInsufficientFundsBoolean equals insufficientFundsBoolean
	}
	
	
	public abstract void setDeposit();		//setDeposit method
	
	public abstract void setWithdraw();	//setWithdraw method
	
	
	/******************************************************************************************************
	 * Get Methods
	 * 
	 ******************************************************************************************************/
	public String getName()		//getName method
	{
		return newNameString;	//return newNameString value
	}
	
	public int getPin()			//getPin method
	{
		return newPinInteger;	//return newPinInteger value
	}
	
	public float getTransAmount()	//getTransAmount method	
	{
		return newTransAmtFloat;	//return newTransAmtFloat
	}
	
	public String getTransType()	//getTransType method
	{
		return newTypeString;		//return newTypeString value
	}
	
	public float getNewBalance()	//getNewBalance method
	{
		return accountFundsFloat;		//return newBalanceFloat value		
	}
	
	public boolean getInsufficientFunds()		//getInsufficientFunds method
	{
		return newInsufficientFundsBoolean;		//return newInsufficientFundsBoolean value
	}
	
	public String getAccountType()		//getAccountType method
	{
		return accountType;			//return accountType String
	}
	
	public float getCharge()		//getCharge method
	{
		return chargeFloat;			//return chargeFloat value
	}
	public float getDeposit()		//getDeposit method
	{
		return newDepositFloat;		//return newDepositFloat value
	}
	
	public float getWithdraw()		//getWithdraw method	
	{
		return newWithdrawFloat;	//return newWithdrawFloat value
	}
}
