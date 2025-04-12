import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddContactPage extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private String username; // To store the username

    public AddContactPage(String username) {
        this.username = username; // Store the username for later use

        setTitle("Add Contact");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create a panel with a background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("images/back.png"); // Update the path to your image
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

        // Name field
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        nameField = new JTextField(15);
        nameField.setToolTipText("Enter contact's name");
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // Phone field
        JLabel phoneLabel = new JLabel("Phone Number:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(phoneLabel, gbc);
        phoneField = new JTextField(15);
        phoneField.setToolTipText("Enter a 10-digit phone number");
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        // Add button
        JButton addButton = createStyledButton("Add Contact");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        // Add button action
        addButton.addActionListener(e -> addContact());

        // Add form panel to the main panel
        panel.add(formPanel);
        add(panel);

        setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validate input
        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate phone number (only digits, max length)
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone number must be 10 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database insert logic
        String jdbcUrl = "jdbc:postgresql://localhost:5432/contacts";
        String dbUsername = "postgres"; // Database username
        String dbPassword = "12345"; // Database password

        // Use try-with-resources to manage the connection and statement
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + username + " (name, phone) VALUES (?, ?)")) { // Use the username as table name
             
            stmt.setString(1, name);
            stmt.setString(2, phone);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Contact added: " + name + " - " + phone);
                int response = JOptionPane.showConfirmDialog(this, "Do you want to add another contact?", "Add Another?", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    nameField.setText("");
                    phoneField.setText("");
                } else {
                    dispose(); // Close the dialog after adding
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error adding contact. It might already exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding contact: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to create a styled button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 144, 255)); // Dodger blue
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
                button.setBackground(new Color(30, 144, 255)); // Reset to original color
            }
        });

        return button;
    }

    public static void main(String[] args) {
        // For testing purposes
        new AddContactPage("test_user"); // Replace with actual username for testing
    }
}
