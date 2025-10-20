package za.ac.cput.studentsystemclient;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class AdminDashboard extends JFrame implements ActionListener {

    private JPanel pnlPage = new JPanel();
    private JPanel pnlLabel = new JPanel();
    private JPanel pnlTabs = new JPanel();

    private JLabel lblLogo = new JLabel("ADMIN DASHBOARD");
    private JLabel lblWelcome = new JLabel("WELCOME....... ADMIN!!!");
 
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Menu");
    
    private JMenuItem menuAddStudent = new JMenuItem("Add Student");
    private JMenuItem menuAddCourse = new JMenuItem("Add Course");
    private JMenuItem menuViewEnrollments = new JMenuItem("View Enrollments");
    private JMenuItem menuLogout = new JMenuItem("Logout");

    public AdminDashboard() {
        super("Student System");
        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        pnlPage.setLayout(new GridLayout(2, 1, 5, 5));
        pnlPage.add(pnlLabel);
        pnlPage.add(pnlTabs);

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

        pnlLabel.setLayout(new GridLayout(1, 1, 5, 5));
        pnlLabel.setBackground(new Color(173, 216, 230));
        lblLogo.setFont(lblLogo.getFont().deriveFont(Font.BOLD, 28f));
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        pnlLabel.add(lblLogo);

        pnlTabs.setLayout(new GridLayout(1, 1, 20, 20));
        lblWelcome.setFont(lblLogo.getFont().deriveFont(Font.BOLD, 28f));
        lblWelcome.setHorizontalAlignment(JLabel.CENTER);
        pnlTabs.add(lblWelcome);

        this.setJMenuBar(menuBar);
        this.add(pnlPage);

    }
@Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if ( source == menuAddStudent) {
            AddStudentPage addStudentPage = new AddStudentPage();
            addStudentPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addStudentPage.setVisible(true);
        } 
        else if ( source == menuAddCourse) {
            AddCoursePage addCoursePage = new AddCoursePage();
            addCoursePage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addCoursePage.setVisible(true);
        } 
        else if ( source == menuViewEnrollments) {
            ViewEnrollmentsPage viewEnrollmentsPage = new ViewEnrollmentsPage();
            viewEnrollmentsPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewEnrollmentsPage.setVisible(true);
        } 
        else if (source == menuLogout) {
            this.dispose();
            LoginPage loginPage = new LoginPage();
            loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginPage.setVisible(true);
        }

    }
}
