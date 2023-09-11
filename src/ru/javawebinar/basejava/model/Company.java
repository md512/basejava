package ru.javawebinar.basejava.model;

import java.util.Date;

public class Company {
    private String name;
    private String website;
    private Date beginDate;
    private Date endDate;
    private String description;

    public Company(String name, String website, Date beginDate, Date endDate, String description) {
        this.name = name;
        this.website = website;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.description = description;
    }
}