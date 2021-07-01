import java.awt.*;
import javax.swing.*;
import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Teil3 {
    public static void main(String[] args) {    
        JFrame frame = new JFrame("Eingabe");
        

        frame.setSize(350, 285);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();    
        placeComponents(panel);

        frame.add(panel);
        frame.setVisible(true);
    }


    private static void placeComponents(JPanel panel) {
        Map<String, Map<JTextField, JTextField>> entries = readDate();
        panel.setLayout(null);

        
        //* Start Lieferaufträge
        JLabel deliveryOrder_Label = new JLabel("Aktuelle Lieferaufträge:");
        deliveryOrder_Label.setBounds(10,20,150,25);
        panel.add(deliveryOrder_Label);

        JButton deliveryOrder_Button = new JButton("Öffne Manager");
        deliveryOrder_Button.setBounds(170,20,150,25);
        panel.add(deliveryOrder_Button);

        deliveryOrder_Button.addActionListener(e -> {
            getManager(entries.get("PARCEL-AT"), "PARCEL-AT");
            System.out.println("Open lieferaufträge manager");
        });
        // TODO noch nicht klar -> Lese Aufgabenstellung
        // * Ende Lieferaufträge



        // * Start Stand des Kilometerzählers 
        JLabel truck_Label = new JLabel("Anzahl Trucks " + entries.get("TRUCK").size());
        // TODO counter not working
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
        // TODO counter not working
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
        // TODO counter not working
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
        JButton save_Button = new JButton("Speichern");
        save_Button.setBounds(10, 185, 150, 25);
        panel.add(save_Button);

        save_Button.addActionListener(e -> {
            System.out.println("Speichern");
            writeData(entries);
        });


        JButton cancel_Button = new JButton("Verwerfen");
        cancel_Button.setBounds(170, 185, 150, 25);
        panel.add(cancel_Button);

        cancel_Button.addActionListener(e -> {
            System.out.println("Verwerfen");
            // TODO Verwerfen der Änderungen 
        });

        // * Start Input Button
        JButton start_Button = new JButton("Ausführen");
        start_Button.setBounds(10, 215, 150, 25);
        panel.add(start_Button);

        start_Button.addActionListener(e -> {
            System.out.println("Ausführen");
            // TODO Aufrufen von Basic
        });


        JButton stop_Button = new JButton("Abbrechen");
        stop_Button.setBounds(170, 215, 150, 25);
        panel.add(stop_Button);

        stop_Button.addActionListener(e -> {
            System.out.println("Abbrechen");
            // TODO Abbrechen von Basic
        });
        // * Ende Input Button
    }



    private static void getManager(Map<JTextField, JTextField> entries, String manager) {
        JFrame newFrame = new JFrame(manager);


        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
        mainPanel.setPreferredSize(new Dimension(1,(entries.size()*20)));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        

        Function <Map.Entry<JTextField, JTextField>, JPanel> foo = new Function<Map.Entry<JTextField, JTextField>, JPanel>() {
            private int counter = 1;

            @Override
            public JPanel apply(Entry<JTextField, JTextField> t) {
                JPanel subPanel = getSubPanel(t, counter++);
                JButton button = (JButton)subPanel.getComponent(5);

                button.addActionListener(e -> {                                         // Set remove event listener
                    counter--;                                                          // decrease count of elements
                    entries.remove((subPanel.getComponent(2)));                         // Remove from Map
                    mainPanel.remove(subPanel);                                         // Remove from Panel
                    mainPanel.revalidate();                                             // Redo Panel
                    mainPanel.repaint();                                                // Redo Panel
                    System.out.println(counter*20);
                    mainPanel.setPreferredSize(new Dimension(1,counter*20));            // Grow with ScrollPane
                });
                return subPanel;
            }
        };


        entries.entrySet().stream().map(foo::apply).forEach(mainPanel::add);


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        
        JButton hinzufuegen = new JButton("Eintrag hinzufügen");
        hinzufuegen.setPreferredSize(new Dimension(330, 26));
        hinzufuegen.addActionListener(lamdbda -> {
            System.out.println("Eintrag hinzufügen");
            JPanel newEntry = foo.apply(null);
            JTextField key = (JTextField) newEntry.getComponent(2);
            JTextField value = (JTextField) newEntry.getComponent(3);
            
            entries.put(key, value);
            mainPanel.add(newEntry);                                    // Append new empty entry
            mainPanel.revalidate();                                     // Redo Panel
            mainPanel.repaint();                                        // Redo Panel

            int count = mainPanel.getComponentCount() *20;              // Grow with ScrollPane
            mainPanel.setPreferredSize(new Dimension(1,count));         // Grow with ScrollPane
        });

        
        topPanel.add(hinzufuegen);

        newFrame.add(topPanel, BorderLayout.NORTH);
        newFrame.add(scrollPane, BorderLayout.CENTER);

        newFrame.setSize(350, 285);
        newFrame.setVisible(true);
    }

    private static JPanel getSubPanel(Map.Entry<JTextField, JTextField> entry, Integer counter) {
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        subPanel.add(Box.createRigidArea(new Dimension(4, 0)));

        JTextField key = (entry == null)? null : entry.getKey();
        JTextField value = (entry == null)? null : entry.getValue();


        JLabel entryNr = new JLabel("Nr: " + counter); 
        entryNr.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        entryNr.setPreferredSize(new Dimension(40, 20));
        subPanel.add(entryNr);

        JTextField entryKey = (key == null)? new JTextField() : key;
        entryKey.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        entryKey.setPreferredSize(new Dimension(90, 20));
        subPanel.add(entryKey);

        JTextField entryValue = (value == null)? new JTextField() : value;
        entryValue.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        entryValue.setPreferredSize(new Dimension(100, 20));
        subPanel.add(entryValue);

        subPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton delButton = new JButton("Löschen");
        delButton.setPreferredSize(new Dimension(95, 20));
        subPanel.add(delButton);

        return subPanel;
    }

    private static Map<String, Map<JTextField, JTextField>> readDate() {
        Map<String, Map<JTextField, JTextField>> entries = new HashMap<>();

        try (FileReader fileReader = new FileReader("application/basic/problem")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            /********* Funktional Interface -> Consumer *********/
            Consumer<String> line = str -> {
                String arr[] = str.replace("(", "").replace(")", "").trim().split(" ");
                if (arr.length < 3) return;
                if (entries.get(arr[0]) == null) 
                    entries.put(arr[0], new HashMap<>());
                entries.get(arr[0]).put(new JTextField(arr[1]), new JTextField(arr[2]));
            };
            
            /********* Main Stream that goes over the data *********/
            bufferedReader.lines().forEach(line::accept);
        } catch (Exception e) { System.out.println(e); }

        return entries;
    }


    private static void writeData(Map<String, Map<JTextField, JTextField>> entries)  {
        try (FileWriter fileWriter = new FileWriter("application/basic/test.txt")) {

            Function<Map.Entry<JTextField, JTextField>, String> line = str -> {
                return " " + str.getKey().getText() + " " + str.getValue().getText() + ")";
            };

            Function<Map.Entry<String, Map<JTextField, JTextField>>, String> format = str -> {
                return str.getValue().entrySet().stream().map(line::apply)
                    .map(("\t\t(" + str.getKey())::concat).collect(Collectors.joining("\n"));
            };

            Consumer<String> write = curLine -> {
                try { fileWriter.write("\n\n" + curLine);
                } catch (IOException e) { System.out.println(e.getMessage()); }
            };


            fileWriter.write("(defproblem problem basic\n\t(");
            entries.entrySet().stream().map(format::apply).forEach(write::accept);
            fileWriter.write("\t)\n\t(\n\t)\n)");
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
}