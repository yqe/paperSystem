package com.nju.paperSystem.entity;

public class modification {
    private int id;
    private int version;
    private String studentEmail;
    private String summary;
    private String description;
    private String date;
    private String fileName;
    private String teacherAdvice;
    private String teacherFileName;


    public modification() {
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTeacherAdvice() {
        return teacherAdvice;
    }

    public void setTeacherAdvice(String teacherAdvice) {
        this.teacherAdvice = teacherAdvice;
    }

    public String getTeacherFileName() {
        return teacherFileName;
    }

    public void setTeacherFileName(String teacherFileName) {
        this.teacherFileName = teacherFileName;
    }
}
