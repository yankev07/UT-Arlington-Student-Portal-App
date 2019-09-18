package com.segroup9.uta_stud.Helper;

/**
 * Created by kevinyanogo on 9/29/18.
 */

public class UserInformation {

    public String uid;
    public String firstName;
    public String lastName;
    public String studentID;
    public String netID;
    public String email;
    public String password;
    public String course1;
    public String course2;
    public String course3;
    public String professor1;
    public String professor2;
    public String professor3;
    public String status;

    public UserInformation(){

    }

    public UserInformation(String uid, String firstName, String lastName, String studentID, String netID, String email, String password, String course1, String course2, String course3, String professor1, String professor2, String professor3, String status) {

        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.netID = netID;
        this.email = email;
        this.password = password;
        this.course1 = course1;
        this.course2 = course2;
        this.course3 = course3;
        this.professor1 = professor1;
        this.professor2 = professor2;
        this.professor3 = professor3;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getNetID() {
        return netID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCourse1() {
        return course1;
    }

    public String getCourse2() {
        return course2;
    }

    public String getCourse3() {
        return course3;
    }

    public String getProfessor1() {
        return professor1;
    }

    public String getProfessor2() {
        return professor2;
    }

    public String getProfessor3() {
        return professor3;
    }

    public String getStatus() {
        return status;
    }
}
