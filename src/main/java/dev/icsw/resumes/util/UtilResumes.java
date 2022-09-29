package dev.icsw.resumes.util;

import dev.icsw.resumes.model.*;

import java.time.Month;

public class UtilResumes {
    public static Resume fillWithNumberUuidName(int num) {
        return new Resume(
                "uuid-------------------------------" + num,
                "Name-" + num + " Surname-" + num
        );
    }

    public static Resume fillWithNumberContacts(int num) {
        Resume resume = fillWithNumberUuidName(num);

        // Fill Contacts
        resume.addContact(ContactType.PHONE, "+7 (" + num + num + num + ")" + " " + num + num + num + "-" + num + num + num + num);
        resume.addContact(ContactType.SKYPE, "skype" + "-" + num);
        resume.addContact(ContactType.EMAIL, "email" + "-" + num + "@domain" + "-" + num + ".me");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/" + "name" + "-" + num + "surname" + "-" + num);
        resume.addContact(ContactType.GITHUB, "https://github.com/" + "name" + "-" + num + "surname" + "-" + num);
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/" + num + num + num + num + num + num);
        resume.addContact(ContactType.HOMEPAGE, "https://" + "@domain" + "-" + num + ".me");

        return resume;
    }


    public static Resume fillWithAllButOrganization(int num) {
        Resume resume = fillWithNumberContacts(num);

        // Fill Objective
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Objective-" + num));

        // Fill Personal
        resume.addSection(SectionType.PERSONAL, new TextSection("Personal-Feature-" + num));

        // Fill Achievements
        ListSection achievementsSection = new ListSection(new String[]{
                "Achievement-" + num + "-" + 1,
                "Achievement-" + num + "-" + 2,
                "Achievement-" + num + "-" + 3
        });
        resume.addSection(SectionType.ACHIEVEMENTS, achievementsSection);

        // Fill QUALIFICATIONS
        ListSection qualificationsSection = new ListSection(new String[]{
                "Qualification-" + num + "-" + 1,
                "Qualification-" + num + "-" + 2,
                "Qualification-" + num + "-" + 3
        });
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);

