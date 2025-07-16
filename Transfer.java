public class Transfer extends Transaction
{
    private Keypad keypad;
    private double amount_transfer;
    private int transfer_account;
    private final static int CANCELED = 0;

    public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,Keypad atmKeypad )
    {
        
        super( userAccountNumber, atmScreen, atmBankDatabase );
        keypad = atmKeypad; 
    }

    @Override
    public void execute()
    {
        double available_balance;
        BankDatabase bankDatabase = getBankDatabase(); 
        Screen screen = getScreen(); 

        screen.displayMessageLine("Please enter the payee account number:  (Press Cancel return to main menu.)");

        do
        {
            
            keypad.userInput();
            screen.displayMessage("The payee account number:" + keypad.getstr_input());
            transfer_account = keypad.getInput();
            
            if( transfer_account == CANCELED)
            {
            screen.restTextBox();
            screen.displayMessageLine("You have exited. Please press Enter return to main menu.");
            return;
            }

            if(super.getAccountNumber() == transfer_account){
                screen.displayMessageLine("\nSorry. You cannot transfer money to your own account.");
                screen.displayMessageLine("Please enter another account number: ");
                continue;
            }
            
            if(bankDatabase.account_matching(transfer_account) != -1)
            {
                
                screen.displayMessageLine("\nHow much do you want to transfer? Press Cancel return to main menu." );
                screen.displayMessage("\nYour current available balance: ");
                screen.displayMessage(Double.toString(bankDatabase.getAvailableBalance(super.getAccountNumber())));
                do{
                
                keypad.userInput();
                screen.restTextBox();

                if(keypad.getstr_input().contains("."))
                {
                    amount_transfer = keypad.getDouble();
                }
                else
                    amount_transfer = keypad.getInput();


                if(amount_transfer != CANCELED)
                {
                    screen.displayMessageLine("Are you sure you want to transfer: $" + keypad.getstr_input() + "?" );
                    screen.displayMessageLine("\n  1 - confirm         2 - cancel");//confirmation

                   keypad.userInput();
                   int confirm = keypad.getInput();

                    available_balance = bankDatabase.getAvailableBalance(super.getAccountNumber());//assign the available balance of user's
                    
                    if(confirm == 1)
                    {

                        if(amount_transfer <= available_balance)//if user has enough amount of available_balance to transfer
                        {
                                
                                bankDatabase.debit(super.getAccountNumber(), amount_transfer );
                                bankDatabase.credit(transfer_account,amount_transfer);
                                screen.displayMessageLine("Transaction successed!");
                                screen.displayMessageLine("Please press Enter return to main menu.");
                                keypad.enterFlag(false);
                                keypad.enterFlag();
                                if(keypad.enterFlagReturn() == true)
                                    amount_transfer = 0;
                        }

                        else
                        {
                            keypad.enterFlag(false);
                            screen.restTextBox();
                            screen.displayMessageLine("Sorry! Your available balance is insufficient.");
                            screen.displayMessageLine("Please enter the amount again or enter 0/cancel to return to main menu.");
                            continue;
                            //User dont have enough money to transfer
                        }
                    }
                    else if(confirm == 2)
                    {
                        screen.displayMessageLine("You have exited. Press Enter return to main menu.");
                        return;
                    }
                    else
                    screen.displayMessageLine( "\nWrong selection! Please press Enter to back to the main menu." );
                    return;// return to main menu because of wrong selection
                }
                else
                {
                    screen.displayMessageLine("You have exited. Press Enter return to main menu.");
                    

                    return; 
                }
                }while((amount_transfer > bankDatabase.getAvailableBalance(super.getAccountNumber())));

            }
            else
            {
                screen.displayMessageLine("\nSorry! Could not found this account, please re-enter. (Input 0  return to main menu.)");
                continue;
            }

        }while((super.getAccountNumber() == transfer_account) || (bankDatabase.account_matching(transfer_account) == -1));

    }
}
