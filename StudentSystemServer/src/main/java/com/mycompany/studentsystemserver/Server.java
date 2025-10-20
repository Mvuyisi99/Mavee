package com.mycompany.studentsystemserver;

import com.mycompany.studentsystemserver.studentdao.StudentDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {

    public static void main(String[] args) throws SQLException {
        int port = 5000;
        StudentDAO studentDAO = new StudentDAO();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(" Server started. Listening on port " + port + "...");
            System.out.println(" Start your client now!");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(" Client connected: " + socket.getInetAddress());

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                    String received = reader.readLine();
                    System.out.println(" Received: " + received);

                    if (received != null && !received.isEmpty()) {
                        
                        String[] parts = received.split(":", 2);
                        String command = parts[0].trim();

                        System.out.println(" Command: " + command);

                        
                        if (parts.length == 1) {
                            if (command.equals("LISTCOURSES")) {
                                System.out.println(" LISTCOURSES command received!");
                                try {
                                    String coursesList = studentDAO.getAllCourses();
                                    System.out.println(" Courses: " + coursesList);
                                    if (coursesList != null && !coursesList.isEmpty()) {
                                        writer.println("SUCCESS:" + coursesList);
                                        System.out.println(" Courses sent to client");
                                    } else {
                                        writer.println("EMPTY:No courses available");
                                    }
                                } catch (Exception e) {
                                    writer.println("ERROR:Failed to load courses - " + e.getMessage());
                                    e.printStackTrace();
                                }
                            } else {
                                writer.println("ERROR:Unknown command: " + command);
                            }
                        } 
                        else if (parts.length >= 2) {
                            String dataPart = parts[1];
                            String[] data = dataPart.split(",");

                            if (command.equals("LOGIN")) {
                                
                                String[] loginParts = parts[1].split(":", 2); 

                                if (loginParts.length >= 2) {
                                    String userType = loginParts[0].trim(); 
                                    String[] credentials = loginParts[1].split(","); 

                                    if (credentials.length >= 2) {
                                        String username = credentials[0].trim();
                                        String password = credentials[1].trim();

                                        System.out.println(" Login - Type: " + userType + ", User: " + username);

                                        boolean loginSuccess = false;
                                        String userRole = "";

                                        if (userType.equals("STUDENT")) {
                                            loginSuccess = studentDAO.verifyStudentLogin(username, password);
                                            userRole = loginSuccess ? "STUDENT" : "";
                                        } else if (userType.equals("ADMIN")) {
                                            loginSuccess = studentDAO.verifyAdminLogin(username, password);
                                            userRole = loginSuccess ? "ADMIN" : "";
                                        }
                                        if (loginSuccess) {
                                            writer.println("SUCCESS:" + userRole);
                                            System.out.println(" Login successful: " + userRole + " - " + username);
                                        } else {
                                            writer.println("ERROR:Invalid credentials");
                                            System.out.println(" Login failed");
                                        }
                                    } else {
                                        writer.println("ERROR:Invalid login format - username,password required");
                                    }
                                } else {
                                    writer.println("ERROR:Invalid LOGIN format");
                                }
                            } else if (command.equals("ENROLL")) {
                                System.out.println("ENROLL command received");

                                if (data.length >= 2) {
                                    String studentUsername = data[0].trim();
                                    String courseId = data[1].trim();

                                    System.out.println("  Enrollment:");
                                    System.out.println("   Student: " + studentUsername);
                                    System.out.println("   Course: " + courseId);

                                    if (studentUsername.isEmpty() || courseId.isEmpty()) {
                                        writer.println("ERROR:Username and course ID required");
                                        return;
                                    }

                                    try {
                                        studentDAO.enrollStudent(studentUsername, courseId);
                                        writer.println("SUCCESS:Enrolled " + studentUsername + " in " + courseId);
                                        System.out.println(" Enrollment successful");
                                    } catch (SQLException e) {
                                        String errorMsg = e.getMessage();
                                        System.out.println(" Enrollment error: " + errorMsg);

                                        if (errorMsg.contains("not found")) {
                                            writer.println("ERROR:Student or course not found");
                                        } else if (errorMsg.contains("already enrolled")) {
                                            writer.println("ERROR:Already enrolled in this course");
                                        } else {
                                            writer.println("ERROR:Enrollment failed - " + errorMsg);
                                        }
                                        e.printStackTrace();
                                    }
                                } else {
                                    writer.println("ERROR:ENROLL:username,courseId");
                                }
                            } else if (command.equals("GETENROLLMENTS")) {
                                if (data.length >= 1) {
                                    String courseId = data[0].trim();
                                    System.out.println(" GETENROLLMENTS for course: " + courseId);

                                    try {
                                        String enrollments = studentDAO.getCourseEnrollments(courseId);
                                        
                                        if (enrollments != null && !enrollments.isEmpty()) {
                                            writer.println("SUCCESS:" + enrollments);  
                                            System.out.println(" Enrollments sent: " + enrollments);
                                        } else {
                                            writer.println("EMPTY:No enrollments found for this course");
                                        }
                                    } catch (SQLException e) {
                                        writer.println("ERROR:Failed to retrieve enrollments - " + e.getMessage());
                                        System.out.println(" Database error: " + e.getMessage());
                                        e.printStackTrace();
                                    }
                                } else {
                                    writer.println("ERROR:GETENROLLMENTS requires courseId");
                                }
                            } else if (command.equals("GETSTUDENTENROLLMENTS")) {
                                if (data.length >= 1) {
                                    String studentUsername = data[0].trim();
                                    System.out.println(" GETSTUDENTENROLLMENTS for student: " + studentUsername);

                                    String enrollments = studentDAO.getStudentEnrollments(studentUsername);
                                    if (enrollments != null && !enrollments.isEmpty()) {
                                        writer.println("SUCCESS:" + enrollments);  
                                        System.out.println("Student enrollments sent");
                                    } else {
                                        writer.println("EMPTY:No enrollments found");
                                    }
                                } else {
                                    writer.println("ERROR:GETSTUDENTENROLLMENTS requires username");
                                }
                            } else if (command.equals("ADDSTUDENT")) {
                                String[] studentData = data;
                                if (studentData.length >= 5) {
                                    String name = studentData[0].trim();
                                    String surname = studentData[1].trim();
                                    String email = studentData[2].trim();
                                    String username = studentData[3].trim();
                                    String password = studentData[4].trim();

                                    Student student = new Student(name, surname, email, username, password);
                                    studentDAO.saveStudentEnrolled(student);
                                    studentDAO.saveUserStudent(student);
                                    writer.println("SUCCESS:Student created");
                                } else {
                                    writer.println("ERROR:ADDSTUDENT needs 5 fields");
                                }
                            } else if (command.equals("ADDCOURSE")) {
                                if (data.length >= 3) {
                                    String id = data[0].trim();
                                    String name = data[1].trim();
                                    String credits = data[2].trim();

                                    Course course = new Course(id, name, credits);
                                    studentDAO.saveCourseEnroled(course);
                                    writer.println("SUCCESS:Course created");
                                } else {
                                    writer.println("ERROR:ADDCOURSE needs ID,Name,Credits");
                                }
                            } else {
                                writer.println("ERROR:Unknown command: " + command);
                            }
                        } else {
                            writer.println("ERROR:Malformed command");
                        }
                    } else {
                        writer.println("ERROR:Empty message");
                    }
                } catch (IOException e) {
                    System.out.println("IO Error: " + e.getMessage());
                }

                socket.close();
            }
        } catch (IOException e) {
            System.err.println(" Server failed to start on port " + port);
            e.printStackTrace();
        }

    }
}
