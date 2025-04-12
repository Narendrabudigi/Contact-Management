import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// Main application class
public class LoginSignupApp {
    public static void main(String[] args) {
        // Create the main login frame
        JFrame frame = new JFrame("Login Page");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create a sub-panel to hold the form fields and buttons with a transparent background
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent background
        formPanel.setLayout(new GridBagLayout());

        // Username and Password fields
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        JTextField userText = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(userText, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passLabel, gbc);

        JPasswordField passText = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passText, gbc);

        // Login and Sign Up buttons
        JButton loginButton = createStyledButton("Login", new Color(60, 179, 113));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        JButton signupButton = createStyledButton("Sign Up", new Color(70, 130, 180));
        gbc.gridx = 1;
        formPanel.add(signupButton, gbc);

        // Add the formPanel to the main panel
        panel.add(formPanel);
        frame.add(panel);
        frame.setVisible(true);

        // ActionListener for Login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String pass = new String(passText.getPassword());

                if (username.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        if (checkLogin(username, pass)) {
                            frame.dispose(); // Close the login window
                            SwingUtilities.invokeLater(() -> new WelcomePage(username)); // Open Welcome Page
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // ActionListener for Sign Up button
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignupFrame(frame); // Open the signup window
            }
        });
    }

    // Method to check login credentials in the database
    public static boolean checkLogin(String username, String pass) {
        boolean isValid = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/contacts", "postgres", "12345");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM login WHERE username = ? AND pass = ?");
            ps.setString(1, username);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Successfully logged in", "Success", JOptionPane.INFORMATION_MESSAGE);
                isValid = true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    // Method to create styled buttons
    private static JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor); // Base color
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker()); // Darker shade on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor); // Reset to original color
            }
        });

        return button;
    }

    // Inner class for the SignupFrame
    public static class SignupFrame extends JFrame {
        private JFrame parentFrame;

        public SignupFrame(JFrame parentFrame) {
            this.parentFrame = parentFrame;
            setTitle("Sign Up");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a panel for the form
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Username and password fields
            JLabel userLabel = new JLabel("Username:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(userLabel, gbc);

            JTextField userText = new JTextField(15);
            gbc.gridx = 1;
            panel.add(userText, gbc);

            JLabel passLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(passLabel, gbc);

            JPasswordField passText = new JPasswordField(15);
            gbc.gridx = 1;
            panel.add(passText, gbc);

            JLabel confirmPassLabel = new JLabel("Confirm Password:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(confirmPassLabel, gbc);

            JPasswordField confirmPassText = new JPasswordField(15);
            gbc.gridx = 1;
            panel.add(confirmPassText, gbc);

            // Sign Up button
            JButton signupButton = new JButton("Sign Up");
            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(signupButton, gbc);

            // Add the panel to the frame
            add(panel);
            setVisible(true);

            // ActionListener for the signup button
            signupButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = userText.getText();
                    String password = new String(passText.getPassword());
                    String confirmPassword = new String(confirmPassText.getPassword());

                    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(SignupFrame.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!password.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(SignupFrame.this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            if (signupUser(username, password)) {
                                JOptionPane.showMessageDialog(SignupFrame.this, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                                createUserContactsTable(username);
                                dispose(); // Close the signup window
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }
           // Method to create a user-specific contacts table
        public void createUserContactsTable(String username) {
            try {
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/contacts", "postgres", "12345");
                Statement stmt = con.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS " + username + " (id SERIAL PRIMARY KEY, name VARCHAR(255), phone VARCHAR(20))";
                stmt.executeUpdate(sql);
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Method to register a new user in the database
        public boolean signupUser(String username, String password) {
            boolean isRegistered = false;
            try {
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/contacts", "postgres", "12345");
                PreparedStatement ps = con.prepareStatement("INSERT INTO login (username, pass) VALUES (?, ?)");
                ps.setString(1, username);
                ps.setString(2, password);
                ps.executeUpdate();

                isRegistered = true;

                ps.close();
                con.close();
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return isRegistered;
        }
    }
}

