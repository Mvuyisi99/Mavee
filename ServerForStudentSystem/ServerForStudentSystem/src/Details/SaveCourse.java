/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Details;

/**
 *
 * @author Mvuyi
 */
public class SaveCourse {

    private String courseID;
    private String courseName;
    private String credits;

    public SaveCourse() {
    }

    public SaveCourse(String courseID, String courseName, String credits) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credits = credits;
    }
     public String getCourseID(){
         return this.courseID;
     }
     public String getCourseName(){
         return this.courseName;
     }
     public String getCredits(){
         return this.credits;
     }
     public void setCourseID(String courseID){
         this.courseID =  courseID;
     }
     public void setCourseName(String courseName){
         this.courseName =  courseName;
     }
     public void setCredits(String credits){
         this.credits =  credits;
     }
}
