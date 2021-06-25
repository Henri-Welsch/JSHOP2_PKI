import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class test {
    
    public static void main(String[] args) {    
        JFrame frame = new JFrame("Eingabe");

        frame.setSize(350, 270);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();    
        placeComponents(panel);

        frame.add(panel);
        frame.setVisible(true);
    }


    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        
        //* Start Lieferaufträge
        JLabel deliveryOrder_Label = new JLabel("Aktuelle Lieferaufträge:");
        deliveryOrder_Label.setBounds(10,20,150,25);
        panel.add(deliveryOrder_Label);

        JTextField deliveryOrder_Text = new JTextField(20);
        deliveryOrder_Text.setBounds(170,20,150,25);
        panel.add(deliveryOrder_Text);
        // * Ende Lieferaufträge



        // * Start Stand des Kilometerzählers 
        JLabel truck_Label = new JLabel("Anzahl Trucks XXX ");
        truck_Label.setBounds(10,70,150,25);
        panel.add(truck_Label);

        JButton truck_Button = new JButton("Öffne Manager");
        truck_Button.setBounds(170,70,150,25);
        panel.add(truck_Button);

        truck_Button.addActionListener(e -> {
            getManager("Truck Manager");
            System.out.println("Open truck manager");
        });
        // * Ende Stand des Kilometerzählers 


        // * Start Stand des Stundenkontos. 
        JLabel driver_Label = new JLabel("Anzahl Fahrer XXX");
        driver_Label.setBounds(10,100,150,25);
        panel.add(driver_Label);

        JButton driver_Button = new JButton("Öffne Manager");
        driver_Button.setBounds(170,100,150,25);
        panel.add(driver_Button);

        driver_Button.addActionListener(e -> {
            getManager("Driver Manager");
            System.out.println("Open driver manager");
        });
        // * Ende Stand des Stundenkontos. 


        // * Start Hallen
        JLabel hall_Label = new JLabel("Anzahl Hallen XXX");
        hall_Label.setBounds(10,130,150,25);
        panel.add(hall_Label);

        JButton hall_Button = new JButton("Öffne Manager");
        hall_Button.setBounds(170,130,150,25);
        panel.add(hall_Button);

        hall_Button.addActionListener(e -> {
            getManager("Hall Manager");
            System.out.println("Open hall manager");
        });
        // * Ende Hallen 



        // * Start Input Button
        JButton start_Button = new JButton("Ausführen");
        start_Button.setBounds(10, 185, 150, 25);
        panel.add(start_Button);

        start_Button.addActionListener(e -> {
            System.out.println("Start");
        });


        JButton cancel_Button = new JButton("Abbruch");
        cancel_Button.setBounds(170, 185, 150, 25);
        panel.add(cancel_Button);

        cancel_Button.addActionListener(e -> {
            System.out.println("Cancel");
        });
        // * Ende Input Button


    }

    private static void getManager(String manager) {
        JFrame newFrame = new JFrame(manager);

        JPanel vPanel=new JPanel();
        vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 4; i++) {
            JPanel hPanel=new JPanel();
            hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));
            hPanel.add(new JButton("This"));
            hPanel.add(new JButton("is"));
            hPanel.add(new JButton("a"));
            hPanel.add(new JButton("test"));
            vPanel.add(hPanel);
        }

        newFrame.add(vPanel);
        newFrame.setSize(350, 270);
        //newFrame.pack();          // Important for later
        newFrame.setVisible(true);
    }
}