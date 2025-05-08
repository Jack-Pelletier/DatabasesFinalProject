import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Scanner;

public class Gui {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JComboBox entreeField, appsField, sideField, drinkField, banquetField, sizeField, steakField, fishField, chickenField, pastaField;
    private JTextField nameField;
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

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createBanquetManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel detailsPanel = new JPanel(new GridLayout(3, 4, 5, 1));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Order"));

        detailsPanel.add(new JLabel("Banquet Room:"));
        String[] choices = {"CHOICE 1", "CHOICE 2", "CHOICE 3", "CHOICE 4", "CHOICE 5", "CHOICE 6"};
        banquetField = new JComboBox(choices);
        detailsPanel.add(banquetField);

        detailsPanel.add(new JLabel("Group Size:"));
        sizeField = new JComboBox();
        for (int i = 1; i <= 50; i++) sizeField.addItem(i);
        detailsPanel.add(sizeField);

        detailsPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        detailsPanel.add(nameField);

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

        JButton addButton = new JButton("Add Reservation");
        JButton deleteButton = new JButton("Cancel Reservation");
        detailsPanel.add(addButton);
        detailsPanel.add(deleteButton);

        JTextArea reservationInfoText = new JTextArea();
        reservationInfoText.setText("Reservation Info:\n\n" +
                "Open Hours: Mon - Sun (4 PM - 10 PM)\n" +
                "Reservations are held for 2 hour blocks\n" +
                "The primary banquet room holds a maximum of 50 guests, while the Sinatra holds 20, all other rooms fit a table of 8\n" +
                "All Guests MUST choose from one of the banquet meal options.\n");
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

        DefaultTableModel searchTableModel = new DefaultTableModel(new String[]{"Name", "Room", "Party Size", "Cost ($)", "Steak Meals", "Salmon Meals", "Chicken Meals", "Pasta Meals", "Date"}, 0);
        JTable searchTable = new JTable(searchTableModel);
        searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane searchScrollPane = new JScrollPane(searchTable);
        searchScrollPane.setPreferredSize(new Dimension(600, 300));
        searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        searchScrollPane.setBorder(BorderFactory.createTitledBorder("Current Reservations: "));

        panel.add(searchScrollPane, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(detailsPanel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createOrderManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Meal", "Calories", "Cost", "Gluten Free", "Seafood"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.setPreferredSize(new Dimension(450, 300));
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Order"));

        detailsPanel.add(new JLabel("Entree:"));
        entreeField = new JComboBox();
        detailsPanel.add(entreeField);

        detailsPanel.add(new JLabel("Appetizer:"));
        appsField = new JComboBox();
        detailsPanel.add(appsField);

        detailsPanel.add(new JLabel("Side:"));
        sideField = new JComboBox();
        detailsPanel.add(sideField);

        detailsPanel.add(new JLabel("Drink:"));
        String[] choices = {"CHOICE 1", "CHOICE 2", "CHOICE 3", "CHOICE 4", "CHOICE 5", "CHOICE 6"};
        drinkField = new JComboBox(choices);
        detailsPanel.add(drinkField);

        JButton addButton = new JButton("Add Order");
        JButton deleteButton = new JButton("Cancel Order");

        detailsPanel.add(addButton);
        deleteButton.addActionListener(new DeleteBookListener());
        detailsPanel.add(deleteButton);

        orderCountLabel = new JLabel("Total Orders: 0", SwingConstants.CENTER);
        panel.add(orderCountLabel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailsPanel);
        splitPane.setDividerLocation(500);
        splitPane.setContinuousLayout(true);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
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
        restaurantInfoText.setText("Welcome to ...!\n\n" +
                "Open Hours: Mon - Sun (4 PM - 10 PM)\n" +
                "Location: Main Campus, Block A\n\n");
        restaurantInfoText.setEditable(false);
        restaurantInfoText.setLineWrap(true);
        restaurantInfoText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(restaurantInfoText);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private class DeleteBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                updateBookCount();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an order to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateBookCount() {
        orderCountLabel.setText("Total Orders: " + tableModel.getRowCount());
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
