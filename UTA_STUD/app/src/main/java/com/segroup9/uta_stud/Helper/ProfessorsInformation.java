package com.segroup9.uta_stud.Helper;

/**
 * Created by kevinyanogo on 11/15/18.
 */

public class ProfessorsInformation {

    public String professorName;
    public String assistantName;
    public String professorOffice;
    public String assistantOffice;
    public String professorEmail;
    public String assistantEmail;
    public String professorPhone;
    public String assistantPhone;
    public String professorHours;
    public String assistantHours;

    public ProfessorsInformation(){

    }

    public ProfessorsInformation(String professorName, String assistantName, String professorOffice, String assistantOffice, String professorEmail, String assistantEmail, String professorPhone, String assistantPhone, String professorHours, String assistantHours) {
        this.professorName = professorName;
        this.assistantName = assistantName;
        this.professorOffice = professorOffice;
        this.assistantOffice = assistantOffice;
        this.professorEmail = professorEmail;
        this.assistantEmail = assistantEmail;
        this.professorPhone = professorPhone;
        this.assistantPhone = assistantPhone;
        this.professorHours = professorHours;
        this.assistantHours = assistantHours;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getAssistantName() {
        return assistantName;
    }

    public String getProfessorOffice() {
        return professorOffice;
    }

    public String getAssistantOffice() {
        return assistantOffice;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public String getAssistantEmail() {
        return assistantEmail;
    }

    public String getProfessorPhone() {
        return professorPhone;
    }

    public String getAssistantPhone() {
        return assistantPhone;
    }

    public String getProfessorHours() {
        return professorHours;
    }

    public String getAssistantHours() {
        return assistantHours;
    }
}
