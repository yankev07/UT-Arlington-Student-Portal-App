package com.segroup9.uta_stud.Helper;

/**
 * Created by kevinyanogo on 10/5/18.
 */

public class ContactInformation {

    public String uid;
    public String firstName;
    public String lastName;
    public String email;
    public String course1;
    public String course2;
    public String course3;


    public ContactInformation(){

    }

    public ContactInformation(String uid, String firstName, String lastName, String email, String course1, String course2, String course3) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.course1 = course1;
        this.course2 = course2;
        this.course3 = course3;
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

    public String getEmail() {
        return email;
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
}
