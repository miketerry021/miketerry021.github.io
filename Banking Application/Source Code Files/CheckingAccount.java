/*Folder/Project Name	: Project8
 * Programmer Name		: Michael Terry
 * Date					: 12/04/17
 * Class Name			: CheckingAccount
 * Project Description	: 
  */
public class CheckingAccount extends Account
{
	//Create local private variables
	private final float CHECKING_WITHDRAW_FEE_FLOAT = 2f, CHECKING_MIN_FLOAT = 750f;
	
	public CheckingAccount(String nameString, int pinInteger, float NEW_CHECKING_FLOAT,
			float transAmtFloat, String accountString, String typeString, boolean insufficientFundsBoolean)
	{
		super(nameString, pinInteger, transAmtFloat, typeString, insufficientFundsBoolean);
		setAccountType(accountString);
		setAccountFunds(NEW_CHECKING_FLOAT);
		calculateBalance();
	}
	
	private void setAccountType(String accountString)		//setAccountType method
	{
		accountType = accountString;					//accountType equals accountString
	}
	
	private void setAccountFunds(float NEW_CHECKING_FLOAT)		//setAccountFunds method
	{
		accountFundsFloat = NEW_CHECKING_FLOAT;			//accountFundsFloat equals NEW_CHECKING_FLOAT
	}
	
	private void calculateBalance()
	{
		tempNewBalanceFloat = accountFundsFloat;	//tempNewBalanceFloat equals newBalanceFloat
		
		if(newTypeString == "Deposit")	//If newTypeString equals Deposit
		{
			accountFundsFloat += newTransAmtFloat;	//Accumulate newBalanceFloat	
		}
		if(newTypeString == "Withdraw")	//If newTypeString equals Withdraw
		{
			if(newTransAmtFloat > CHECKING_MIN_FLOAT)
			{	
				accountFundsFloat -= (newTransAmtFloat + CHECKING_WITHDRAW_FEE_FLOAT);	//Subtract newBalanceFloat
				chargeFloat = CHECKING_WITHDRAW_FEE_FLOAT;
				if(accountFundsFloat < 0)	//If newBalanceFloat is less than 0
				{
					newInsufficientFundsBoolean = false; //newInsufficientFundsBoolean equals false
					accountFundsFloat = tempNewBalanceFloat;	//newBalanceFloat equals tempNewBalanceFloat
					chargeFloat = 0.0f;
				}
			}
			else
			{
				accountFundsFloat -= newTransAmtFloat;
				if(accountFundsFloat < 0)	//If newBalanceFloat is less than 0
				{
					newInsufficientFundsBoolean = false; //newInsufficientFundsBoolean equals false
					accountFundsFloat = tempNewBalanceFloat;	//newBalanceFloat equals tempNewBalanceFloat
				}
			}
		}
	}
	

	public void setDeposit()						//setDeposit method
	{
		newDepositFloat = newTransAmtFloat;		//newDepositFloat equals newTransAmtFloat 
	}
	
	public void setWithdraw()				//setWithdraw method
	{
		newWithdrawFloat = newTransAmtFloat;		//newWithdrawFloat equals newTransAmtFloat
	}
}
