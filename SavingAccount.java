//SaveAccount.java
//Represent saving account
import java.util.Date;

public class SavingAccount extends Account
{
    private double InterestRate = 0.001; //Assign 0.001 to InterestRate

     //the Constructor of SavingAccount
    public SavingAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance)
    {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
    }

    //Get the interest rate
    public double getInterestRate(){
        return InterestRate;
        //end method of getInterestRate
    }

    //Set the interest rate
    public void setInterestRate(double theInterestRate){
        if (theInterestRate >= 0.0f) //if the parameter "theInterestRate" is greater or equal to 0, set InterestRate by theInterestRate
            InterestRate = theInterestRate;
        else //if the parameter "theInterestRate" is smaller than 0, set throw a new IllegalArgumentException
            throw new IllegalArgumentException ("Interest Rate must greater than 0.00");
        //end method of setInterestRate
    }


}
