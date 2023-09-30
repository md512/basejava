package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

public class ResumeTestData {
    public static Resume fill(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "123456");
        resume.addContact(ContactType.EMAIL, "test@mail.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/");
        resume.addContact(ContactType.GITHUB, "https://github.com/");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/");
        resume.addContact(ContactType.WEBSITE, "http://topjava.ru/");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Software developer"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Personal qualities"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<>(Arrays.asList("Achievement1", "Achievement2"))));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList("Qualification1", "Qualification2"))));

        Company company1 = new Company("Company1", "company1.com", DateUtil.of(2010, Month.JULY), DateUtil.of(2015, Month.MARCH), "Tester", "Description1");
        Company company2 = new Company("Company2", "company2.com", DateUtil.of(2015, Month.APRIL), DateUtil.of(2020, Month.DECEMBER), "Developer", "Description2");
        resume.addSection(SectionType.EXPERIENCE, new CompanySection(new ArrayList<>(Arrays.asList(company1, company2))));

        Company university = new Company("University", "test1.com", DateUtil.of(2005, Month.SEPTEMBER), DateUtil.of(2010, Month.JUNE), "Student", null);
        Company courses = new Company("Course", "test2.com", DateUtil.of(2010, Month.JULY), DateUtil.of(2010, Month.DECEMBER), "Java", null);
        courses.addPeriod(DateUtil.of(2011, Month.JANUARY), DateUtil.of(2011, Month.FEBRUARY), "SQL", null);
        resume.addSection(SectionType.EDUCATION, new CompanySection(new ArrayList<>(Arrays.asList(university, courses))));

        return resume;
    }
}
