import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField; 
public class BenutzerEingabe {
    
    public static void main(String[] args) {    
        // Creating instance of JFrame
        JFrame frame = new JFrame("Eingabe");
        // Setting the width and height of frame
        frame.setSize(350, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Creating panel. This is same as a div tag in HTML
         * We can create several panels and add them to specific 
         * positions in a JFrame. Inside panels we can add text 
         * fields, buttons and other components.
         */
        JPanel panel = new JPanel();    
        // adding panel to frame
        frame.add(panel);
        /* calling user defined method for adding components
         * to the panel.
         */
        placeComponents(panel);

        // Setting the frame visibility to true
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        
        //* Start Lieferaufträge
        JLabel deliveryOrder_Label = new JLabel("Aktuelle Lieferaufträge:");
        deliveryOrder_Label.setBounds(10,20,150,25);
        panel.add(deliveryOrder_Label);

        JTextField deliveryOrder_Text = new JTextField(20);
        deliveryOrder_Text.setBounds(170,20,165,25);
        panel.add(deliveryOrder_Text);
        // * Ende Lieferaufträge



        // * Start Stand des Kilometerzählers 
        JLabel truck1_Label = new JLabel("KM Anzahl von Truck 1:");
        truck1_Label.setBounds(10,50,150,25);
        panel.add(truck1_Label);

        JTextField truck1_Text = new JTextField(20);
        truck1_Text.setBounds(170,50,165,25);
        panel.add(truck1_Text);


        JLabel truck2_Label = new JLabel("KM Anzahl von Truck 2:");
        truck2_Label.setBounds(10,75,150,25);
        panel.add(truck2_Label);

        JTextField truck2_Text = new JTextField(20);
        truck2_Text.setBounds(170,75,165,25);
        panel.add(truck2_Text);
        // * Ende Stand des Kilometerzählers 



        // * Start Stand des Stundenkontos. 
        JLabel meyer_Label = new JLabel("Meyer Stundenkonto:");
        meyer_Label.setBounds(10,105,150,25);
        panel.add(meyer_Label);

        JTextField meyer_Text = new JTextField(20);
        meyer_Text.setBounds(170,105,165,25);
        panel.add(meyer_Text);


        JLabel berger_Label = new JLabel("Berger Stundenkonto:");
        berger_Label.setBounds(10,130,150,25);
        panel.add(berger_Label);

        JTextField berger_Text = new JTextField(20);
        berger_Text.setBounds(170,130,165,25);
        panel.add(berger_Text);


        JLabel schmitz_Label = new JLabel("Schmitz Stundenkonto:");
        schmitz_Label.setBounds(10,155,150,25);
        panel.add(schmitz_Label);

        JTextField schmitz_Text = new JTextField(20);
        schmitz_Text.setBounds(170,155,165,25);
        panel.add(schmitz_Text);
        // * Ende Stand des Stundenkontos. 


        // * Start Input Button
        JButton start_Button = new JButton("Start");
        start_Button.setBounds(10, 185, 100, 25);
        panel.add(start_Button);

        JButton cancel_Button = new JButton("Abbruch");
        cancel_Button.setBounds(130, 185, 100, 25);
        panel.add(cancel_Button);
        // * Ende Input Button

    }

}