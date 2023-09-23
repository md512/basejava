package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private final String name;
    private final String website;
    private final List<Period> periods = new ArrayList<>();

    public Company(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public void addPeriod(String title, LocalDate startDate, LocalDate endDate, String description) {
        periods.add(new Period(title, startDate, endDate, description));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append(website).append("\n");
        for (Period period : periods) {
            sb.append(period.title).append("\n");
            sb.append(period.startDate.getMonth()).append(".").append(period.startDate.getYear());
            sb.append(" - ");
            sb.append(period.endDate.getMonth()).append(".").append(period.endDate.getYear()).append("\n");
            sb.append(period.description);
            if (!period.description.equals("")) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!Objects.equals(name, company.name)) return false;
        if (!Objects.equals(website, company.website)) return false;
        return periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + periods.hashCode();
        return result;
    }

    static class Period {
        private final String title;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String description;

        public Period(String title, LocalDate startDate, LocalDate endDate, String description) {
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
        }
    }
}