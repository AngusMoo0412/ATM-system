//ChequeAccount.java
public class ChequeAccount extends Account
{
    private final double Default_Limit_Per_Cheque = 10000.0; //assign 10000 to Default_Limit_Per_Cheque
    private double Limit_Per_Cheque = Default_Limit_Per_Cheque; // assign Default_Limit_Per_Cheque to Limit_Per_Cheque

    //the Constructor of ChequeAccount
    public ChequeAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance){
        // initialize superclass variables
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
    }

    public void setLimitPerCheque( double input ) // set limit per cheque
    {
        Limit_Per_Cheque = input;
    }// end method setLimitPerCheque

    public double getLimitPerCheque() // get limit per cheque
    {
        return Limit_Per_Cheque;
    }// end method getLimitPerCheque
}
