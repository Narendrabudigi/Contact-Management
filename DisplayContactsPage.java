import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DisplayContactsPage extends JFrame {
    private JTable contactsTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton, downloadButton, backButton; // Added backButton
    private String username; // To store the username

    public DisplayContactsPage(String username) {
        this.username = username; // Store the username for later use
        initializeUI();
        loadContacts(); // Load contacts when initializing
    }

    private void initializeUI() {
        setTitle("Display All Contacts");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with a background image
        JPanel mainPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("path/to/your/back.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; 
        add(mainPanel);

        // Header panel with more style
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false); 
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Added spacing
        JLabel headerLabel = new JLabel("Contact List");
        headerLabel.setForeground(new Color(255, 215, 0)); // Gold color for header
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(headerLabel);
        gbc.gridy = 0; 
        mainPanel.add(headerPanel, gbc);

        // Create table model and JTable
        tableModel = new DefaultTableModel(new String[]{"Name", "Phone"}, 0);
        contactsTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        // Style the table
        contactsTable.setFillsViewportHeight(true);
        contactsTable.setRowHeight(40); // Adjusted row height
        contactsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contactsTable.setSelectionBackground(new Color(0, 150, 136)); // Teal for selected row
        contactsTable.setSelectionForeground(Color.WHITE);
        contactsTable.setGridColor(new Color(200, 200, 200));
        contactsTable.setShowGrid(true);
        contactsTable.setIntercellSpacing(new Dimension(0, 0)); // Remove spacing between cells

        // Centering text in the cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        contactsTable.setDefaultRenderer(Object.class, centerRenderer);

        // Custom renderer for alternating row colors and hover effect
        contactsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row);
                if (row % 2 == 0) {
                    cell.setBackground(new Color(240, 248, 255)); // Light blue for even rows
                } else {
                    cell.setBackground(Color.WHITE); // White for odd rows
                }
                if (isSelected) {
                    cell.setBackground(new Color(0, 150, 136)); // Selected row color
                    cell.setForeground(Color.WHITE);
                }
                return cell;
            }
        });

        // Add JScrollPane with a smaller preferred size
        JScrollPane scrollPane = new JScrollPane(contactsTable);
        scrollPane.setPreferredSize(new Dimension(550, 250)); 
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 136), 2)); // Teal border
        gbc.gridy = 1; // Row 1
        mainPanel.add(scrollPane, gbc);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false); // Make bottom panel transparent

        // Refresh Button
        refreshButton = createButton("Refresh", new Color(0, 150, 136), e -> loadContacts());
        bottomPanel.add(refreshButton);

        // Download Button
        downloadButton = createButton("Download", new Color(255, 140, 0), e -> downloadContacts());
        bottomPanel.add(downloadButton);

        // Back Button
        backButton = createButton("Back", new Color(255, 69, 0), e -> {
            // Action on Back Button
            this.dispose(); // Close the current window
        });
        bottomPanel.add(backButton);
        
        gbc.gridy = 2; // Row 2
        mainPanel.add(bottomPanel, gbc);

        setVisible(true);
    }

    private JButton createButton(String text, Color backgroundColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setToolTipText("Click to " + text.toLowerCase());

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker()); // Darker shade on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor); // Reset to original color
            }
        });

        button.addActionListener(actionListener);
        return button;
    }

    // Method to load contacts from the user's specific table in the database into the JTable
    private void loadContacts() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/contacts";
        String dbUsername = "postgres";
        String password = "12345";

        // SQL query to select from a user-specific table
        String query = "SELECT * FROM \"" + username + "\""; // Use the username as the table name

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, password);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            // Clear previous data
            tableModel.setRowCount(0);

            // Populate the table model
            while (rs.next()) {
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone");
                tableModel.addRow(new Object[]{name, phoneNumber});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading contacts: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to download contacts into a CSV file
    private void downloadContacts() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Contacts As...");
        fileChooser.setSelectedFile(new java.io.File("contacts.csv")); // Default file name
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            try (FileWriter csvWriter = new FileWriter(fileToSave)) {
                // Write CSV header
                csvWriter.append("Name,Phone \n");

                // Write table data to CSV
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    csvWriter.append(tableModel.getValueAt(i, 0).toString());
                    csvWriter.append(",");
                    csvWriter.append(tableModel.getValueAt(i, 1).toString());
                    csvWriter.append("\n");
                }

                JOptionPane.showMessageDialog(this, "Contacts successfully downloaded to: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        // For testing purposes, provide a test username that matches a table name.
        new DisplayContactsPage("testUser"); // Replace with actual username for testing
    }
}
