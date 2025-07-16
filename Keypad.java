// Keypad.java
// Represents the keypad of the ATM
import java.util.Scanner; // program uses Scanner to obtain user input
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;


public class Keypad
{
    private String str_input; // reads data from the command line
    private JPanel panel;//input panel
    private JPanel keyPanel;//KeyPanel
    private JButton[] buttons;//Buttons
    private JTextField userInput;//Textfield
    private JPasswordField password;//password field
    private String int_input;//String of user input
    private String double_input;//String of user input
    private int Int_input;//intger of user input
    private double Double_input;//Double of user input
    private boolean enter = false;//enter flag
    private boolean inputNumberState;//The state of inputting number
    private boolean inputPasswordState = false;//The state of inputting password

    // no-argument constructor initializes the Scanner
    public Keypad()
    {
        // initialize GUI 
        buttons = new JButton[16];
        panel = new JPanel();
        keyPanel = new JPanel();
        panel.setLayout(new BorderLayout());
        keyPanel.setLayout(new GridLayout(4,4));

        buttons[0] = new JButton("1");
        buttons[1] = new JButton("2");
        buttons[2] = new JButton("3");
        buttons[3] = new JButton("Cancel");
        buttons[4] = new JButton("4");
        buttons[5] = new JButton("5");
        buttons[6] = new JButton("6");
        buttons[7] = new JButton("Clear");
        buttons[8] = new JButton("7");
        buttons[9] = new JButton("8");
        buttons[10] = new JButton("9");
        buttons[11] = new JButton("Enter");
        buttons[12] = new JButton(".");
        buttons[13] = new JButton("0");
        buttons[14] = new JButton("00");
        buttons[15] = new JButton(" ");

        buttons[3].setBackground(Color.RED);
        buttons[7].setBackground(Color.YELLOW);
        buttons[11].setBackground(Color.GREEN);

        userInput = new JTextField(20);
        userInput.setEditable(false);
        password = new JPasswordField(20);
        password.setEditable(false);
        panel.add(userInput,BorderLayout.NORTH);

        TextFieldHandler text_handler = new TextFieldHandler();
        userInput.addActionListener(text_handler);

        for(int i = 0; i < 16; i++)
        {
            keyPanel.add(buttons[i]);
        }

        ButtonHandler button_handler = new ButtonHandler();
        for(int i = 0; i < 16; i++)
        {
            buttons[i].addActionListener(button_handler);
        }

        panel.add(keyPanel, BorderLayout.CENTER);
    } 

    public void userInput()
    {
        panel.add(userInput,BorderLayout.NORTH);
        userInput.setText("");
        inputNumberState = true;
        enterFlag();

        enter = false;
    }


    public void passwordInfo()
    {
        inputPasswordState = true;
        panel.remove(userInput);
        panel.add(password,BorderLayout.NORTH);

        inputNumberState = true;
        enterFlag();

        enter = false;
    }

    public void removeUserinput()
    {
        panel.remove(userInput);
    }
    
    public void removePasswordField()
    {
        panel.remove(password);
    }

    public void inputPasswordState(boolean i)
    {
        if(i == false)
            inputPasswordState = false;
        else if(i == true)
            inputPasswordState = true;
    }

    public void inputNumberState(boolean i)
    {
        if(i == false)
            inputNumberState = false;
        else if(i == true)
            inputNumberState = true;
    }


    public void enterFlag()
    {
        while(enter == false)
        {
            System.out.print("");
        }
    }

    public void enterFlag(boolean i)
    {
        if(i == false)
            enter = false;
        else if(i == true)
            enter = true;
    }

    public boolean enterFlagReturn()
    {
        return enter;
    }

    public JPanel getPanel()//return the panel
    {
        return panel;
    }

    public String getstr_input()//return the string of the input
    {
        return str_input;
    }

    // return an integer value entered by user
    public int getInput()//return the integer of the input
    {
        return Int_input; // we assume that user enters an integer
    } // end method getInput

    public double getDouble()//return the double of the input
    {
        return Double_input;
    }

//Button handler
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            for(int i = 0; i < 3; i++)
            {
                if(event.getSource() == buttons[i])
                {
                    if(inputPasswordState == true)
                    {
                        password.setText(password.getText().concat(String.valueOf(i + 1)));
                    }
                    else
                        userInput.setText(userInput.getText().concat(String.valueOf(i + 1)));
                }
            }

            if(event.getSource() == buttons[3])
            {
                //set text to 0, then enter automatically
                userInput.setText("0");
                str_input = userInput.getText();
                Int_input = Integer.parseInt(str_input);
                enter = true;
                userInput.setText("");

                inputNumberState = false;


            }

            for(int i = 4; i <= 6; i++)
            {
                if(event.getSource() == buttons[i])//input button 4-6
                {
                    if(inputPasswordState == true)
                    {
                        password.setText(password.getText().concat(String.valueOf(i)));
                    }
                    else
                        userInput.setText(userInput.getText().concat(String.valueOf(i)));
                }
            }

            for(int i = 8; i<= 10; i++)
            {
                if(event.getSource() == buttons[i])//input button 7-9
                {
                    if(inputPasswordState == true)
                    {
                        password.setText(password.getText().concat(String.valueOf(i - 1)));
                    }
                    else
                        userInput.setText(userInput.getText().concat(String.valueOf(i - 1)));
                }
            }

            if(event.getSource() == buttons[11])//enter button
            {
                try{
                    if(inputNumberState == true && inputPasswordState == false)
                    {
                        str_input = userInput.getText();//assign text to str_input
                        if(str_input.contains("."))
                            Double_input = Double.parseDouble(str_input);
                        else
                            Int_input = Integer.parseInt(str_input);
                        enter = true;//User press enter
                        userInput.setText("");

                        inputNumberState = false;//set the state to false
                    }
                    else if (inputNumberState == true && inputPasswordState == true)
                    {
                        str_input = password.getText();
                        Int_input = Integer.parseInt(str_input);
                        enter = true;
                        password.setText("");

                        inputPasswordState = false;//set both password and input number state to false
                        inputNumberState = false;
                    }
                    else
                    {
                        enter = true;
                    }
                }catch(NumberFormatException numberFormatException)
                {
                }
            }

            if(event.getSource() == buttons[13])//"0" button
            {
                if(inputPasswordState == true)
                {
                    password.setText(password.getText() + "0");
                }
                else
                    userInput.setText(userInput.getText() + "0");
            }

            if(event.getSource() == buttons[7])//clear button
            {
                if(inputPasswordState == true)
                {
                    password.setText("");
                }
                else
                    userInput.setText("");
            }

            if(event.getSource() == buttons[12])//"." button
            {
                if(inputPasswordState == true)
                {
                    password.setText(password.getText() + ".");
                }
                else
                    userInput.setText(userInput.getText() + ".");
            }

            if(event.getSource() == buttons[14])//"00" button
            {
                if(inputPasswordState == true)
                {
                    password.setText(password.getText() + "00");
                }
                else
                    userInput.setText(userInput.getText() + "00");
            }

        }
    }


    private class TextFieldHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {

        }
    }
} // end class Keypad
