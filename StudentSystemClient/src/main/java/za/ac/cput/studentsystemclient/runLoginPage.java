package za.ac.cput.studentsystemclient;

import javax.swing.JFrame;

/**
 *
 * @author 222830646
 */
public class runLoginPage {

    public static void main(String[] args) {
        LoginPage guiObject = new LoginPage();
        guiObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiObject.setBounds(100, 200, 700, 500);
        guiObject.setVisible(true);
    }
}