/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentEnrolDao;

import DbConnection.DBConnection;
import Details.SaveCourse;
import Details.SaveStudent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

/**
 *
 * @author Mvuyi
 */
public class StudentDao {

    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;

    public StudentDao() {
        try {
            this.con = DBConnection.derbyConnection();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void saveStudentEnrolled(SaveStudent student) {
        int ok;
        String sql = "INSERT INTO Students values ('" + student.getStudentNo() + "', '"
                + student.getName() + "','" + student.getLastName() + "','" + student.getPassword() + "')";
        try {
            stmt = this.con.createStatement();
            ok = stmt.executeUpdate(sql);
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "SQL Error: "
                    + sqlException.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

    public void saveCourseEnroled(SaveCourse course) {
        int ok;
        String sql = "INSERT INTO Course values ('" + course.getCourseID() + "', '"
                + course.getCourseName() + "','" + course.getCredits() + "')";
        try {
            stmt = this.con.createStatement();
            ok = stmt.executeUpdate(sql);
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "SQL Error: "
                    + sqlException.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

    public ResultSet readStudents(SaveStudent student) {
        String sql = "SELECT * FROM Students";
        ResultSet resultSet = null;
        try {
            stmt = this.con.createStatement();
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String studentNumber = resultSet.getString("StudentNo");
                String name = resultSet.getString("Name");
                String surname = resultSet.getString("LastName");
            }
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "SQL Error: "
                    + sqlException.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
        return resultSet;
    }

    public ResultSet readCourses(SaveCourse couse) {
        String sql = "SELECT * FROM Course";
        ResultSet resultSet = null;
        try {
            stmt = this.con.createStatement();
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String courseID = resultSet.getString("CourseID");
                String courseName = resultSet.getString("CourseName");
                String studentCredits = resultSet.getString("Credits");
            }
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "SQL Error: "
                    + sqlException.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
        return resultSet;
    }

    public void updateStudentEnrolled(SaveStudent student){
        int ok;
        String sql = "UPDATE Course SET name = ?, lastName = ?, password = ? WHERE StudentNo = ?";
        try {
            
            pstmt = this.con.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getPassword());
            pstmt.setString(4, student.getStudentNo());
            
            ok = pstmt.executeUpdate();
            pstmt.close(); 
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + sqlException.getMessage());
        }
    }
    
    public void updateCourseEnrolled(SaveCourse course) {
        int ok;
        String sql = "UPDATE Course SET courseName = ?, credits = ? WHERE courseID = ?";
        try {
            
            pstmt = this.con.prepareStatement(sql);
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCredits());
            pstmt.setString(3, course.getCourseID());

            ok = pstmt.executeUpdate();
            pstmt.close(); 
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + sqlException.getMessage());
        }
    }

}
