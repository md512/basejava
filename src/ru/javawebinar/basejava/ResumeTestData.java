package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.WEBSITE, "http://gkislin.ru/");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по " +
                "Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры."));

        ListSection achivements = new ListSection();
        achivements.addToList("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения " +
                "автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring " +
                "Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achivements.addToList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achivements.addToList("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.addSection(SectionType.ACHIEVEMENT, achivements);

        ListSection qualifications = new ListSection();
        qualifications.addToList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.addToList("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addToList("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        resume.addSection(SectionType.QUALIFICATIONS, qualifications);

        CompanySection workExperience = new CompanySection();
        Company alcatel = new Company("Alcatel", "http://www.alcatel.ru/");
        alcatel.addPeriod("Инженер по аппаратному и программному тестированию",
                LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1),
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        workExperience.addCompany(alcatel);
        Company siemens = new Company("Siemens AG", "https://www.siemens.com/ru/ru/home.html");
        siemens.addPeriod("Разработчик ПО",
                LocalDate.of(2005, 1, 1),
                LocalDate.of(2007, 2, 1),
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на " +
                        "мобильной IN платформе Siemens @vantage (Java, Unix).");
        workExperience.addCompany(siemens);
        Company enkarta = new Company("Enkarta", "http://enkata.com/");
        enkarta.addPeriod("Разработчик ПО",
                LocalDate.of(2007, 3, 1),
                LocalDate.of(2008, 6, 1),
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                        "частей кластерного J2EE приложения (OLAP, Data mining).");
        workExperience.addCompany(enkarta);
        resume.addSection(SectionType.EXPERIENCE, workExperience);

        CompanySection education = new CompanySection();

        Company schoolMfti = new Company("Заочная физико-техническая школа при МФТИ", "https://mipt.ru/");
        schoolMfti.addPeriod("Закончил с отличием",
                LocalDate.of(1984, 9, 1),
                LocalDate.of(1987, 6, 1),
                "");
        education.addCompany(schoolMfti);
        Company itmo = new Company("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "http://www.ifmo.ru/");
        itmo.addPeriod("Инженер (программист Fortran, C)",
                LocalDate.of(1987, 9, 1),
                LocalDate.of(1993, 7, 1),
                "");
        itmo.addPeriod("Аспирантура (программист С, С++)",
                LocalDate.of(1993, 9, 1),
                LocalDate.of(1996, 7, 1),
                "");
        education.addCompany(itmo);
        Company alcatelEdu = new Company("Alcatel", "http://www.alcatel.ru/");
        alcatelEdu.addPeriod("6 месяцев обучения цифровым телефонным сетям (Москва)",
                LocalDate.of(1997, 6, 1),
                LocalDate.of(1998, 3, 1),
                "");
        education.addCompany(alcatelEdu);
        resume.addSection(SectionType.EDUCATION, education);

        System.out.println(resume.getFullName());
        System.out.println();

        for (ContactType contactType : ContactType.values()) {
            String contact = resume.getContacts().get(contactType);
            if (contact != null) {
                System.out.println(contactType.getTitle() + ": " + contact);
            }
        }
        System.out.println();

        for (SectionType sectionType : SectionType.values()) {
            Section section = resume.getSections().get(sectionType);
            if (section != null) {
                System.out.println(sectionType.getTitle());
                System.out.println(section);
                System.out.println();
            }
        }
    }
}
