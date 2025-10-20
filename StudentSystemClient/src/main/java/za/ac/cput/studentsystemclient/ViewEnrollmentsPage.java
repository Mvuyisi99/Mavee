package za.ac.cput.studentsystemclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewEnrollmentsPage extends JFrame implements ActionListener {

    private JPanel pnlPage = new JPanel();
    private JPanel pnlLabel = new JPanel();
    private JPanel pnlBody = new JPanel();
    private JTextField txtCourseId = new JTextField(12);
    private JButton btnGet = new JButton("Get Enrollments");
    private JTextArea txtResults = new JTextArea(12, 50);

    public ViewEnrollmentsPage() {
        super("View Enrollments");
        pnlPage.setLayout(new BorderLayout());
        JLabel lbl = new JLabel("View Enrollments by Course ID");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 18f));
        pnlLabel.add(lbl);
        pnlPage.add(pnlLabel, BorderLayout.NORTH);

        JPanel p = new JPanel();
        p.add(new JLabel("Course ID:"));
        p.add(txtCourseId);
        p.add(btnGet);
        pnlPage.add(p, BorderLayout.CENTER);

        txtResults.setEditable(false);
        JScrollPane sp = new JScrollPane(txtResults);
        pnlBody.add(sp);
        pnlPage.add(pnlBody, BorderLayout.SOUTH);

        this.add(pnlPage);
        this.pack();
        this.setLocationRelativeTo(null);

        btnGet.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String courseId = txtCourseId.getText().trim();
        if (courseId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course ID.");
            return;
        }

        String msg = "GETENROLLMENTS:" + courseId;
        String resp = Client.sendToServer(msg);

        if (resp == null) {
            txtResults.setText("ERROR:No response from server.");
            return;
        }

        if (resp.startsWith("EMPTY:")) {
            txtResults.setText("No enrollments found for course " + courseId);
        } else if (resp.startsWith("ERROR:")) {
            JOptionPane.showMessageDialog(this, resp.substring(6));
            txtResults.setText("Error: " + resp.substring(6));
        } else if (resp.startsWith("SUCCESS:")) {
            
            String enrollmentsData = resp.substring(8); 
            if (enrollmentsData.isEmpty()) {
                txtResults.setText("No enrollments found for course " + courseId);
            } else {
                String[] rows = enrollmentsData.split("\\|\\|");
                StringBuilder out = new StringBuilder();
                for (String r : rows) {
                    out.append(r.trim()).append("\n");
                }
                txtResults.setText(out.toString());
            }
        } else {
            
            txtResults.setText("Unexpected response: " + resp);
        }
    }
}
