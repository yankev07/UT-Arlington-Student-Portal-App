package com.segroup9.uta_stud.Helper;

/**
 * Created by kevinyanogo on 10/20/18.
 */

public class RatingInformation {

    public String courseName;
    public String professorName;
    public String courseContent;
    public String courseTeaching;
    public String courseHomeworks;
    public String courseExams;
    public String courseRecommendation;
    public String personalComments;
    public int overallRating;
    public String author;

    public RatingInformation(){

    }

    public RatingInformation(String courseName, String professorName, String courseContent, String courseTeaching, String courseHomeworks, String courseExams, String courseRecommendation, String personalComments, int overallRating, String author) {
        this.courseName = courseName;
        this.professorName = professorName;
        this.courseContent = courseContent;
        this.courseTeaching = courseTeaching;
        this.courseHomeworks = courseHomeworks;
        this.courseExams = courseExams;
        this.courseRecommendation = courseRecommendation;
        this.personalComments = personalComments;
        this.overallRating = overallRating;
        this.author = author;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public String getCourseTeaching() {
        return courseTeaching;
    }

    public String getCourseHomeworks() {
        return courseHomeworks;
    }

    public String getCourseExams() {
        return courseExams;
    }

    public String getCourseRecommendation() {
        return courseRecommendation;
    }

    public String getPersonalComments() {
        return personalComments;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public String getAuthor() {
        return author;
    }
}
