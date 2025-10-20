
package com.mycompany.studentsystemserver;

/**
 *
 * @author 222830646
 */
public class Course {
     private String courseID;
    private String courseName;
    private String credits;

    public Course(String courseID, String courseName, String credits) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credits = credits;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
    
    
}
