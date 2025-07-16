// Withdrawal.java
// Represents a withdrawal ATM transaction
import java.util.Scanner;

public class Withdrawal extends Transaction
{
    private int amount; // amount to withdraw
    private Keypad keypad; // reference to keypad
    private CashDispenser cashDispenser; // reference to cash dispenser

    // constant corresponding to menu option to cancel
    private final static int CANCELED = 0;


    // Withdrawal constructor
    public Withdrawal( int userAccountNumber, Screen atmScreen,
                       BankDatabase atmBankDatabase, Keypad atmKeypad,
                       CashDispenser atmCashDispenser )
    {
        // initialize superclass variables
        super( userAccountNumber, atmScreen, atmBankDatabase );

        // initialize references to keypad and cash dispenser
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
    } // end Withdrawal constructor

    // perform transaction
    public void execute()
    {
        boolean cashDispensed = false; // cash was not dispensed yet
        double availableBalance; // amount available for withdrawal

        //references to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        
        do
        {
            amount = displayMenuOfAmounts();
            // check user choice
            if ( amount != CANCELED )
            {
                screen.displayMessageLine("\nAre you sure you want to withdraw: $" + amount + "?");
                screen.displayMessageLine("\n  1 - confirm         2 - cancel");//confirmation
            
                keypad.userInput();
                int confirm = keypad.getInput();
                
                availableBalance =
                        bankDatabase.getAvailableBalance( getAccountNumber() );

                if(confirm == 1)
                {
                // check whether the user has enough money in the account
                    if ( amount <= availableBalance )
                    {
                        // check whether the cash dispenser has enough money
                        if ( cashDispenser.isSufficientCashAvailable( amount ) )
                        {
                            // update the account involved to reflect withdrawal
                            bankDatabase.debit( getAccountNumber(), amount );

                            cashDispenser.dispenseCash( amount ); // dispense cash
                            cashDispensed = true; // cash was dispensed

                        
                        

                            // instruct user to take cash
                            screen.restTextBox();
                            screen.displayMessageLine("Cash dispensing..." );
                            screen.displayMessageLine("Cash dispensed");
                            screen.displayMessageLine("Please take your cash.");
                            screen.displayMessageLine("Press enter to back to the main menu.");
                             
                        } // end if
                        else{ // cash dispenser does not have enough cash
                            screen.restTextBox();
                            screen.displayMessageLine(
                                    "\nInsufficient cash available in the ATM." +
                                            "\n\nPlease choose a smaller amount." );
                        }
                    } // end if
                    else // not enough money available in user's account
                    {
                        screen.restTextBox();
                        screen.displayMessageLine(
                                "\nInsufficient funds in your account." +
                                        "\n\nPlease choose a smaller amount." );
                    } // end else
                }
                else if(confirm == 2)
                {
                screen.displayMessageLine( "\nCanceling transaction..." );
                screen.displayMessageLine("Press enter to continue....");
                return; // return to main menu because user canceled
                }
                else
                screen.displayMessageLine( "\nWrong selection! Please press Enter to back to the main menu." );
                return;// return to main menu because of wrong selection
            } // end if
            else // user chose cancel menu option
            {
                screen.displayMessageLine( "\nCanceling transaction...Press Enter return to main menu." );
                return; // return to main menu because user canceled
            } // end else
        } while ( !cashDispensed );

    } // end method execute

    // display a menu of withdrawal amounts and the option to cancel;
    // return the chosen amount or 0 if the user chooses to cancel
    private int displayMenuOfAmounts()
    {
        int userChoice = -1; // local variable to store return value


        BankDatabase bankDatabase = getBankDatabase();
        bankDatabase.getAvailableBalance( getAccountNumber() );

        Screen screen = getScreen(); // get screen reference

        // array of amounts to correspond to menu numbers
        int amounts[] = { 0, 200, 400, 600, 1000, 2000 };

        // loop while no valid choice has been made
        while ( userChoice == -1 )
        {
            // display the menu
            screen.displayMessageLine( "\nWithdrawal Menu:" );
            screen.displayMessage("\nYour current available balance: ");
            screen.displayMessage(Double.toString(bankDatabase.getAvailableBalance(super.getAccountNumber())));
            screen.displayMessageLine( "\n\n1 - $200 " );
            screen.displayMessageLine( "2 - $400 " );
            screen.displayMessageLine( "3 - $600 " );
            screen.displayMessageLine( "4 - $1000 " );
            screen.displayMessageLine( "5 - $2000 " );
            screen.displayMessageLine( "0 - Cancel transaction" );
            screen.displayMessage( "\nType your withdraw amount");
            screen.displayMessage(" \nOR"); 
            screen.displayMessage(" \nChoose a preset amount(1-5): " );
            
            
            keypad.userInput();
            screen.displayMessage(keypad.getstr_input());
            int input = keypad.getInput(); 

           
            switch ( input )
            {
                case 1: 
                case 2:
                case 3:
                case 4:
                case 5:
                
                    userChoice = amounts[ input ]; 
                    break;
                case CANCELED: 
                    userChoice = CANCELED; 
                    break;
                default: 
                    if (input <= bankDatabase.getAvailableBalance( getAccountNumber() ) && input%100==0)
                    {
                        userChoice=input;
                    }
                    else if (input > bankDatabase.getAvailableBalance( getAccountNumber() ))
                    {
                        screen.restTextBox();
                        screen.displayMessageLine(
                                "\nNot enough balance !" );
                        continue;
                    }
                    else
                    {
                        screen.restTextBox();
                        screen.displayMessageLine(
                                "\n Please input the amount of multiple of 100. Try again." );
                        continue;
                    }
            } // end switch
        } // end while

        return userChoice; // return withdrawal amount or CANCELED
    } // end method displayMenuOfAmounts
} // end class Withdrawal
