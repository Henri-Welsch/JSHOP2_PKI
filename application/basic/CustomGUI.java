import java.awt.*;
import javax.swing.*;
import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CustomGUI {
    static String dist = "";

    public static void main(String[] args) {    
        JFrame frame = new JFrame("Eingabe");
        

        frame.setSize(350, 265);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();    
        placeComponents(panel);

        frame.add(panel);
        frame.setVisible(true);
    }


    /** #region [ rgba(255, 120, 255, 0.12) ]
     * Creates a custom GUI for interacting with the problem base
     *
     * @param panel         Panel in which the Swing elements are set
     * @return              Has no retrun, the Main method has the refrence to the Panel
     */ //#endregion

    private static void placeComponents(JPanel panel) {
        /******* Call readData to load the Data form problem into the cash *******/
        Map<String, Map<JTextField, JTextField>> entries = readDate();


        /************** Create Main GUI as an Interface for the user *************/
        panel.setLayout(null);

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


        JLabel truck_Label = new JLabel("Einträge - Trucks: ");
        truck_Label.setBounds(10,70,150,25);
        panel.add(truck_Label);

        JButton truck_Button = new JButton("Öffne Manager");
        truck_Button.setBounds(170,70,150,25);
        panel.add(truck_Button);

        truck_Button.addActionListener(e -> {
            getManager(entries.get("TRUCK"), "TRUCK");
            System.out.println("Open truck manager");

        });

        
        JLabel driver_Label = new JLabel("Einträge - Fahrer: ");
        driver_Label.setBounds(10,100,150,25);
        panel.add(driver_Label);

        JButton driver_Button = new JButton("Öffne Manager");
        driver_Button.setBounds(170,100,150,25);
        panel.add(driver_Button);

        driver_Button.addActionListener(e -> {
            getManager(entries.get("TRUCK-LICENSE"),"TRUCK-LICENSE");
            System.out.println("Open driver manager");

        });


        JLabel hall_Label = new JLabel("Einträge - Hallen: ");
        hall_Label.setBounds(10,130,150,25);
        panel.add(hall_Label);

        JButton hall_Button = new JButton("Öffne Manager");
        hall_Button.setBounds(170,130,150,25);
        panel.add(hall_Button);

        hall_Button.addActionListener(e -> {
            getManager(entries.get("IN-CITY"), "IN-CITY");
            System.out.println("Open hall manager");
        });


        JButton save_Button = new JButton("Speichern");
        save_Button.setBounds(10, 185, 150, 25);
        panel.add(save_Button);

        save_Button.addActionListener(e -> {
            System.out.println("Speichern");
            writeData(entries);
        });


        JButton start_Button = new JButton("Ausführen");
        start_Button.setBounds(170, 185, 150, 25);
        panel.add(start_Button);

        start_Button.addActionListener(e -> {
            System.out.println("Ausführen");
            try {
               Runtime.getRuntime().exec(new String[] {"cmd", "/K", "make run basic problem"});
            } catch (Exception a) { 
                System.out.println(a.getMessage()); 
            }
        });
    }


    /** #region [ rgba(255, 120, 255, 0.12) ]
     * Creates a custom GUI that represent all data entries for a given attribute
     *
     * @param entries       Represents the cash of the data from the problem data set
     * @param manager       A simple String that represent the the name for the current manager
     * @return              Has no retrun, this method creats a new GUI for the given manager
     */ //#endregion

    private static void getManager(Map<JTextField, JTextField> entries, String manager) {
        /******************** Create all Panels that will later be used *******************/
        JFrame newFrame = new JFrame(manager);

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
        mainPanel.setPreferredSize(new Dimension(1,(entries.size()*20)));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        /********* Interface that calls getComponen() and adds additional features ********/
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


        /******** Main stream that streams over all entries of the cash *********/
        entries.entrySet().stream().map(foo::apply).forEach(mainPanel::add);


        /**************** Create additional features for the GUI ****************/
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

        
        /************** Add all Swing elements to the main element **************/
        topPanel.add(hinzufuegen);

        newFrame.add(topPanel, BorderLayout.NORTH);
        newFrame.add(scrollPane, BorderLayout.CENTER);

        newFrame.setSize(350, 265);
        newFrame.setVisible(true);
    }


    /** #region [ rgba(255, 120, 255, 0.12) ]
     * Creates a single JPanel that contains all information for a given entry of the entries cash set
     *
     * @param entry         Represents a single entry of the entries cash set
     * @param counter       A simple Integer, that reprsents the number count of the current entry
     * @return              Return the formatted informations of the given entry as Swing elements
     */ //#endregion

    private static JPanel getSubPanel(Map.Entry<JTextField, JTextField> entry, Integer counter) {
        /************ Create a panel with the various swing elements in it ************/
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
        entryValue.setPreferredSize(new Dimension(90, 20));
        subPanel.add(entryValue);

        subPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton delButton = new JButton("Löschen");
        delButton.setPreferredSize(new Dimension(85, 20));
        subPanel.add(delButton);

        return subPanel;
    }


    /** #region [ rgba(255, 120, 255, 0.12) ]
     * Reads all entries from the Problem dataset and stores them in a HashMap
     * 
     * @return              Return the HashMap containing all entries in the Problem data set
     */ //#endregion

    private static Map<String, Map<JTextField, JTextField>> readDate() {
        Map<String, Map<JTextField, JTextField>> entries = new HashMap<>();

        try (FileReader fileReader = new FileReader("application/basic/problem")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            /********* Interface that formats the String *********/
            Consumer<String> line = str -> {
                if (str.contains("DISTANCE")) { dist +=  "\n\t" + str; return; };
                String arr[] = str.replace("(", "").replace(")", "").trim().split(" ");
                if (arr.length < 3) return;
                if (entries.get(arr[0]) == null) 
                    entries.put(arr[0], new HashMap<>());
                entries.get(arr[0]).put(new JTextField(arr[1]), new JTextField(arr[2]));
            };

            /********* Main Stream that goes over the data *********/
            bufferedReader.lines().skip(1).forEach(line::accept);
        } catch (Exception e) { System.out.println(e); }

        return entries;
    }


    /** #region [ rgba(255, 120, 255, 0.12) ]
     * Overwrites the Problem data set with the current entries of the cash (HashMap)
     *
     * @param entries       Represents the cash of the data from the problem data set
     * @return              Has no return
     */ //#endregion

    private static void writeData(Map<String, Map<JTextField, JTextField>> entries)  {
        try (FileWriter fileWriter = new FileWriter("application/basic/problem")) {

            /************ Interface formats the data for the Problem data set ************/
            Function<Map.Entry<JTextField, JTextField>, String> line = str -> {
                return " " + str.getKey().getText() + " " + str.getValue().getText() + ")";
            };

            /******** Interface streams over the all entries of a given attribute ********/
            Function<Map.Entry<String, Map<JTextField, JTextField>>, String> format = str -> {
                return str.getValue().entrySet().stream().map(line::apply)
                    .map(("\t\t(" + str.getKey())::concat).collect(Collectors.joining("\n"));
            };

            /************ Interface formats the data for the Problem data set ************/
            Consumer<String> write = curLine -> {
                try { fileWriter.write("\n\n" + curLine);
                } catch (IOException e) { System.out.println(e.getMessage()); }
            };            


            /***** Stream over the cash and apply different functions for each entry *****/
            fileWriter.write("(defproblem problem basic\n\t(");
            fileWriter.write(dist);
            entries.entrySet().stream().filter(str -> !str.getKey().contains("PARCEL-AT"))
            .map(format::apply).forEach(write::accept);
            fileWriter.write("\n\t)\n\t(");

            entries.entrySet().stream().filter(str -> str.getKey().contains("PARCEL-AT"))
                .map(format::apply).forEach(write::accept);
            fileWriter.write("\n\t)\n)");
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
}