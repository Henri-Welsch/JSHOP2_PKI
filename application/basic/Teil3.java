import java.awt.*;  
import javax.swing.*;  

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


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
        Map<String, Map<String, String>> data = readDate();
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
        JLabel truck_Label = new JLabel("Anzahl Trucks " + data.get("TRUCK").size());
        truck_Label.setBounds(10,70,150,25);
        panel.add(truck_Label);

        JButton truck_Button = new JButton("Öffne Manager");
        truck_Button.setBounds(170,70,150,25);
        panel.add(truck_Button);

        truck_Button.addActionListener(e -> {
            getManager(data.get("TRUCK"), "TRUCK");
            System.out.println("Open truck manager");
            // TODO 
        });
        // * Ende Stand des Kilometerzählers 


        // * Start Stand des Stundenkontos. 
        JLabel driver_Label = new JLabel("Anzahl Fahrer " + data.get("TRUCK-LICENSE").size());
        driver_Label.setBounds(10,100,150,25);
        panel.add(driver_Label);

        JButton driver_Button = new JButton("Öffne Manager");
        driver_Button.setBounds(170,100,150,25);
        panel.add(driver_Button);

        driver_Button.addActionListener(e -> {
            getManager(data.get("TRUCK-LICENSE"),"TRUCK-LICENSE");
            System.out.println("Open driver manager");
            // TODO 
        });
        // * Ende Stand des Stundenkontos. 


        // * Start Hallen
        JLabel hall_Label = new JLabel("Anzahl Hallen " + data.get("IN-CITY").size());
        hall_Label.setBounds(10,130,150,25);
        panel.add(hall_Label);

        JButton hall_Button = new JButton("Öffne Manager");
        hall_Button.setBounds(170,130,150,25);
        panel.add(hall_Button);

        hall_Button.addActionListener(e -> {
            getManager(data.get("IN-CITY"), "IN-CITY");
            System.out.println("Open hall manager");
            // TODO 
        });
        // * Ende Hallen 



        // * Start Input Button
        JButton start_Button = new JButton("Ausführen");
        start_Button.setBounds(10, 185, 150, 25);
        panel.add(start_Button);

        start_Button.addActionListener(e -> {
            System.out.println("Start");
            // TODO 
        });


        JButton cancel_Button = new JButton("Abbruch");
        cancel_Button.setBounds(170, 185, 150, 25);
        panel.add(cancel_Button);

        cancel_Button.addActionListener(e -> {
            System.out.println("Cancel");
            // TODO 
        });
        // * Ende Input Button
    }

    private static void getManager(Map<String, String> data, String manager) {
        AtomicInteger nrCounter = new AtomicInteger(1);
        JFrame newFrame = new JFrame(manager);

        
        JPanel vPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        vPanel.setPreferredSize(new Dimension(1,data.size()*20));
        JScrollPane scrollPane = new JScrollPane(vPanel);





        data.entrySet().stream().forEach(str -> {
            JPanel hPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            hPanel.add(Box.createRigidArea(new Dimension(2, 0)));


            JLabel x = new JLabel("Nr: " + nrCounter.getAndIncrement()); 
            x.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            x.setPreferredSize(new Dimension(40, 20));


            JLabel a = new JLabel(str.getKey()); 
            a.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            a.setPreferredSize(new Dimension(90, 20));

            JTextField b = new JTextField(str.getValue());
            b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            b.setPreferredSize(new Dimension(100, 20));

            JButton c = new JButton("Löschen");
            c.setPreferredSize(new Dimension(90, 20));
            hPanel.add(Box.createRigidArea(new Dimension(2, 0)));

            c.addActionListener(e -> {
                vPanel.remove(hPanel);
                vPanel.revalidate();
                vPanel.repaint();
                data.remove(str.getKey());
                vPanel.setPreferredSize(new Dimension(1,data.size()*20));
                // TODO call Writer
            });
            
            hPanel.add(x);
            hPanel.add(a);
            hPanel.add(b);
            hPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            hPanel.add(c);
            vPanel.add(hPanel);
        });

        System.out.println(data.size()*20);
        newFrame.add(scrollPane);
        newFrame.setSize(350, 270);
        newFrame.setVisible(true);
    }

    
    private static Map<String, Map<String, String>> readDate() {
        Map<String, Map<String, String>> data = new HashMap<>();

        try (FileReader fileReader = new FileReader("application/basic/problem")) {
            BufferedReader reader = new BufferedReader(fileReader);

            reader.lines().filter(str -> !str.isBlank()).map(String::trim)
                .map(str -> str.replace("(", "")).map(str -> str.replace(")", ""))
                .map(str -> str.split(" ")).forEach(str -> {
                        try {
                            if (data.get(str[0]) == null) 
                                data.put(str[0], new HashMap<>());
                            if (data.get(str[0]).get(str[1]) == null)
                                data.get(str[0]).put(str[1], str[2]);
                        } catch (Exception e) {}
                    });
        } catch (Exception e) { System.out.println(e); }
        // data.entrySet().stream().forEach(x -> x.getValue().entrySet().stream().forEach(y -> System.out.println(x.getKey() + " " + y.getKey() + " " + y.getValue())));
        return data;
    }
}