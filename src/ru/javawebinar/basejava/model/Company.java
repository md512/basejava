package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private final String companyName;
    private final String website;
    private final List<Period> periods = new ArrayList<>();

    public Company(String companyName, String website) {
        this.companyName = companyName;
        this.website = website;
    }

    public void addPeriod(String title, LocalDate startDate, LocalDate endDate, String description) {
        periods.add(new Period(title, startDate, endDate, description));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(companyName).append("\n");
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

    class Period {
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