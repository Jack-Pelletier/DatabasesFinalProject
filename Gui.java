import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

public class Gui {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JComboBox entreeField, appsField, sideField, drinkField, banquetField, sizeField, steakField, fishField, chickenField, pastaField;
    private JTextField ordernameField, nameField, orderdeleteField, reservationdeleteField, timeField;
    private JLabel orderCountLabel;
    private GuiDAL dal; // ADDED: GUI data access object

    public Gui(GuiDAL dal) {
        this.dal = dal; // ADDED: Assign passed DAL
        frame = new JFrame("Restaurant System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Restaurant Info", createInfoPanel());
        tabbedPane.addTab("Menu", createMenuPanel());
        tabbedPane.addTab("Takeout Orders", createOrderManagementPanel());
        tabbedPane.addTab("Banquet Reservations", createBanquetManagementPanel());
        tabbedPane.addTab("Admin Orders", createAdminDeleteManagementPanel());
        tabbedPane.addTab("Admin Reservation", createAdminDeleteReservationManagementPanel());


        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createBanquetManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel detailsPanel = new JPanel(new GridLayout(3, 4, 5, 1));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Order"));

        detailsPanel.add(new JLabel("Banquet Room:"));
        banquetField = new JComboBox();
        for (int i = 1; i <= 6; i++) banquetField.addItem(i);
        detailsPanel.add(banquetField);

        detailsPanel.add(new JLabel("Group Size:"));
        sizeField = new JComboBox();
        for (int i = 1; i <= 50; i++) sizeField.addItem(i);
        detailsPanel.add(sizeField);

        detailsPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        detailsPanel.add(nameField);

        detailsPanel.add(new JLabel("Date and Time (yyyy-MM-dd HH):"));
        timeField = new JTextField();
        detailsPanel.add(timeField);

        detailsPanel.add(new JLabel("Steak Meals ($36 per):"));
        steakField = new JComboBox();
        for (int i = 0; i <= 50; i++) steakField.addItem(i);
        detailsPanel.add(steakField);

        detailsPanel.add(new JLabel("Salmon Meals ($36 per):"));
        fishField = new JComboBox();
        for (int i = 0; i <= 50; i++) fishField.addItem(i);
        detailsPanel.add(fishField);

        detailsPanel.add(new JLabel("Chicken Meals ($36 per):"));
        chickenField = new JComboBox();
        for (int i = 0; i <= 50; i++) chickenField.addItem(i);
        detailsPanel.add(chickenField);

        detailsPanel.add(new JLabel("Pasta Meals ($36 per):"));
        pastaField = new JComboBox();
        for (int i = 0; i <= 50; i++) pastaField.addItem(i);
        detailsPanel.add(pastaField);

        JButton addReservationButton = new JButton("Add Reservation");
        detailsPanel.add(addReservationButton);
        addReservationButton.addActionListener(new addReservation());

        JTextArea reservationInfoText = new JTextArea();
        reservationInfoText.setText("Reservation Info:\n\n" +
                "Open Hours: Mon - Sun (4 PM - 10 PM)\n" +
                "Reservations are held for 2 hour blocks\n" +
                "The primary banquet room, room 6 holds a maximum of 50 guests, while the Sinatra room, room 5 holds 20, all other rooms fit a table of 8\n" +
                "All Guests MUST choose from one of the banquet meal options.\n" +
                "Date and time format (yyyy-MM-dd HH):\n");
        reservationInfoText.setEditable(false);
        reservationInfoText.setLineWrap(true);
        reservationInfoText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(reservationInfoText);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel(" Search: "));
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField);

        DefaultTableModel searchTableModel = new DefaultTableModel(new String[]{"Name", "Room", "Party Size", "Steak Meals", "Salmon Meals", "Chicken Meals", "Pasta Meals", "Date"}, 0);
        JTable searchTable = new JTable(searchTableModel);
        searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane searchScrollPane = new JScrollPane(searchTable);
        searchScrollPane.setPreferredSize(new Dimension(600, 300));
        searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        searchScrollPane.setBorder(BorderFactory.createTitledBorder("Current Reservations: "));

        panel.add(searchScrollPane, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(detailsPanel, BorderLayout.NORTH);

        loadReservationsIntoTable(searchTableModel);
        return panel;
    }

    private class addReservation implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            int hall = (int) banquetField.getSelectedItem();
            int size = (int) sizeField.getSelectedItem();
            int steak = (int) steakField.getSelectedItem();
            int salmon = (int) fishField.getSelectedItem();
            int chicken = (int) chickenField.getSelectedItem();
            int pasta =  (int) pastaField.getSelectedItem();
            String time = timeField.getText().trim();


            if (!name.isEmpty() && !time.isEmpty()) {
                dal.AddReservation("Italian", "root", "Flint0711##", name, hall, size, steak, salmon, chicken, pasta, time);
            } else 
                JOptionPane.showMessageDialog(frame, "Please fill out all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    

    private JPanel createAdminDeleteManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Name", "Cost ($)", "Entree", "Appetizer", "Side", "Drink"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.setPreferredSize(new Dimension(450, 300));
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Order"));

        detailsPanel.add(new JLabel("Name:"));
        orderdeleteField = new JTextField();
        detailsPanel.add(orderdeleteField);


        JButton addButton = new JButton("Remove Order");
        
        detailsPanel.add(addButton);
        addButton.addActionListener(new deleteOrder());

        orderCountLabel = new JLabel("Total Orders: 0", SwingConstants.CENTER);
        panel.add(orderCountLabel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailsPanel);
        splitPane.setDividerLocation(500);
        splitPane.setContinuousLayout(true);

        loadTakeoutOrdersIntoTable();
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private class deleteOrder implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String namedelete = orderdeleteField.getText().trim();
                
            if (!namedelete.isEmpty()) {
                dal.CancelTakeoutOrder("Italian", "root", "Flint0711##", namedelete);
            } else if (namedelete.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please add an order name to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createAdminDeleteReservationManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Name", "Room", "Party Size", "Steak Meals", "Salmon Meals", "Chicken Meals", "Pasta Meals", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.setPreferredSize(new Dimension(450, 300));
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Order"));

        detailsPanel.add(new JLabel("Name:"));
        reservationdeleteField = new JTextField();
        detailsPanel.add(reservationdeleteField);


        JButton addButton = new JButton("Remove Reservation");
        
        detailsPanel.add(addButton);
        addButton.addActionListener(new deleteReservation());

        orderCountLabel = new JLabel("Total Reservations: 0", SwingConstants.CENTER);
        panel.add(orderCountLabel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailsPanel);
        splitPane.setDividerLocation(500);
        splitPane.setContinuousLayout(true);

        loadReservationsIntoTable(tableModel);
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private class deleteReservation implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String resnamedelete = reservationdeleteField.getText().trim();
                
            if (!resnamedelete.isEmpty()) {
                dal.CancelReservation("Italian", "root", "Flint0711##", resnamedelete);
            } else if (resnamedelete.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please add a reservation name to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private JPanel createOrderManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Name", "Cost ($)", "Entree", "Appetizer", "Side", "Drink"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.setPreferredSize(new Dimension(450, 300));
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Order"));

        detailsPanel.add(new JLabel("Name:"));
        ordernameField = new JTextField();
        detailsPanel.add(ordernameField);

        detailsPanel.add(new JLabel("Entree:"));
        entreeField = new JComboBox();
        detailsPanel.add(entreeField);
        populateDropdown(entreeField, "Entree");

        detailsPanel.add(new JLabel("Appetizer:"));
        appsField = new JComboBox();
        detailsPanel.add(appsField);
        populateDropdown(appsField, "Appetizers");

        detailsPanel.add(new JLabel("Side:"));
        sideField = new JComboBox();
        detailsPanel.add(sideField);
        populateDropdown(sideField, "Sides");

        detailsPanel.add(new JLabel("Drink:"));
        drinkField = new JComboBox();
        detailsPanel.add(drinkField);
        populateDropdown(drinkField, "Drinks");

        JButton addButton = new JButton("Add Order");
        
        detailsPanel.add(addButton);
        addButton.addActionListener(new addOrder());

        orderCountLabel = new JLabel("Total Orders: 0", SwingConstants.CENTER);
        panel.add(orderCountLabel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailsPanel);
        splitPane.setDividerLocation(500);
        splitPane.setContinuousLayout(true);

        loadTakeoutOrdersIntoTable();
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private class loadTakeoutOrdersIntoTableButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                ResultSet rs = dal.GetAllTakeoutOrders("Italian", "root", "Flint0711##");
        
                // Clear existing table data
                tableModel.setRowCount(0);
        
                while (rs.next()) {
                    String orderName = rs.getString("OrderName");
                    String entree = rs.getString("Entree");
                    String appetizer = rs.getString("Appetizer");
                    String side = rs.getString("Side");
                    String drink = rs.getString("Drink");
        
                    Object[] row = { orderName, entree, appetizer, side, drink };
                    tableModel.addRow(row);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class addOrder implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = ordernameField.getText().trim();
            String entree = (String) entreeField.getSelectedItem();
            String app = (String) appsField.getSelectedItem();
            String side = (String) sideField.getSelectedItem();
            String drink = (String) drinkField.getSelectedItem();


            if (!name.isEmpty() && !entree.isEmpty() && !app.isEmpty() && !side.isEmpty() && !drink.isEmpty()) {
                dal.AddTakeoutOrder("Italian", "root", "Flint0711##", name, entree, app, side, drink);
            } else if (name.isEmpty()) {
                System.out.println(name);
                JOptionPane.showMessageDialog(frame, "Please add an order name!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel(" Search: "));
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField);

        DefaultTableModel searchTableModel = new DefaultTableModel(new String[]{"Meal", "Calories", "Cost ($)", "Gluten Free", "Seafood"}, 0);
        JTable searchTable = new JTable(searchTableModel);
        searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane searchScrollPane = new JScrollPane(searchTable);
        searchScrollPane.setPreferredSize(new Dimension(600, 300));
        searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        try {
            ResultSet rs = dal.GetAllFoodItems(); // Uses injected DAL object
            System.out.println(rs);
            while (rs.next()) {
                String foodType = rs.getString("FoodType");
                String name = rs.getString("FoodName");
                int calories = rs.getInt("Calories");
                int cost = rs.getInt("Cost");
                boolean gluten = rs.getBoolean("Gluten");
                boolean seafood = rs.getBoolean("Seafood");


                searchTableModel.addRow(new Object[]{
                        name,
                        calories,
                        cost,
                        gluten ? "Yes" : "No",
                        seafood ? "Yes" : "No"
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading menu items from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        searchField.addActionListener(e -> {
            String query = searchField.getText().toLowerCase();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(searchTableModel);
            searchTable.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        });

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(searchScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea restaurantInfoText = new JTextArea();
        restaurantInfoText.setText("Welcome to Minton's Steakhouse!\n\n" +
                "Open Hours: Mon - Sun (4 PM - 10 PM)\n" +
                "Location: Main Campus, North Residental Village, Building 7, Room 105. (Don't visit we will be fleeing the state within the hour)\n\n");
        restaurantInfoText.setEditable(false);
        restaurantInfoText.setLineWrap(true);
        restaurantInfoText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(restaurantInfoText);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


    
    private void populateDropdown(JComboBox<String> comboBox, String category) {
        try {
            List<String> items = dal.getFoodItemByCategory(category);
            comboBox.removeAllItems();
            for (String item : items) {
                comboBox.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadTakeoutOrdersIntoTable() {
        try {
            ResultSet rs = dal.GetAllTakeoutOrders("Italian", "root", "Flint0711##");
    
            // Clear existing table data
            tableModel.setRowCount(0);
    
            while (rs.next()) {
                String orderName = rs.getString("OrderName");
                int cost = rs.getInt("Cost");
                String entree = rs.getString("Entree");
                String appetizer = rs.getString("Appetizer");
                String side = rs.getString("Side");
                String drink = rs.getString("Drink");
    
                Object[] row = { orderName, cost, entree, appetizer, side, drink };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadReservationsIntoTable(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing rows
        try {
            ResultSet rs = dal.GetAllReservations();
            while (rs != null && rs.next()) {
                Object[] row = new Object[]{
                    rs.getString("ReservationName"),
                    rs.getInt("BanquetHall"),
                    rs.getInt("PartySize"),
                    rs.getInt("SteakMeals"),
                    rs.getInt("SalmonMeals"),
                    rs.getInt("ChickenMeals"),
                    rs.getInt("PastaMeals"),
                    rs.getString("ReservationDate")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        Scanner userInformation = new Scanner(System.in);
        System.out.println("Enter username and password:");
        String userName = userInformation.nextLine();
        String password = userInformation.nextLine();

        GuiDAL dal = new GuiDAL("Italian", userName, password);
; // You must define this to connect to your DB
        SwingUtilities.invokeLater(() -> new Gui(dal));
    }
}
