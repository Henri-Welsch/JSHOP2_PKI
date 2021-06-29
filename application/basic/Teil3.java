import java.awt.*;  
import javax.swing.*;

import java.util.function.Function;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


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
        Map<String, Map<String, String>> entries = readDate();
        panel.setLayout(null);

        
        //* Start Lieferaufträge
        JLabel deliveryOrder_Label = new JLabel("Aktuelle Lieferaufträge:");
        deliveryOrder_Label.setBounds(10,20,150,25);
        panel.add(deliveryOrder_Label);

        JTextField deliveryOrder_Text = new JTextField(20);
        deliveryOrder_Text.setBounds(170,20,150,25);
        panel.add(deliveryOrder_Text);
        // TODO noch nicht klar -> Lese Aufgabenstellung
        // * Ende Lieferaufträge



        // * Start Stand des Kilometerzählers 
        JLabel truck_Label = new JLabel("Anzahl Trucks " + entries.get("TRUCK").size());
        truck_Label.setBounds(10,70,150,25);
        panel.add(truck_Label);

        JButton truck_Button = new JButton("Öffne Manager");
        truck_Button.setBounds(170,70,150,25);
        panel.add(truck_Button);

        truck_Button.addActionListener(e -> {
            getManager(entries.get("TRUCK"), "TRUCK");
            System.out.println("Open truck manager");
        });
        // * Ende Stand des Kilometerzählers 


        // * Start Stand des Stundenkontos. 
        JLabel driver_Label = new JLabel("Anzahl Fahrer " + entries.get("TRUCK-LICENSE").size());
        driver_Label.setBounds(10,100,150,25);
        panel.add(driver_Label);

        JButton driver_Button = new JButton("Öffne Manager");
        driver_Button.setBounds(170,100,150,25);
        panel.add(driver_Button);

        driver_Button.addActionListener(e -> {
            getManager(entries.get("TRUCK-LICENSE"),"TRUCK-LICENSE");
            System.out.println("Open driver manager");
        });
        // * Ende Stand des Stundenkontos. 


        // * Start Hallen
        JLabel hall_Label = new JLabel("Anzahl Hallen " + entries.get("IN-CITY").size());
        hall_Label.setBounds(10,130,150,25);
        panel.add(hall_Label);

        JButton hall_Button = new JButton("Öffne Manager");
        hall_Button.setBounds(170,130,150,25);
        panel.add(hall_Button);

        hall_Button.addActionListener(e -> {
            getManager(entries.get("IN-CITY"), "IN-CITY");
            System.out.println("Open hall manager");
        });
        // * Ende Hallen 



        // * Start Input Button
        JButton start_Button = new JButton("Ausführen");
        start_Button.setBounds(10, 185, 150, 25);
        panel.add(start_Button);

        start_Button.addActionListener(e -> {
            System.out.println("Start");
            // TODO Aufrufen von Basic
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



    private static void getManager(Map<String, String> entries, String manager) {
        JFrame newFrame = new JFrame(manager);


        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
        mainPanel.setPreferredSize(new Dimension(1,(entries.size()*20)));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        

        Function <Map.Entry<String, String>, JPanel> foo = new Function<Map.Entry<String, String>, JPanel>(){
            private int counter = 1;

            @Override
            public JPanel apply(Entry<String, String> t) {
                JPanel subPanel = getSubPanel(t, counter++);
                JButton button = (JButton)subPanel.getComponent(5);

                button.addActionListener(e -> {                                         // Set remove event listener
                    counter--;                                                          // decrease count of elements
                    entries.remove(((JTextField)subPanel.getComponent(2)).getText());   // Remove from Map
                    mainPanel.remove(subPanel);                                         // Remove from Panel
                    mainPanel.revalidate();                                             // Redo Panel
                    mainPanel.repaint();                                                // Redo Panel
                    mainPanel.setPreferredSize(new Dimension(1,counter*20));            // Grow with ScrollPane
                });
                return subPanel;
            }
        };


        entries.entrySet().stream().map(foo::apply).forEach(mainPanel::add);


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        
        JButton hinzufuegen = new JButton("Eintrag hinzufügen");
        hinzufuegen.setPreferredSize(new Dimension(165, 26));
        hinzufuegen.addActionListener(lamdbda -> {
            System.out.println("Eintrag hinzufügen");
            mainPanel.add(foo.apply(null));                             // Append new empty entry
            mainPanel.revalidate();                                     // Redo Panel
            mainPanel.repaint();                                        // Redo Panel

            int count = mainPanel.getComponentCount() *20;              // Grow with ScrollPane
            mainPanel.setPreferredSize(new Dimension(1,count));         // Grow with ScrollPane
            // TODO Cant Save Entry or changes
        });


        JButton speichern = new JButton("Speichern");
        speichern.setPreferredSize(new Dimension(165, 26));
        speichern.addActionListener(lambda -> {
            System.out.println("Speichern");
            // TODO Speichern von elementen in der Map
            // TODO Speichern von elementen in der Problem Datei
        });
        

        topPanel.add(speichern);
        topPanel.add(hinzufuegen);

        newFrame.add(topPanel, BorderLayout.NORTH);
    
        
        newFrame.add(scrollPane, BorderLayout.CENTER);
        newFrame.setSize(350, 270);
        newFrame.setVisible(true);
    }

    
    private static Map<String, Map<String, String>> readDate() {
        Map<String, Map<String, String>> entries = new HashMap<>();

        try (FileReader fileReader = new FileReader("application/basic/problem")) {
            BufferedReader reader = new BufferedReader(fileReader);

            reader.lines().filter(str -> !str.isBlank()).map(String::trim)
                .map(str -> str.replace("(", "")).map(str -> str.replace(")", ""))
                .map(str -> str.split(" ")).forEach(str -> {
                        try {
                            if (entries.get(str[0]) == null) 
                                entries.put(str[0], new HashMap<>());
                            if (entries.get(str[0]).get(str[1]) == null)
                                entries.get(str[0]).put(str[1], str[2]);
                        } catch (Exception e) {}
                    });
        } catch (Exception e) { System.out.println(e); }
        return entries;
    }



    private static JPanel getSubPanel(Map.Entry<String, String> entry, Integer counter) {
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        subPanel.add(Box.createRigidArea(new Dimension(4, 0)));

        String key = (entry == null)? "" : entry.getKey();
        String value = (entry == null)? "" : entry.getValue();


        JLabel entryNr = new JLabel("Nr: " + counter); 
        entryNr.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        entryNr.setPreferredSize(new Dimension(40, 20));
        subPanel.add(entryNr);

        JTextField entryKey = new JTextField(key); 
        entryKey.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        entryKey.setPreferredSize(new Dimension(90, 20));
        subPanel.add(entryKey);

        JTextField entryValue = new JTextField(value);
        entryValue.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        entryValue.setPreferredSize(new Dimension(100, 20));
        subPanel.add(entryValue);

        subPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton delButton = new JButton("Löschen");
        delButton.setPreferredSize(new Dimension(95, 20));
        subPanel.add(delButton);

        return subPanel;
    }
}