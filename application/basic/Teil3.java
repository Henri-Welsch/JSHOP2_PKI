import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Teil3 {
    
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
            getManager("TRUCK");
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
            getManager("IN-CITY");
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
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger nrCounter = new AtomicInteger(1);
        JFrame newFrame = new JFrame(manager);

        JPanel vPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        

        try (FileReader fileReader = new FileReader("application/basic/problem")) {
            BufferedReader reader = new BufferedReader(fileReader);

            reader.lines().filter(str -> !str.isBlank()).map(String::trim)
                .map(str -> str.replace("(", "")).map(str -> str.replace(")", ""))
                .map(str -> str.split(" ")).filter(str -> str[0].equals(manager))
                .forEach(str -> {
                    JPanel hPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    hPanel.add(Box.createRigidArea(new Dimension(2, 0)));
                    
                    JLabel x = new JLabel("Nr: " + nrCounter.getAndIncrement()); 
                    x.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    x.setPreferredSize(new Dimension(40, 20));


                    JLabel a = new JLabel(str[1]); 
                    a.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    a.setPreferredSize(new Dimension(90, 20));

                    JTextField b = new JTextField(str[2]);
                    b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    b.setPreferredSize(new Dimension(100, 20));

                    JButton c = new JButton("Löschen");
                    c.setPreferredSize(new Dimension(90, 20));
                    hPanel.add(Box.createRigidArea(new Dimension(2, 0)));
                    counter.set(counter.get()+20);
                    
                    hPanel.add(x);
                    hPanel.add(a);
                    hPanel.add(b);
                    hPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    hPanel.add(c);
                    vPanel.add(hPanel);
                });
            
        } catch (Exception e) {System.out.println(e.getMessage());}
        
        newFrame.add(vPanel);
        newFrame.setSize(350, 270);
        //newFrame.pack();          // Important for later
        newFrame.setVisible(true);
    }
}