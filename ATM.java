// ATM.java
// Represents an automated teller machine

public class ATM
{
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   //private DepositSlot depositSlot; // ATM's deposit slot
   private BankDatabase bankDatabase; // account information database

   // constants corresponding to account menu options
   private static final int SAVINGACC = 1;

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int TRANSFER = 3;
   //private static final int DEPOSIT = 4;
   private static final int EXIT = 0;

   // no-argument ATM constructor initializes instance variables
   public ATM()
   {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      keypad = new Keypad(); // create keypad
      screen = new Screen(keypad.getPanel()); // create screen
      cashDispenser = new CashDispenser(); // create cash dispenser
      //depositSlot = new DepositSlot(); // create deposit slot
      bankDatabase = new BankDatabase(); // create acct info database
   } // end no-argument ATM constructor

   // start ATM
   public void run()
   {
      // welcome and authenticate user; perform transactions
      while ( true )
      {
         // loop while user is not yet authenticated
         while ( !userAuthenticated )
         {
            screen.displayMessageLine( "Welcome!" );
            authenticateUser(); // authenticate user
         } // end while

         performTransactions(); // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine( "\nThank you! Goodbye!" );
         keypad.enterFlag();
         if(keypad.enterFlagReturn() == true)
         {
             screen.restTextBox();
         }
      } // end while
   } // end method run

   // attempts to authenticate user against database
   private void authenticateUser()
   {
      keypad.enterFlag(false); //Set the enter state to false(user haven't press enter)
      
      screen.displayMessage( "\nPlease enter your account number: " );
      keypad.userInput();//Enter user input state
      int accountNumber = keypad.getInput(); // input account number
      screen.displayMessage(keypad.getstr_input());//display user input
      keypad.removeUserinput();
      
      screen.displayMessage( "\nPlease enter your PIN: " ); // prompt for PIN
      keypad.passwordInfo();//enter input password state
      int pin = keypad.getInput(); // input PIN
      if(keypad.getstr_input() == "0")
          screen.displayMessage(" ");
      else//password masking
          for(int i = 0; i < keypad.getstr_input().length(); i++)
              screen.displayMessage("*");
      keypad.removePasswordField();

      // set userAuthenticated to boolean value returned by database
      userAuthenticated =
         bankDatabase.authenticateUser( accountNumber, pin );

      // check whether authentication succeeded
      if ( userAuthenticated )
      {
         currentAccountNumber = accountNumber; // save user's account #
      } // end if
      else if(pin == 0)
      {
          keypad.inputPasswordState(false);
          screen.restTextBox();
      }
      else
         screen.displayMessageLine(
             "\nInvalid Account number or PIN. Please try again." );
   } // end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions()
   {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;

      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while ( !userExited )
      {
         //int accountMenuSelection = selectAccount();
          // show main menu and get user selection
         
         screen.restTextBox();
         int mainMenuSelection = displayMainMenu();

         //switch(accountMenuSelection)

             //case SAVINGACC:
             // decide how to proceed based on user's menu selection
             switch ( mainMenuSelection )
             {
                // user chose to perform one of three transaction types
                case BALANCE_INQUIRY:
                     
                case WITHDRAWAL:
                   
                case TRANSFER:
                
                //case DEPOSIT:


                   // initialize as new object of chosen type
                   currentTransaction =
                      createTransaction( mainMenuSelection );

                     screen.restTextBox();

                     currentTransaction.execute(); // execute transaction
                     keypad.enterFlag();//prompt user input
                     keypad.enterFlag(false);
                     if(keypad.enterFlagReturn() == true)//User press enter
                     {
                         screen.displayMessage("");
                         return;
                     }
                   break;
                case EXIT: // user chose to terminate session
                   screen.restTextBox();
                   screen.displayMessageLine("Exiting the system." );
                    screen.displayMessageLine("Card ejected.");
                    screen.displayMessageLine("Please take your card.");
                    screen.displayMessageLine("Please take the cash if you have withdrawn");
                   userExited = true; // this ATM session should end
                   break;
                default: // user did not enter an integer from 1-4
                   screen.displayMessageLine(
                      "\nYou did not enter a valid selection. Try again." );
                   break;
             } // end switch
             //break;

      } // end while
   } // end method performTransactions


   // display the main menu and return an input selection
   private int displayMainMenu()
   {
      screen.displayMessageLine( "Main menu:" );
      screen.displayMessageLine( "1 - View my balance" );
      screen.displayMessageLine( "2 - Withdraw cash" );
      screen.displayMessageLine( "3 - Transfer" );
      screen.displayMessageLine( "0/Cancel - Exit\n" );
      screen.displayMessage( "Enter a choice: " );
      keypad.userInput();
      screen.displayMessage(keypad.getstr_input());
      return keypad.getInput(); // return user's selection
   } // end method displayMainMenu

   // return object of specified Transaction subclass
   private Transaction createTransaction( int type )
   {
      Transaction temp = null; // temporary Transaction variable

      // determine which type of Transaction to create
      switch ( type )
      {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
            temp = new BalanceInquiry(
               currentAccountNumber, screen, bankDatabase );
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            temp = new Withdrawal( currentAccountNumber, screen,
               bankDatabase, keypad, cashDispenser );
            break;
         case TRANSFER:
             temp = new Transfer( currentAccountNumber, screen,
               bankDatabase, keypad);
         /*case DEPOSIT: // create new Deposit transaction
            temp = new Deposit( currentAccountNumber, screen,
               bankDatabase, keypad, depositSlot );
            break;*/
      } // end switch

      return temp; // return the newly created object
   } // end method createTransaction
} // end class ATM