        return resume;
    }

    public static Resume fillWithNumber(int num) {
        Resume resume = fillWithNumberContacts(num);

        // Fill Objective
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Objective-" + num));

        // Fill Personal
        resume.addSection(SectionType.PERSONAL, new TextSection("Personal-Feature-" + num));

        // Fill Achievements
        ListSection achievementsSection = new ListSection(new String[]{
                "Achievement-" + num + "-" + 1,
                "Achievement-" + num + "-" + 2,
                "Achievement-" + num + "-" + 3
        });
        resume.addSection(SectionType.ACHIEVEMENTS, achievementsSection);

        // Fill QUALIFICATIONS
        ListSection qualificationsSection = new ListSection(new String[]{
                "Qualification-" + num + "-" + 1,
                "Qualification-" + num + "-" + 2,
                "Qualification-" + num + "-" + 3
        });
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);

        // Fill EXPERIENCE
        Organization[] experienceOrganizations = {
                new Organization("Employer-" + num + "-" + 1, "https://employmer" + "-" + num + "-" + 1 + ".com/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        2022, Month.JANUARY,
                                        "Org-" + num + "-1" + "-Employment-Activity-1" + "-Title",
                                        "Org-" + num + "-1" + "-Employment-Activity-1" + "-Description"
                                ),
                                new Organization.Activity(
                                        2021, Month.JANUARY, 2021, Month.DECEMBER,
                                        "Org-" + num + "-1" + "-Employment-Activity-2" + "-Title",
                                        "Org-" + num + "-1" + "-Employment-Activity-2" + "-Description"
                                )
                        }
                ),
                new Organization("Employer-" + num + "-" + 2, null,
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        2020, Month.JANUARY, 2020, Month.DECEMBER,
                                        "Org-" + num + "-2" + "-Employment-Activity-1" + "-Title",
                                        "Org-" + num + "-2" + "-Employment-Activity-1" + "-Description"
                                ),
                                new Organization.Activity(
                                        2019, Month.JANUARY, 2019, Month.DECEMBER,
                                        "Org-" + num + "-2" + "-Employment-Activity-2" + "-Title",
                                        "Org-" + num + "-2" + "-Employment-Activity-2" + "-Description"
                                )
                        }
                ),
                new Organization("Employer-" + num + "-" + 3, "https://employmer" + "-" + num + "-" + 3 + ".com/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        2018, Month.JANUARY, 2018, Month.DECEMBER,
                                        "Org-" + num + "-3" + "-Employment-Activity-1" + "-Title",
                                        "Org-" + num + "-3" + "-Employment-Activity-1" + "-Description"
                                ),
                                new Organization.Activity(
                                        2017, Month.JANUARY, 2017, Month.DECEMBER,
                                        "Org-" + num + "-3" + "-Employment-Activity-2" + "-Title",
                                        "Org-" + num + "-3" + "-Employment-Activity-2" + "-Description"
                                )
                        }
                )
        };
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(experienceOrganizations));

        // Fill Education
        Organization[] educationOrganizations = new Organization[]{
                new Organization("Educational-Institution-" + num + "-" + 1, "https://educational-institution" + "-" + num + "-" + 1 + ".edu/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        2020, Month.SEPTEMBER, 2020, Month.OCTOBER,
                                        "Org-" + num + "-1" + "-Education-Activity-1" + "-Title",
                                        null
                                ),
                                new Organization.Activity(
                                        2020, Month.MARCH, 2020, Month.APRIL,
                                        "Org-" + num + "-1" + "-Education-Activity-2" + "-Title",
                                        null
                                )
                        }
                ),
                new Organization("Educational-Institution-" + num + "-" + 2, null,
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        2018, Month.SEPTEMBER, 2018, Month.OCTOBER,
                                        "Org-" + num + "-2" + "-Education-Activity-1" + "-Title",
                                        null
                                ),
                                new Organization.Activity(
                                        2018, Month.MARCH, 2018, Month.APRIL,
                                        "Org-" + num + "-2" + "-Education-Activity-2" + "-Title",
                                        null
                                )
                        }
                ),
                new Organization("Educational-Institution-" + num + "-" + 3, "https://educational-institution" + "-" + num + "-" + 3 + ".edu/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        2016, Month.SEPTEMBER, 2016, Month.OCTOBER,
                                        "Org-" + num + "-3" + "-Education-Activity-1" + "-Title",
                                        null
                                ),
                                new Organization.Activity(
                                        2016, Month.MARCH, 2016, Month.APRIL,
                                        "Org-" + num + "-3" + "-Education-Activity-2" + "-Title",
                                        null
                                )
                        }
                )
        };
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(educationOrganizations));

        return resume;
    }

    public static Resume fillOut(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        // Fill Contacts
        resume.addContact(ContactType.PHONE, "+7 (921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");

        // Fill Objective
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        // Fill Personal
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        // Fill Achievements
        ListSection achievementsSection = new ListSection(new String[]{
                "Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        });
        resume.addSection(SectionType.ACHIEVEMENTS, achievementsSection);

        // Fill QUALIFICATIONS
        ListSection qualificationsSection = new ListSection(new String[]{
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""
        });
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);

        // Fill EXPERIENCE
        Organization[] experienceOrganizations = {
                new Organization("Java Online Projects", "http://javaops.ru/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/10/2013", ""),
                                        2013, Month.OCTOBER,
                                        "Автор проекта",
                                        "Создание, организация и проведение Java онлайн проектов и стажировок"

                                )
                        }
                ),
                new Organization("Wrike", "https://www.wrike.com",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/10/2014", "01/01/2016"),
                                        2014, Month.OCTOBER, 2016, Month.JANUARY,
                                        "Старший разработчик (backend)",

                                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."

                                )
                        }
                ),
                new Organization("RIT Center", null,
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/04/2012", "01/10/2014"),
                                        2012, Month.APRIL, 2014, Month.OCTOBER,
                                        "Java архитектор",
                                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"
                                )
                        }
                ),
                new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/12/2010", "01/04/2012"),
                                        2010, Month.DECEMBER, 2012, Month.APRIL,
                                        "Ведущий программист",
                                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."
                                )
                        }
                ),
                new Organization("Yota", "https://www.yota.ru/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/06/2008", "01/04/2010"),
                                        2008, Month.JUNE, 2010, Month.APRIL,
                                        "Ведущий специалист",
                                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS"
                                )
                        }
                ),
                new Organization("Enkata", "http://enkata.com/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/03/2007", "01/06/2008"),
                                        2007, Month.MARCH, 2008, Month.JUNE,
                                        "Разработчик ПО",
                                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)"
                                )
                        }
                ),
                new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/01/2005", "01/02/2007"),
                                        2005, Month.JANUARY, 2007, Month.FEBRUARY,
                                        "Разработчик ПО",
                                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)"

                                )
                        }
                ),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/09/1997", "01/01/2005"),
                                        1997, Month.SEPTEMBER, 2005, Month.JANUARY,
                                        "Инженер по аппаратному и программному тестированию",
                                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)"

                                )
                        }
                )
        };
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(experienceOrganizations));

        // Fill Education
        Organization[] educationOrganizations = new Organization[]{
                new Organization("Coursera", "https://www.coursera.org/course/progfun",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/03/2013", "01/05/2013"),
                                        2013, Month.MARCH, 2013, Month.MAY,
                                        "'Functional Programming Principles in Scala' by Martin Odersky",
                                        null
                                )
                        }
                ),
                new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/03/2011", "01/04/2011"),
                                        2011, Month.MARCH, 2011, Month.APRIL,
                                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML\"",
                                        null
                                )
                        }
                ),
                new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/01/2005", "01/04/2005"),
                                        2005, Month.JANUARY, 2005, Month.APRIL,
                                        "3 месяца обучения мобильным IN сетям (Берлин)",
                                        null
                                )
                        }
                ),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/09/1997", "01/03/1998"),
                                        1997, Month.SEPTEMBER, 1998, Month.MARCH,
                                        "6 месяцев обучения цифровым телефонным сетям (Москва)",
                                        null
                                )
                        }
                ),
                new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/09/1993", "01/07/1996"),
                                        1993, Month.SEPTEMBER, 1996, Month.JULY,
                                        "Ведущий специалист",
                                        null
                                ),
                                new Organization.Activity(
                                        // "01/09/1987", "01/07/1993"),
                                        1987, Month.SEPTEMBER, 1993, Month.JULY,
                                        "Ведущий специалист",
                                        null
                                )
                        }

                ),
                new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                        new Organization.Activity[]{
                                new Organization.Activity(
                                        // "01/09/1984", "01/06/1987"),
                                        1984, Month.SEPTEMBER, 1987, Month.JUNE,
                                        "Закончил с отличием",
                                        null
                                )
                        }
                ),
        };
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(educationOrganizations));

        return resume;
    }

    public static Resume fillOut(String uuid) {
        return fillOut(uuid, "FooName FooSurname");
    }
}
