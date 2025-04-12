import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteContactPage extends JFrame {
    private JTextField searchField;
    private JTextField nameField;
    private JTextField phoneField;
    private String username; // To store the username

    public DeleteContactPage(String username) {
        this.username = username; // Store the username for later use
        initUI(); // Initialize UI components
    }

    private void initUI() {
        setTitle("Delete Contact");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Create a panel with a background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("images/back.png"); // Update with your image path
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create a semi-transparent panel for form inputs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white background

        // Search field
        JLabel searchLabel = new JLabel("Search by Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(searchLabel, gbc);
        searchField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(searchField, gbc);

        // Search button
        JButton searchButton = createStyledButton("Search");
        gbc.gridx = 2;
        formPanel.add(searchButton, gbc);

        // Name field
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(nameLabel, gbc);
        nameField = new JTextField(15);
        nameField.setEditable(false); // Make name field non-editable
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // Phone field
        JLabel phoneLabel = new JLabel("Phone Number:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(phoneLabel, gbc);
        phoneField = new JTextField(15);
        phoneField.setEditable(false); // Make phone field non-editable
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        // Delete button
        JButton deleteButton = createStyledButton("Delete Contact");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // Set grid width to 1 for the delete button
        formPanel.add(deleteButton, gbc);

        // Back button
        JButton backButton = createStyledButton("Back");
        gbc.gridx = 1; // Set the grid x position to 1 for the back button
        formPanel.add(backButton, gbc);

        // Add action listeners
        searchButton.addActionListener(e -> searchContact());
        deleteButton.addActionListener(e -> deleteContact());
        backButton.addActionListener(e -> {
            dispose(); // Close current window
            new WelcomePage(username); // Redirect to the Welcome Page
        });

        // Add form panel to the main panel
        panel.add(formPanel);
        add(panel);

        setVisible(true);
    }

    private void searchContact() {
        String searchName = searchField.getText().trim();

        // Validate input
        if (searchName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database search logic
        String jdbcUrl = "jdbc:postgresql://localhost:5432/contacts";
        String dbUsername = "postgres"; // Change this to your database username
        String dbPassword = "12345"; // Change this to your database password

        // Update SQL statement to include user-specific logic
        String query = "SELECT * FROM \"" + this.username + "\" WHERE name = ?"; // Assuming your table is named as <username>_contacts

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, searchName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Load data into fields
                nameField.setText(rs.getString("name"));
                phoneField.setText(rs.getString("phone"));
            } else {
                JOptionPane.showMessageDialog(this, "Contact not found.", "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching contact: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteContact() {
        String name = nameField.getText().trim();

        // Validate that a contact is selected
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for a contact to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database delete logic
        String jdbcUrl = "jdbc:postgresql://localhost:5432/contacts";
        String dbUsername = "postgres"; // Change this to your database username
        String dbPassword = "12345"; // Change this to your database password

        // Update SQL statement to include user-specific logic
        String query = "DELETE FROM \"" + this.username + "\" WHERE name = ?"; // Assuming your table is named as <username>_contacts

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Contact deleted: " + name);
                nameField.setText("");
                phoneField.setText("");
                searchField.setText(""); // Clear the search field after deletion
            } else {
                JOptionPane.showMessageDialog(this, "No contact found to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting contact: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to create a styled button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 69, 0)); // Red-orange color
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker()); // Darker shade on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 69, 0)); // Reset to original color
            }
        });

        return button;
    }

    public static void main(String[] args) {
        // For testing purposes
        new DeleteContactPage("username"); // Replace with actual username for testing
    }
}
