// Account.java
// Represents a bank account

import java.util.Date;

public class Account
{
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   protected double availableBalance; // funds available for withdrawal
   protected double totalBalance; // funds available + pending deposit


   // Account constructor initializes attributes
   public Account( int theAccountNumber, int thePIN,
      double theAvailableBalance, double theTotalBalance )
   {
      accountNumber = theAccountNumber;
      pin = thePIN;
      availableBalance = theAvailableBalance;
      totalBalance = theTotalBalance;
   } // end Account constructor

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN( int userPIN )
   {
      if ( userPIN == pin )
         return true;
      else
         return false;
   } // end method validatePIN

   // returns available balance
   public double getAvailableBalance()
   {
      return availableBalance;
   } // end getAvailableBalance

   // returns the total balance
   public double getTotalBalance()
   {
      return totalBalance;
   } // end method getTotalBalance

   // credits an amount to the save account
   public void credit( double amount )
   {
      totalBalance += amount; // add to total balance
      availableBalance += amount;// add to available balance
   } // end method credit

   // debits an amount from the save account
   public void debit( double amount )
   {
      availableBalance -= amount; // subtract from available balance
      totalBalance -= amount; // subtract from total balance
   } // end method debit

   // returns account number
   public int getAccountNumber()
   {
      return accountNumber;
   } // end method getAccountNumber
} // end class Account
