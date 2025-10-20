/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentsystemserver.studentdao;

import com.mycompany.studentsystemserver.*;
import com.mycompany.studentsystemserver.Course;
import com.mycompany.studentsystemserver.DBConnection;
import com.mycompany.studentsystemserver.Student;
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
public class StudentDAO {

    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;

    public StudentDAO() {
        try {
            this.con = DBConnection.derbyConnection();
            System.out.println("Database connection established: " + con);
            con.setAutoCommit(true); 
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Connection Error: " + exception.getMessage());
            System.out.println("Connection Error: " + exception.getMessage());
        }
    }

    public void saveStudentEnrolled(Student student) {
        if (con == null) {
            System.out.println("Connection is null, reinitializing...");
            try {
                con = DBConnection.derbyConnection();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reinitialize connection: " + e.getMessage());
                return;
            }
        }
        String sql = "INSERT INTO Student (Name, SurName, Email, UserName, Password) VALUES (?, ?, ?, ?, ?)";
        System.out.println("Executing SQL: " + sql + " with values: Name=" + student.getName() + ", SurName=" + student.getSurName() + ", Email=" + student.getEmail() + ", UserName=" + student.getUserName() + ", Password=" + student.getPassword());
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getSurName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getUserName());
            pstmt.setString(5, student.getPassword());
            int ok = pstmt.executeUpdate();
            System.out.println("Insert successful, rows affected: " + ok);
        } catch (SQLException sqlException) {
            System.out.println("SQL Error: " + sqlException.getMessage());
            JOptionPane.showMessageDialog(null, "SQL Error: " + sqlException.getMessage());
        }
    }

    public void saveUserStudent(Student student) {
        if (con == null) {
            System.out.println("Connection is null, reinitializing...");
            try {
                con = DBConnection.derbyConnection();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reinitialize connection: " + e.getMessage());
                return;
            }
        }
        String sql = "INSERT INTO Users (UserName, Password) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, student.getUserName());
            pstmt.setString(2, student.getPassword());
            int ok = pstmt.executeUpdate();
            System.out.println("Insert successful, rows affected: " + ok);
        } catch (SQLException sqlException) {
            System.out.println("SQL Error: " + sqlException.getMessage());
            JOptionPane.showMessageDialog(null, "SQL Error: " + sqlException.getMessage());
        }

    }

    public boolean verifyStudentLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM Users u JOIN Student s ON u.UserName = s.UserName "
                + "WHERE u.UserName = ? AND u.Password = ? AND s.UserName IS NOT NULL";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Login verification error: " + e.getMessage());
        }
        return false;
    }

    public boolean verifyAdminLogin(String username, String password) {
        
        String sql = "SELECT COUNT(*) FROM Admins WHERE Username = ? AND Password = ?";

        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Admin login verification error: " + e.getMessage());
        }
        return false;
    }

    public void saveCourseEnroled(Course course) {
        if (con == null) {
            System.out.println("Connection is null, reinitializing...");
            try {
                con = DBConnection.derbyConnection();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reinitialize connection: " + e.getMessage());
                return;
            }
        }
        String sql = "INSERT INTO Course (CourseID, CourseName, Credits) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseID());
            pstmt.setString(2, course.getCourseName());
            pstmt.setString(3, course.getCredits());
            int ok = pstmt.executeUpdate();
            System.out.println("Insert successful, rows affected: " + ok);
        } catch (SQLException sqlException) {
            System.out.println("SQL Error: " + sqlException.getMessage());
            JOptionPane.showMessageDialog(null, "SQL Error: " + sqlException.getMessage());
        }
    }

    public ResultSet readStudents(Student student) {
        String sql = "SELECT * FROM Student";
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

    public String getAllCourses() throws SQLException {
        StringBuilder courses = new StringBuilder();
        String sql = "SELECT CourseID, CourseName, Credits FROM Course";

        System.out.println("Executing: " + sql); 

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            int count = 0;
            while (rs.next()) {
                count++;
                String courseID = rs.getString("CourseID");
                String courseName = rs.getString("CourseName");
                String credits = rs.getString("Credits");

                System.out.println("Course " + count + ": " + courseID + " - " + courseName); 

                if (count > 1) {
                    courses.append("||");
                }
                courses.append(courseID).append("||")
                        .append(courseName != null ? courseName : "").append("||")
                        .append(credits != null ? credits : "0");
            }

            System.out.println("Total courses found: " + count);
            return count > 0 ? courses.toString() : "";

        } catch (SQLException e) {
            System.out.println("SQL Error in getAllCourses: " + e.getMessage());
            throw e;
        }
    }

    public void updateStudentEnrolled(Student student) {
        int ok;
        String sql = "UPDATE Course SET name = ?, lastName = ?, password = ? WHERE StudentNo = ?";
        try {

            pstmt = this.con.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getSurName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPassword());

            ok = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + sqlException.getMessage());
        }
    }

    public void updateCourseEnrolled(Course course) {
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

    public void enrollStudent(String studentUsername, String courseId) throws SQLException {
        System.out.println("=== Starting enrollment process ===");
        System.out.println("Student Username: " + studentUsername);
        System.out.println("Course ID: " + courseId);

        
        String studentSql = "SELECT Name, SurName FROM Student WHERE UserName = ?";
        String studentName = "";
        String studentSurname = "";

        try (PreparedStatement studentStmt = con.prepareStatement(studentSql)) {
            studentStmt.setString(1, studentUsername);
            ResultSet studentRs = studentStmt.executeQuery();

            if (studentRs.next()) {
                studentName = studentRs.getString("Name");
                studentSurname = studentRs.getString("SurName");
                System.out.println("Found student: " + studentName + " " + studentSurname);
            } else {
                throw new SQLException("Student not found with username: " + studentUsername);
            }
        }

        
        String courseSql = "SELECT CourseName FROM Course WHERE CourseID = ?";
        String courseName = "";

        try (PreparedStatement courseStmt = con.prepareStatement(courseSql)) {
            courseStmt.setString(1, courseId);
            ResultSet courseRs = courseStmt.executeQuery();

            if (courseRs.next()) {
                courseName = courseRs.getString("CourseName");
                System.out.println("Found course: " + courseId + " - " + courseName);
            } else {
                throw new SQLException("Course not found with ID: " + courseId);
            }
        }

        
        String checkEnrollmentSql = """
        SELECT COUNT(*) FROM ViewEnrollment 
        WHERE Name = ? AND Surname = ? AND CourseID = ?
        """;

        try (PreparedStatement checkStmt = con.prepareStatement(checkEnrollmentSql)) {
            checkStmt.setString(1, studentName);
            checkStmt.setString(2, studentSurname);
            checkStmt.setString(3, courseId);
            ResultSet checkRs = checkStmt.executeQuery();

            if (checkRs.next() && checkRs.getInt(1) > 0) {
                throw new SQLException("Student is already enrolled in this course");
            }
        }

        
        String enrollmentSql = """
        INSERT INTO ViewEnrollment (Name,Surname, CourseID, CourseName) 
        VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement enrollStmt = con.prepareStatement(enrollmentSql)) {
            enrollStmt.setString(1, studentName);
            enrollStmt.setString(2, studentSurname);
            enrollStmt.setString(3, courseId);
            enrollStmt.setString(4, courseName);

            int rowsAffected = enrollStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(" Enrollment successful!");
                System.out.println("Stored: " + studentName + " " + studentSurname
                        + " in " + courseName + " (ID: " + courseId + ")");
            } else {
                throw new SQLException("Enrollment failed - no rows inserted");
            }
        }

        System.out.println("=== Enrollment process completed ===");
    }

    public String getCourseEnrollments(String courseId) throws SQLException {
    System.out.println("Fetching enrollments for course: " + courseId);

    StringBuilder enrollments = new StringBuilder();
    String sql = """
        SELECT Name, Surname, CourseName 
        FROM ViewEnrollment 
        WHERE CourseID = ?
        """;

    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, courseId);
        ResultSet rs = pstmt.executeQuery();

        int count = 0;
        while (rs.next()) {
            count++;
            String name = rs.getString("Name");
            String surname = rs.getString("Surname");
            String courseName = rs.getString("CourseName");

            String enrollmentInfo = name + " " + surname + " - " + courseName;

            if (count > 1) {
                enrollments.append("||");
            }
            enrollments.append(enrollmentInfo);

            System.out.println("Enrollment " + count + ": " + enrollmentInfo);
        }

        System.out.println("Found " + count + " enrollments for course " + courseId);
        return count > 0 ? enrollments.toString() : "";
    }
}
    public String getStudentEnrollments(String studentUsername) throws SQLException {
    System.out.println("Fetching enrollments for student username: " + studentUsername);
    
    StringBuilder enrollments = new StringBuilder();
    String sql = """
        SELECT ve.Name, ve.Surname, ve.CourseID, ve.CourseName 
        FROM ViewEnrollment ve
        JOIN Student s ON ve.Name = s.Name AND ve.Surname = s.SurName
        WHERE s.UserName = ?
        ORDER BY ve.CourseName
        """;
    
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, studentUsername);
        ResultSet rs = pstmt.executeQuery();
        
        int count = 0;
        while (rs.next()) {
            count++;
            String name = rs.getString("Name");
            String surname = rs.getString("Surname");
            String courseId = rs.getString("CourseID");
            String courseName = rs.getString("CourseName");
            
            String enrollmentInfo = name + " " + surname + " - " + courseId + " (" + courseName + ")";
            
            if (count > 1) {
                enrollments.append("||");
            }
            enrollments.append(enrollmentInfo);
            
            System.out.println("Enrollment " + count + ": " + enrollmentInfo);
        }
        
        System.out.println("Found " + count + " enrollments for student " + studentUsername);
        return count > 0 ? enrollments.toString() : "";
    }
}

}
