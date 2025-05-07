import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JComboBox titleField, appsField, authorField, genreField;
    private JLabel orderCountLabel;

    public Gui() {
        frame = new JFrame("Restuarant System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Restuarant Info", createInfoPanel());
        tabbedPane.addTab("Menu", createMenuPanel());
        tabbedPane.addTab("Takeout Orders", createOrderManagementPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    /*
     *  Creates the "Order Management" panel with JScrollPane
     */
    private JPanel createOrderManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table Panel
        String[] columnNames = {"Meal", "Calories", "Cost", "Gluten Free", "Seafood"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.setPreferredSize(new Dimension(450, 300)); // Ensure ScrollPane is properly sized
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Right Side: Book Details Form
        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Order"));

        detailsPanel.add(new JLabel("Entree:"));
        titleField = new JComboBox();
        detailsPanel.add(titleField);

        detailsPanel.add(new JLabel("Appetizer:"));
        appsField = new JComboBox();
        detailsPanel.add(appsField);

        detailsPanel.add(new JLabel("Side:"));
        authorField = new JComboBox();
        detailsPanel.add(authorField);

        detailsPanel.add(new JLabel("Drink:"));
        String[] choices = { "CHOICE 1","CHOICE 2", "CHOICE 3","CHOICE 4","CHOICE 5","CHOICE 6"};
        genreField = new JComboBox(choices);
        detailsPanel.add(genreField);

        JButton addButton = new JButton("Add Order");
        JButton deleteButton = new JButton("Cancel Order");

        //addButton.addActionListener(new AddBookListener());
        deleteButton.addActionListener(new DeleteBookListener());

        detailsPanel.add(addButton);
        detailsPanel.add(deleteButton);

        orderCountLabel = new JLabel("Total Orders: 0", SwingConstants.CENTER);
        panel.add(orderCountLabel, BorderLayout.NORTH);

        // Split Pane (Left: Book List, Right: Book Details)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailsPanel);
        splitPane.setDividerLocation(500);
        splitPane.setContinuousLayout(true);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    /*
     *  Creates the "Search Books" panel with JScrollPane
     */
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Search bar
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel(" Search: "));
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField);

        // Search results table
        DefaultTableModel searchTableModel = new DefaultTableModel(new String[]{"Meal", "Calories", "Cost ($)", "Gluten", "Seafood"}, 0);
        JTable searchTable = new JTable(searchTableModel);
        searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane searchScrollPane = new JScrollPane(searchTable);
        searchScrollPane.setPreferredSize(new Dimension(600, 300)); // Ensure ScrollPane is properly sized
        searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        searchField.addActionListener(e -> {
            String query = searchField.getText().toLowerCase();
            searchTableModel.setRowCount(0);
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String title = tableModel.getValueAt(i, 0).toString().toLowerCase();
                String author = tableModel.getValueAt(i, 1).toString().toLowerCase();
                if (title.contains(query) || author.contains(query)) {
                    searchTableModel.addRow(new Object[]{tableModel.getValueAt(i, 0), tableModel.getValueAt(i, 1), tableModel.getValueAt(i, 2)});
                }
            }
        });

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(searchScrollPane, BorderLayout.CENTER);
        return panel;
    }

    /*
     *  Creates the "Library Information" panel with JScrollPane
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea libraryInfoText = new JTextArea();
        libraryInfoText.setText("Welcome to ...!\n\n" +
                                "Open Hours: Mon - Fri (4 PM - 9 PM) Sat - Sun (12 PM - 10 PM)\n" +
                                "Location: Main Campus, Block A\n\n" +
                                "");
        libraryInfoText.setEditable(false);
        libraryInfoText.setLineWrap(true);
        libraryInfoText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(libraryInfoText);
        scrollPane.setPreferredSize(new Dimension(800, 400)); // Ensure ScrollPane is properly sized
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /*
     * Action Listener for "Add Book" Button
     *
    private class AddBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String title = titleField.get().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();

            if (!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                tableModel.addRow(new Object[]{title, author, genre});
                updateBookCount();
                JOptionPane.showMessageDialog(frame, "Book Added Successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        *\
    /*
     * Action Listener for "Delete Book" Button
     */
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

    /*
     * Updates the book count label
     */
    private void updateBookCount() {
        orderCountLabel.setText("Total Orders: " + tableModel.getRowCount());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gui::new);
    }
}
