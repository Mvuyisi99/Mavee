package za.ac.cput.studentsystemclient;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class LoginPage extends JFrame implements ActionListener {

    private JPanel pnlSystem = new JPanel();
    private JPanel pnlLabel = new JPanel();
    private JPanel pnlDetails = new JPanel();
    private JPanel pnlUserType = new JPanel(); // New panel for radio buttons
    private JPanel pnlButton = new JPanel();

    // Add labels
    private JLabel lblLogo = new JLabel("Login");
    private JLabel lblUsername = new JLabel("Username: ");
    private JLabel lblPassword = new JLabel("Password: ");
    private JLabel lblUserType = new JLabel("Login As: ");

    // Add TextFields
    private JTextField txtUsername = new JTextField(15);
    private JPasswordField txtPassword = new JPasswordField(20);

    // Radio buttons for user type selection
    private JRadioButton rbStudent = new JRadioButton("Student", true); // Default to Student
    private JRadioButton rbAdmin = new JRadioButton("Admin");
    private ButtonGroup userTypeGroup = new ButtonGroup();

    // Add Buttons
    private JButton btnLogin = new JButton("LOGIN");

    // Track selected user type
    private String selectedUserType = "STUDENT"; // Default to STUDENT

    public LoginPage() {
        super("Student System");
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        this.setLayout(new GridLayout(1, 1, 5, 5));
        pnlSystem.setLayout(new GridLayout(4, 1, 5, 5)); // Changed to 4 rows for user type

        // Logo setup
        pnlLabel.setLayout(new GridLayout(1, 1, 5, 5));
        pnlLabel.setBackground(new Color(173, 216, 230));
        lblLogo.setFont(lblLogo.getFont().deriveFont(20f));
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        pnlLabel.add(lblLogo);

        // User type radio buttons setup
        userTypeGroup.add(rbStudent);
        userTypeGroup.add(rbAdmin);
        rbStudent.setSelected(true); // Default to Student

        pnlUserType.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlUserType.add(lblUserType);
        pnlUserType.add(rbStudent);
        pnlUserType.add(rbAdmin);

        // Details panel for username and password
        pnlDetails.setLayout(new GridLayout(2, 2, 5, 5));
        pnlDetails.add(lblUsername);
        pnlDetails.add(txtUsername);
        pnlDetails.add(lblPassword);
        pnlDetails.add(txtPassword);

        // Button panel
        pnlButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnLogin.setPreferredSize(new java.awt.Dimension(100, 50));
        btnLogin.setBackground(new Color(60, 178, 114));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
    }

    private void setupLayout() {
        // Add panels to main system panel
        pnlSystem.add(pnlLabel);
        pnlSystem.add(pnlUserType); // Add user type selection panel
        pnlSystem.add(pnlDetails);
        pnlSystem.add(pnlButton);

        pnlButton.add(btnLogin);
        this.add(pnlSystem);
    }

    private void setupEventHandlers() {
        // Radio button listeners
        rbStudent.addActionListener(e -> {
            if (rbStudent.isSelected()) {
                selectedUserType = "STUDENT";
                lblLogo.setText("Student Login");
                lblLogo.setForeground(Color.BLUE);
            }
        });

        rbAdmin.addActionListener(e -> {
            if (rbAdmin.isSelected()) {
                selectedUserType = "ADMIN";
                lblLogo.setText("Admin Login");
                lblLogo.setForeground(Color.RED);
            }
        });

        // Login button listener
        btnLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        String message = "LOGIN:" + selectedUserType + ":" + username + "," + password;
        String response = Client.sendToServer(message);

        if (response == null) {
            response = "ERROR: No response from server.";
        }

        if (response.startsWith("SUCCESS:")) {
            String role = response.substring(8).trim().toUpperCase();
            JOptionPane.showMessageDialog(this, "Login successful. Role: " + role);

            if (role.equals("ADMIN")) {
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                adminDashboard.setVisible(true);
                this.dispose();
            } else if (role.equals("STUDENT")) {
                
                StudentPage studentPage = new StudentPage(username); 
                studentPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                studentPage.setVisible(true);
                this.dispose();
            }
        } else if (response.startsWith("ERROR:")) {
            JOptionPane.showMessageDialog(this, response.substring(6));
        }
    }

}
