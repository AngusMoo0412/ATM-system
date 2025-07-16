// BankDatabase.java
// Represents the bank account information database

public class BankDatabase
{
   private Account accounts[]; // array of Accounts

   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {
      accounts = new Account[ 4 ]; // just 2 saving accounts and 2 cheque accounts for testing
      accounts[ 0 ] = new SavingAccount( 12345, 54321, 1000.0, 1200.0 );
      accounts[ 1 ] = new SavingAccount( 98765, 56789, 200.0, 200.0 );
      accounts[ 2 ] = new ChequeAccount( 11111, 11111, 1000.0, 1000.0 );
      accounts[ 3 ] = new ChequeAccount( 22222, 22222, 0.0, 0.0 );
   } // end no-argument BankDatabase constructor

   // retrieve Account object containing specified account number
   private Account getAccount( int accountNumber )
   {
      // loop through accounts searching for matching account number
      for ( Account currentAccount : accounts )
      {
         // return current account if match found
         if ( currentAccount.getAccountNumber() == accountNumber )
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount

   //Determine if the account number exist in the bank database
   public int account_matching(int search_account_number)
   {
       if(getAccount(search_account_number) != null)
       {
        return 1;//return 1 if account number exist in the bank database
       }
       else
       return -1;// return -1 if no matching account was found
   } //end method account_matching

   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public boolean authenticateUser( int userAccountNumber, int userPIN )
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount( userAccountNumber );

      // if account exists, return result of Account method validatePIN
      if ( userAccount != null )
         return userAccount.validatePIN( userPIN );
      else
         return false; // account number not found, so return false
   } // end method authenticateUser

   // return available balance of Account with specified account number
   public double getAvailableBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getAvailableBalance();
   } // end method getAvailableBalance

   // return total balance of Account with specified account number
   public double getTotalBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getTotalBalance();
   } // end method getTotalBalance

      // credit an amount to Account with specified account number
   public void credit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).credit( amount );
   } // end method credit

      public void debit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).debit( amount );
   } // end method debit


} // end class BankDatabase
