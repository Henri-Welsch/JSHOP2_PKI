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
        Map<String, Map<String, String>> data = readDate();
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
        JLabel truck_Label = new JLabel("Anzahl Trucks " + data.get("TRUCK").size());
        truck_Label.setBounds(10,70,150,25);
        panel.add(truck_Label);

        JButton truck_Button = new JButton("Öffne Manager");
        truck_Button.setBounds(170,70,150,25);
        panel.add(truck_Button);

        truck_Button.addActionListener(e -> {
            getManager(data.get("TRUCK"), "TRUCK");
            System.out.println("Open truck manager");
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



    private static void getManager(Map<String, String> data, String manager) {
        JFrame newFrame = new JFrame(manager);



        JPanel vPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        vPanel.setPreferredSize(vPanel.getPreferredSize());
        int weight = data.size()*20;
        vPanel.setPreferredSize(new Dimension(1,weight));

        JScrollPane scrollPane = new JScrollPane(vPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        


        Function <Map.Entry<String, String>, JPanel> foo = new Function<Map.Entry<String, String>, JPanel>(){
            int counter = 1;

            @Override
            public JPanel apply(Entry<String, String> t) {
                return getEntry(t, counter++);
            }
        };
        


        data.entrySet().stream().map(foo::apply).forEach(vPanel::add);


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        
        JButton hinzufuegen = new JButton("Eintrag hinzufügen");
        hinzufuegen.setPreferredSize(new Dimension(165, 26));
        hinzufuegen.addActionListener(lamdbda -> {
            System.out.println("Eintrag hinzufügen");
            vPanel.add(foo.apply(null));
            vPanel.revalidate();
            vPanel.repaint();
            // TODO ScrollPane is not growing
            // TODO Cant delete enty
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



    private static JPanel getEntry(Map.Entry<String, String> str, Integer counter) {
        JPanel hPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            hPanel.add(Box.createRigidArea(new Dimension(2, 0)));

            String key = (str == null)? "" : str.getKey();
            String value = (str == null)? "" : str.getValue();

            JLabel x = new JLabel("Nr: " + counter); 
            x.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            x.setPreferredSize(new Dimension(40, 20));


            JTextField a = new JTextField(key); 
            a.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            a.setPreferredSize(new Dimension(90, 20));

            JTextField b = new JTextField(value);
            b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            b.setPreferredSize(new Dimension(100, 20));

            JButton c = new JButton("Löschen");
            c.setPreferredSize(new Dimension(95, 20));
            hPanel.add(Box.createRigidArea(new Dimension(2, 0)));

            // TODO Funktionalität von Löschen ist nicht mehr vorhanden
            // TODO Funktionalität von Löschen muss wieder hergestellt werden
            /*
            c.addActionListener(e -> {
                panel.remove(hPanel);
                panel.revalidate();
                panel.repaint();
                data.remove(str.getKey());
                vPanel.setPreferredSize(new Dimension(1,data.size()*20));
                // TODO call Writer
            });
            */

            hPanel.add(x); hPanel.add(a); hPanel.add(b);
            hPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            hPanel.add(c);
            return hPanel;
    }
}