package com.skangude5.classmates.model;

import java.util.ArrayList;

public class User {
    private String full_name;
    private String college_name;
    private String year_of_study;
    private String department;
    private Integer profile_pic;
    private ArrayList<Badge> badges;

    public User(String full_name, String college_name, String year_of_study, String department, Integer profile_pic, ArrayList<Badge> badges) {
        this.full_name = full_name;
        this.college_name = college_name;
        this.year_of_study = year_of_study;
        this.department = department;
        this.profile_pic = profile_pic;
        this.badges = badges;
    }
    public void setUser(String full_name, String college_name, String year_of_study, String department, Integer profile_pic, ArrayList<Badge> badges) {
        this.full_name = full_name;
        this.college_name = college_name;
        this.year_of_study = year_of_study;
        this.department = department;
        this.profile_pic = profile_pic;
        this.badges = badges;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getYear_of_study() {
        return year_of_study;
    }

    public void setYear_of_study(String year_of_study) {
        this.year_of_study = year_of_study;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(Integer profile_pic) {
        this.profile_pic = profile_pic;
    }

    public ArrayList<Badge> getBadges() {
        return badges;
    }

    public void setBadges(ArrayList<Badge> badges) {
        this.badges = badges;
    }
}
