package za.ac.cput.studentsystemclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddStudentPage extends JFrame implements ActionListener {

    private JPanel pnlPage = new JPanel();
    private JPanel pnlLabel = new JPanel();
    private JPanel pnlDetails = new JPanel();
    private JPanel pnlButton = new JPanel();

    private JLabel lblLogo = new JLabel("ADMIN");
    private JLabel lblName = new JLabel("Name : ");
    private JLabel lblSurname = new JLabel("Surname : ");
    private JLabel lblEmail = new JLabel("Email : ");
    private JLabel lblUsername = new JLabel("Username : ");
    private JLabel lblPassword = new JLabel("Password : ");

    private JTextField txtName = new JTextField(15);
    private JTextField txtSurname = new JTextField(15);
    private JTextField txtEmail = new JTextField(15);
    private JTextField txtUsername = new JTextField(15);
    private JTextField txtPassword = new JTextField(15);

    private JButton btnSave = new JButton("SAVE");

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Menu");
    private JMenuItem menuAddStudent = new JMenuItem("Add Student");
    private JMenuItem menuAddCourse = new JMenuItem("Add Course");
    private JMenuItem menuViewEnrollments = new JMenuItem("View Enrollments");
    private JMenuItem menuLogout = new JMenuItem("Logout");

    public AddStudentPage() {
        super("Student System");
        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        pnlPage.setLayout(new GridLayout(3, 1, 5, 5));
        pnlPage.add(pnlLabel);
        pnlPage.add(pnlDetails);
        pnlPage.add(pnlButton);

        pnlLabel.setLayout(new GridLayout(1, 1, 5, 5));
        pnlLabel.setBackground(new Color(173, 216, 230));
        lblLogo.setFont(lblLogo.getFont().deriveFont(Font.BOLD, 24f));
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        pnlLabel.add(lblLogo);

        menuAddStudent.addActionListener(this);
        menuAddCourse.addActionListener(this);
        menuViewEnrollments.addActionListener(this);
        menuLogout.addActionListener(this);

        menu.add(menuAddStudent);
        menu.add(menuAddCourse);
        menu.add(menuViewEnrollments);
        menu.addSeparator();
        menu.add(menuLogout);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        pnlDetails.setLayout(new GridLayout(5, 2, 5, 5));
        pnlDetails.add(lblName);
        pnlDetails.add(txtName);
        pnlDetails.add(lblSurname);
        pnlDetails.add(txtSurname);
        pnlDetails.add(lblEmail);
        pnlDetails.add(txtEmail);
        pnlDetails.add(lblUsername);
        pnlDetails.add(txtUsername);
        pnlDetails.add(lblPassword);
        pnlDetails.add(txtPassword);

        pnlButton.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButton.add(btnSave);

        btnSave.setBackground(new Color(60, 178, 114));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 16));

        this.setJMenuBar(menuBar);
        this.add(pnlPage);

        btnSave.addActionListener(evt -> onSave());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        
        if (source == menuAddStudent) {
            AddStudentPage addStudentPage = new AddStudentPage();
            addStudentPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addStudentPage.setVisible(true);
        } else if (source == menuAddCourse) {
            AddCoursePage addCoursePage = new AddCoursePage();
            addCoursePage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addCoursePage.setVisible(true);
        } else if (source == menuViewEnrollments) {
            ViewEnrollmentsPage viewEnrollmentsPage = new ViewEnrollmentsPage();
            viewEnrollmentsPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewEnrollmentsPage.setVisible(true);
        } else if (source == menuLogout) {
            this.dispose();
            LoginPage loginPage = new LoginPage();
            loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginPage.setVisible(true);
        }
    }

    private void onSave() {
        String name = txtName.getText().trim();
        String surname = txtSurname.getText().trim();
        String email = txtEmail.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        
        String addStudentMsg = "ADDSTUDENT:" + name + "," + surname + "," + email + "," + username + "," + password;
        String response = Client.sendToServer(addStudentMsg);

        if (response == null) {
            response = "ERROR: No response from server.";
        }

        System.out.println("Server response: [" + response + "]");

        if (response.trim().startsWith("Enrollment confirmed for: ADDSTUDENT:")) {
            JOptionPane.showMessageDialog(this, "Student and user account created successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Error: " + response);
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtSurname.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
    }
}
