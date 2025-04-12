import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {
    private String username;

    public WelcomePage(String username) {
        this.username = username;
        setTitle("Welcome to Your Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create the main panel with a background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("images/back.png"); // Update the path to your image
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout());

        // Create a glass pane effect for semi-transparent layers
        JPanel overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black background
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 48));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent background for text

        overlayPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Button panel with a background image
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20)); // Larger grid spacing
        buttonPanel.setOpaque(false); // Transparent panel for buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Add custom buttons with larger icons and sizes
        JButton addContactButton = createStyledButton("Add Contact", "images/add.jpg");
        JButton editContactButton = createStyledButton("Edit Contact", "images/edit.jpg");
        JButton deleteContactButton = createStyledButton("Delete Contact", "images/delete.jpg");
        JButton displayContactsButton = createStyledButton("Display All Contacts", "images/display.jpg");

        buttonPanel.add(addContactButton);
        buttonPanel.add(editContactButton);
        buttonPanel.add(deleteContactButton);
        buttonPanel.add(displayContactsButton);

        overlayPanel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(overlayPanel, BorderLayout.CENTER);
        add(panel);

        setVisible(true);

        // Button action listeners (to be connected with actual pages)
        addContactButton.addActionListener(e -> new AddContactPage(username)); // Assuming AddContactPage is defined
        editContactButton.addActionListener(e -> new EditContactPage(username)); // Assuming EditContactPage is defined
        deleteContactButton.addActionListener(e -> new DeleteContactPage(username)); // Assuming DeleteContactPage is defined
        displayContactsButton.addActionListener(e -> new DisplayContactsPage(username)); // Assuming DisplayContactsPage is defined
    }

    // Method to create a styled button with larger icons and larger size
    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18)); // Larger font
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 179, 113)); // Base color
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true)); // Thicker white border
        button.setBorderPainted(true);
        button.setPreferredSize(new Dimension(200, 60)); // Larger button size

        // Set button icon with resized image
        if (iconPath != null) {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image resizedImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            button.setIcon(resizedIcon); // Set resized icon
            button.setHorizontalTextPosition(SwingConstants.RIGHT); // Position text to the right of the icon
            button.setIconTextGap(20); // Add space between icon and text
        }

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 128, 0)); // Change to darker green on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 179, 113)); // Reset to original green
            }
        });

        return button;
    }
}
