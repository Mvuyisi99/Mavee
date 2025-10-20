
package com.mycompany.studentsystemserver;

/**
 *
 * @author 222830646
 */
public class Student {
    
     private String Name;
      private String surName;
      private String Email;
      private String userName;
      private String password;

    public Student(String Name, String surName, String Email, String userName, String password) {
        this.Name = Name;
        this.surName = surName;
        this.Email = Email;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}