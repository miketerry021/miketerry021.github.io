import javax.swing.JOptionPane;

/*
 * Class			:	CoffeeCalculation
 * Programmer		:	Michael Terry
 * Date				:	11/05/2023
 * Description		:	This Class will receive user input data from the driver Class.
 *					:	The total drink sales will then be calculated from that data. The
 *					:	calculated values will then be returned to the driver Class.
 */


public class CoffeeCalculation 
{ 
	//Create private instance constants to store drink prices
	private final float MOCHA_PRICE_FLOAT = 3.75f, LATTE_PRICE_FLOAT = 3.25f,
			DRIP_PRICE_FLOAT = 1.75f, TAX_RATE_FLOAT = 0.0975f;
	
	//Create private instance variables
	private String tempDrinkString, returnDrinkString;
	private int tempDrinkSelectIndexInteger, tempSalesQuantityInteger, tempDrinkPurchaseIndexInteger; 
	private float tempDrinkPriceFloat, tempDrinkSalesFloat, salesTaxFloat, totalSalesFloat, subTotalFloat;
	private boolean tempDecafBoolean;
	private static float tempSubTotalFloat;
		
	
	/**********************************************************************************************
	 * - CoffeeCalculation(drinkSelectIndexInteger) Constructor -
	 * Call the setDrinkPrice(drinkSelectIndexInteger) method
	 * Call the showDrinkPrice() method
	 **********************************************************************************************/
	CoffeeCalculation(int drinkSelectIndexInteger)
	{
		setDrinkPrice(drinkSelectIndexInteger);
		showDrinkPrice();
	}
	
	//Receive drinkSelectIndexInteger and store in tempDrinkSelectIndexInteger
	private void setDrinkPrice(int drinkSelectIndexInteger)
	{
		
		tempDrinkSelectIndexInteger = drinkSelectIndexInteger;
	}
	
	//showDrinkPrice method
	private void showDrinkPrice()
	{
		//
		if(tempDrinkSelectIndexInteger == 1)
		{
			tempDrinkPriceFloat = MOCHA_PRICE_FLOAT;
		}
		if(tempDrinkSelectIndexInteger == 2)
		{
			tempDrinkPriceFloat = LATTE_PRICE_FLOAT;
		}
		if(tempDrinkSelectIndexInteger == 3)
		{
			tempDrinkPriceFloat = DRIP_PRICE_FLOAT;
		}	
	}
	
	//Return tempDrinkPriceFloat variable
	public float getDrinkPrice()
	{
		return tempDrinkPriceFloat;
	}
	
	/************************************************************************************************
	 * - CoffeeCalculation(salesQuantityInteger, drinkIndex) constructor -
	 * Call the setQuantity(salesQuantityInteger) method
	 * Call the calculation(drinkIndexInteger) method
	 * Call the setDrinkSelection(drinkString) method
	 * Call the setDecaf(decafBoolean) method
	 * Call the calculateDrinkSales() method
	 * Call the calculateDrinkType method
	 ************************************************************************************************/
	CoffeeCalculation(int salesQuantityInteger, int drinkIndexInteger, String drinkString,
			boolean decafBoolean)
	{
		setSalesQuantity(salesQuantityInteger);
		setDrinkPurchase(drinkIndexInteger);
		setDrinkSelection(drinkString);
		setDecaf(decafBoolean);
		calculateDrinkSales();
		calculateDrinkType();
	}
	
	//Receive salesQuantityInteger and store in tempSalesQuanttyInteger
	private void setSalesQuantity(int salesQuantityInteger)
	{
		tempSalesQuantityInteger = salesQuantityInteger;
	}
	
	//Receive drinIndexInteger and store in tempDrinkPurchaseIndexInteger
	private void setDrinkPurchase(int drinkIndexInteger)
	{
		tempDrinkPurchaseIndexInteger = drinkIndexInteger;
	}
	
	//Receive drinkString and store in tempDrinkString
	private void setDrinkSelection(String drinkString)
	{
		tempDrinkString = drinkString;
	}
	
	//Receive decafBoolean and store in tempDecafBoolean
	private void setDecaf(boolean decafBoolean)
	{
		tempDecafBoolean = decafBoolean;
	}
	
	//calculateDrinkSales method
	private void calculateDrinkSales()
	{
		//If Statement
		//Receive tempDrinkPurchaseIndexInteger and calculate the price of a drink
		//based on index selection
		if(tempDrinkPurchaseIndexInteger == 1)
		{	
			tempDrinkSalesFloat = tempSalesQuantityInteger * MOCHA_PRICE_FLOAT;
		}
		if(tempDrinkPurchaseIndexInteger == 2)
		{
			tempDrinkSalesFloat = tempSalesQuantityInteger * LATTE_PRICE_FLOAT;
		}
		if(tempDrinkPurchaseIndexInteger == 3)
		{
			tempDrinkSalesFloat = tempSalesQuantityInteger * DRIP_PRICE_FLOAT;
		}
		
		//Calculate subTotalFloat to equal subTotalFloat + drinkSalesFloat
		tempSubTotalFloat += tempDrinkSalesFloat;
		
	}
	
	//calculateDrinkType
	private void calculateDrinkType()
	{
		//If Statement
		//Receive tempDecafBoolean. Will and "Decaf" if value is true. Will not if false
		if(tempDecafBoolean)
		{
			returnDrinkString = "Decaf " + tempDrinkString;
		}
		else
		{
			returnDrinkString = tempDrinkString;
		}
	}
	
	//Return drinkSalesFloat variable
	public float getDrinkSale()
	{
		return tempDrinkSalesFloat;
	}
	
	//Return returnDrinkString String
	public String getDrinkType()
	{
		return returnDrinkString;
	}
	

	/**********************************************************************************************
	 * - CoffeeCalculation Constructor - 
	 * Call the calculateTotals method
	 **********************************************************************************************/
	CoffeeCalculation()
	{
		calculateTotals();				
	}

	//The calculateTotals method
	private void calculateTotals()
	{
		//Set subTotalFloat to equal tempSubTotalFloat
		subTotalFloat = tempSubTotalFloat;
	
		//Calculate saleTaxFloat to equal subTotalFloat multiplied by TAX_RATE_FLOAT
		salesTaxFloat = subTotalFloat * TAX_RATE_FLOAT;
	
		//Calculate totalSalesFloat to equal subTotalFloat + salesTaxFloat
		totalSalesFloat = subTotalFloat + salesTaxFloat;
		
		//Set tempSubTotalFloat to 0.0f
		tempSubTotalFloat = 0.0f;		
	
	}

	//Return subTotalFloat variable
	public float getSubTotal()
	{
		return subTotalFloat;
	}

	//Return totalSalesFloat variable
	public float getTotalSale()
	{
		return totalSalesFloat;
	}

	//Return salesTax variable 
	public float getSalesTax()
	{
		return salesTaxFloat;
	}

}








