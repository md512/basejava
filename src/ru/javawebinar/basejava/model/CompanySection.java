package ru.javawebinar.basejava.model;

import java.util.List;

public class CompanySection extends Section {
    private final List<Company> companies;

    public CompanySection(List<Company> companies) {
        this.companies = companies;
    }
}
