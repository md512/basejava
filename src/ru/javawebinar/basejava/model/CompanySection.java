package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private final List<Company> companies;

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        return companies.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }
}
