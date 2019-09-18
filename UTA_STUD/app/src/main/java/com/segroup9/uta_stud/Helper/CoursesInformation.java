package com.segroup9.uta_stud.Helper;

/**
 * Created by kevinyanogo on 11/15/18.
 */

public class CoursesInformation {

    public String courseName;
    public String courseTiming;
    public String courseRoom;

    public CoursesInformation(){

    }

    public CoursesInformation(String courseName, String courseTiming, String courseRoom) {
        this.courseName = courseName;
        this.courseTiming = courseTiming;
        this.courseRoom = courseRoom;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTiming() {
        return courseTiming;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

}
