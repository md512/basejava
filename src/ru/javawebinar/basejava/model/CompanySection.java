package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection extends Section {
    private final List<Company> companies = new ArrayList<>();

    public void addCompany(Company company) {
        companies.add(company);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Company company : companies) {
            sb.append(company).append("\n");
        }
        return sb.toString();
    }
}
