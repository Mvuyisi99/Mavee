package za.ac.cput.studentsystemclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddCoursePage extends JFrame implements ActionListener {

    private JPanel pnlPage = new JPanel();
    private JPanel pnlLabel = new JPanel();
    private JPanel pnlDetails = new JPanel();
    private JPanel pnlButton = new JPanel();

    private JLabel lblLogo = new JLabel("ADMIN");
    private JLabel lblCourseID = new JLabel("Course ID : ");
    private JLabel lblCourseName = new JLabel("Course Name : ");
    private JLabel lblCredits = new JLabel("Credits : ");

    private JTextField txtCourseID = new JTextField();
    private JTextField txtCourseName = new JTextField();
    private JTextField txtCredits = new JTextField();

    private JButton btnSave = new JButton("SAVE");

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Menu");
    private JMenuItem menuAddStudent = new JMenuItem("Add Student");
    private JMenuItem menuAddCourse = new JMenuItem("Add Course");
    private JMenuItem menuViewEnrollments = new JMenuItem("View Enrollments");
    private JMenuItem menuLogout = new JMenuItem("Logout");

    public AddCoursePage() {
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

        pnlDetails.setLayout(new GridLayout(3, 2, 5, 5));
        pnlDetails.add(lblCourseID);
        pnlDetails.add(txtCourseID);
        pnlDetails.add(lblCourseName);
        pnlDetails.add(txtCourseName);
        pnlDetails.add(lblCredits);
        pnlDetails.add(txtCredits);

        pnlButton.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButton.add(btnSave);

        btnSave.setBackground(new Color(60, 178, 114));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 16));

        this.setJMenuBar(menuBar);
        this.add(pnlPage);

        // attach save
        btnSave.addActionListener(evt -> onSave());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == menuAddStudent) {
            this.dispose();
            AddStudentPage addStudentPage = new AddStudentPage();
            addStudentPage.setVisible(true);
        } else if (source == menuAddCourse) {
            
        } else if (source == menuViewEnrollments) {
            this.dispose();
            ViewEnrollmentsPage viewEnrollmentsPage = new ViewEnrollmentsPage();
            viewEnrollmentsPage.setVisible(true);
        } else if (source == menuLogout) {
            this.dispose();
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        }
    }

    private void onSave() {
        String id = txtCourseID.getText().trim();
        String name = txtCourseName.getText().trim();
        String credits = txtCredits.getText().trim();
        if (id.isEmpty() || name.isEmpty() || credits.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Please enter Course ID and Course Name.");
            return;
        }
        
        String msg = "ADDCOURSE:" + id + "," + name+","+credits+",";
        String resp = Client.sendToServer(msg);
        if (resp == null) resp = "ERROR:No response from server.";
        if (resp.startsWith("SUCCESS:")) {
            JOptionPane.showMessageDialog(this, "Course added successfully.");
            txtCourseID.setText("");
            txtCourseName.setText("");
            txtCredits.setText("");
        } else if (resp.startsWith("ERROR:")) {
            JOptionPane.showMessageDialog(this, resp.substring(6));
        } else {
            JOptionPane.showMessageDialog(this, "Unexpected response: " + resp);
        }
    }
}
