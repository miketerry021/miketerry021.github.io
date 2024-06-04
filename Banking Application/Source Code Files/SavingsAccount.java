/*
 * Programmer Name		: Michael Terry
 * Date					: 12/04/2023
 * Class Name			: SavingsAccount
 * Project Description	: 
  */
public class SavingsAccount extends Account
{
	//Create local private variables
	private final float SAVINGS_WITHDRAW_FEE_FLOAT = 2.50f, SAVINGS_MIN_FLOAT = 2000f;
	
	public SavingsAccount(String nameString, int pinInteger, float NEW_SAVINGS_FLOAT,
			float transAmtFloat, String accountString, String typeString, boolean insufficientFundsBoolean)
	{
		super(nameString, pinInteger, transAmtFloat, typeString, insufficientFundsBoolean);
		setAccountType(accountString);
		setAccountFunds(NEW_SAVINGS_FLOAT);
		calculateBalance();
	}
	
	private void setAccountType(String accountString)			//setAccountType method
	{
		accountType = accountString;						//accountType equals accountString
	}
	
	private void setAccountFunds( float NEW_SAVINGS_FLOAT)		//setAccountFunds method
	{
		accountFundsFloat = NEW_SAVINGS_FLOAT;				//accountFundsFloat equals NEW_SAVINGS_FLOAT
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
			if(newTransAmtFloat > SAVINGS_MIN_FLOAT)
			{	
				accountFundsFloat -= (newTransAmtFloat + SAVINGS_WITHDRAW_FEE_FLOAT);	//Subtract newBalanceFloat
				chargeFloat = SAVINGS_WITHDRAW_FEE_FLOAT;
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
	
	public void setWithdraw()					//setWithdraw method
	{
		newWithdrawFloat = newTransAmtFloat;		//newWithdrawFloat equals newTransAmtFloat
	}
}
