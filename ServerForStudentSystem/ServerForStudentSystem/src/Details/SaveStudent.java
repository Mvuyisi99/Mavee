package Details;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mvuyi
 */
public class SaveStudent {
    
      private String studentNo;
      private String name;
      private String lastName;
      private String password;
      
      public SaveStudent(){
      }
      public SaveStudent(String studentNo,String name,String lastName,String password){
          this.studentNo = studentNo;
          this.name = name;
          this.lastName = lastName;
          this.password = password;
      }
       public String getStudentNo(){
        return this.studentNo ;
    }
       public String getName(){
        return this.name ;
    } 
      public String getLastName(){
        return this.lastName ;
    }  
      public String getPassword(){
        return this.password ;
    } 
      public void setStudentNo(String studentNo){
         this.studentNo = studentNo ;
    }
      public void setName(String name){
         this.name = name ;
    }
     public void setLastName(String lastName){
         this.lastName = lastName ;
    } 
     public void setPassword(String password){
         this.password = password ;
    }
}
