package za.ac.cput.studentsystemclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.ListSelectionModel;

public class StudentPage extends JFrame implements ActionListener {

    private JPanel pnlPage = new JPanel();
    private JPanel pnlLabel = new JPanel();
    private JPanel pnlChoose = new JPanel();
    private JPanel pnlDisplay = new JPanel();
    private JPanel pnlButton = new JPanel();

    private JLabel lblLogo = new JLabel("STUDENT");

    private JTextField txtDisplay = new JTextField();

    private JTable tblCourse = new JTable();
    private DefaultTableModel modelCourse = new DefaultTableModel();

    private JButton btnEnroll = new JButton("Enroll");
    private JButton btnDisplay = new JButton("Display Enrollments");

    private String studentId; 

   
    public StudentPage(String loggedInUsername) {
        super("Student System");
        this.studentId = loggedInUsername; 
        System.out.println("StudentPage created for user: " + studentId);

        
        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        pnlPage.setLayout(new GridLayout(4, 1, 5, 5));
        pnlPage.add(pnlLabel);
        pnlPage.add(pnlChoose);
        pnlPage.add(pnlDisplay);
        pnlPage.add(pnlButton);

        pnlLabel.setLayout(new GridLayout(1, 1, 5, 5));
        pnlLabel.setBackground(new Color(173, 216, 230));
        lblLogo.setFont(lblLogo.getFont().deriveFont(Font.BOLD, 24f));
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        pnlLabel.add(lblLogo);

        pnlChoose.setLayout(new GridLayout(1, 1, 5, 5));
        modelCourse.addColumn("Course ID");
        modelCourse.addColumn("Course Name");
        modelCourse.addColumn("Credits");

        tblCourse.setModel(modelCourse);
        tblCourse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tblCourse);
        pnlChoose.add(scrollPane);

        pnlDisplay.setLayout(new GridLayout(1, 1, 5, 5));
        pnlDisplay.add(txtDisplay);
        txtDisplay.setEditable(false);
        txtDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDisplay.setBackground(new Color(245, 245, 245));
        txtDisplay.setBorder(new EmptyBorder(5, 10, 5, 10));

        pnlButton.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnEnroll.setPreferredSize(new java.awt.Dimension(200, 50));
        btnDisplay.setPreferredSize(new java.awt.Dimension(200, 50));

        btnEnroll.setBackground(new Color(100, 149, 237));
        btnEnroll.setForeground(Color.WHITE);
        btnEnroll.setFocusPainted(false);
        btnEnroll.setFont(new Font("SansSerif", Font.BOLD, 16));

        btnDisplay.setBackground(new Color(60, 179, 113));
        btnDisplay.setForeground(Color.WHITE);
        btnDisplay.setFocusPainted(false);
        btnDisplay.setFont(new Font("SansSerif", Font.BOLD, 16));

        pnlButton.add(btnEnroll);
        pnlButton.add(btnDisplay);

        this.add(pnlPage);

        btnEnroll.addActionListener(this);
        btnDisplay.addActionListener(this);

        
        loadCoursesFromServer();
    }

    
    public StudentPage() {
        this("testStudent"); 
    }

    private void loadCoursesFromServer() {
        System.out.println("Loading courses from server..."); 
        modelCourse.setRowCount(0);

        String resp = Client.sendToServer("LISTCOURSES");
        System.out.println("Raw server response: '" + resp + "'"); 

        if (resp == null) {
            txtDisplay.setText("ERROR: No response from server.");
            return;
        }

        if (resp.startsWith("EMPTY:")) {
            txtDisplay.setText("No courses available.");
            return;
        }

        if (resp.startsWith("ERROR:")) {
            txtDisplay.setText("Server error: " + resp.substring(6));
            return;
        }

        if (resp.startsWith("SUCCESS:")) {
            String coursesData = resp.substring(8); 
            System.out.println("Courses data to parse: '" + coursesData + "'"); 

            if (coursesData.isEmpty()) {
                txtDisplay.setText("No courses found.");
                return;
            }

            
            String[] parts = coursesData.split("\\|\\|");
            System.out.println("Number of parts: " + parts.length);

            for (int i = 0; i < parts.length - 2; i += 3) {
                String courseID = parts[i].trim();
                String courseName = (i + 1 < parts.length) ? parts[i + 1].trim() : "";
                String credits = (i + 2 < parts.length) ? parts[i + 2].trim() : "0";

                System.out.println("Parsed course: " + courseID + " | " + courseName + " | " + credits);
                modelCourse.addRow(new Object[]{courseID, courseName, credits});
            }

            txtDisplay.setText(modelCourse.getRowCount() + " courses loaded.");
        } else {
            txtDisplay.setText("Unexpected response: " + resp);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnEnroll) {
            int selectedRow = tblCourse.getSelectedRow();
            if (selectedRow != -1) {
                String courseID = tblCourse.getValueAt(selectedRow, 0).toString();
                String courseName = tblCourse.getValueAt(selectedRow, 1).toString();

                
                String message = "ENROLL:" + studentId + "," + courseID;
                System.out.println("Sending enrollment request: " + message);

                String response = Client.sendToServer(message);

                if (response != null && response.startsWith("SUCCESS:")) {
                    JOptionPane.showMessageDialog(this,
                            "Successfully enrolled in " + courseName + "!");
                    txtDisplay.setText(response);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Enrollment failed: " + (response != null ? response.substring(6) : "No response"));
                    txtDisplay.setText(response == null ? "No response" : response);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course first.");
            }
        } else if (src == btnDisplay) {
            String message = "GETSTUDENTENROLLMENTS:" + studentId;
            System.out.println("Requesting enrollments for: " + studentId);

            String resp = Client.sendToServer(message);

            if (resp == null) {
                txtDisplay.setText("ERROR: No response from server.");
            } else if (resp.startsWith("EMPTY:")) {
                txtDisplay.setText("No enrollments found for you.");
            } else if (resp.startsWith("ERROR:")) {
                txtDisplay.setText("Error: " + resp.substring(6));
                JOptionPane.showMessageDialog(this, resp.substring(6));
            } else if (resp.startsWith("SUCCESS:")) {
                
                String enrollmentsData = resp.substring(8);
                if (enrollmentsData.isEmpty()) {
                    txtDisplay.setText("No enrollments found.");
                } else {
                    
                    String[] rows = enrollmentsData.split("\\|\\|");
                    StringBuilder out = new StringBuilder("Your Enrollments:\n\n");
                    for (String r : rows) {
                        out.append(r.trim()).append("\n");
                    }
                    txtDisplay.setText(out.toString());
                    System.out.println("Displayed " + rows.length + " enrollments");
                }
            } else {
                txtDisplay.setText("Unexpected response: " + resp);
            }
        }
    }
}
