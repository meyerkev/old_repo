
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 A simplified calculator. 
 The only operations are addition and subtraction.
*/
public class Calculator extends JFrame 
                        implements ActionListener
{
    public static final int WIDTH = 300;
    public static final int HEIGHT = 100;
    public static final int NUMBER_OF_DIGITS = 20;
    
    public int numberOfDecks = 3; 
    private JTextField ioField; 
    private int count = 0;
    private int aces = numberOfDecks *4;

    public static void main(String[] args)
    {
        Calculator aCalculator = new Calculator( );
        aCalculator.setVisible(true);
    }

    public Calculator( )
    {
        setTitle("Card Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout( ));

        JPanel textPanel = new JPanel( );
        textPanel.setLayout(new FlowLayout( ));
        ioField = 
             new JTextField("Count = " + Double.toString(count) + " Aces = "+ aces , NUMBER_OF_DIGITS);
        ioField.setBackground(Color.WHITE);
        textPanel.add(ioField);
        add(textPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel( );
        buttonPanel.setBackground(Color.BLUE);
        buttonPanel.setLayout(new FlowLayout( )); 

        JButton addButton = new JButton("2 thru 7"); 
        addButton.addActionListener(this);
        buttonPanel.add(addButton); 
        JButton subtractButton = new JButton("8 thru K"); 
        subtractButton.addActionListener(this);
        buttonPanel.add(subtractButton); 
        JButton AceButton = new JButton("A"); 
        AceButton.addActionListener(this);
        buttonPanel.add(AceButton);
        JButton resetButton = new JButton("Reset"); 
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

       add(buttonPanel, BorderLayout.CENTER);
    }


    public void actionPerformed(ActionEvent e)
    {
        try
        {
            assumingCorrectNumberFormats(e);
        }
        catch (NumberFormatException e2)
        {
            ioField.setText("Error: Reenter Number.");
        }
    }


    //Throws NumberFormatException.
    public void assumingCorrectNumberFormats(ActionEvent e)
    {
        String actionCommand = e.getActionCommand( );

        if (actionCommand.equals("2 thru 7"))
        {
            count += 1;
            ioField.setText("Count = " + Double.toString(count) + " Aces = "+ aces );
        }
        else if (actionCommand.equals("8 thru K"))
        {
            count -=1;
            ioField.setText("Count = " + Double.toString(count) + " Aces = "+ aces );

        }
        else if (actionCommand.equals("Reset"))
        {
            count = 0;
            aces = numberOfDecks *4;
            ioField.setText("Count = " + Double.toString(count) + " Aces = "+ aces );            
        }
        else if (actionCommand.equals("A"))
        {
            aces -=1;
            ioField.setText("Count = " + Double.toString(count) + " Aces = "+ aces );
        }
        else
            ioField.setText("Unexpected error.");
    }

}
