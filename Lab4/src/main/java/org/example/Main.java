package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Главные класс, считывающий данные о людях из csv файла,
 * формирующий список обьектов Person & Division
 */
public class Main {

    /**
     * Класс подразделение
     */
    static class Division {
        private static int idCounter = 1;
        private int id;
        private String name;

        /**
         * Конструктор поразделения. Генерирует уникальный ид.
         * @param name название подразделения
         */
        public Division(String name) {
            this.id = idCounter++;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Division{id=" + id + ", name='" + name + "'}";
        }
    }

    /**
     * Класс человек
     */
    static class Person {
        private int id;
        private String name;
        private String gender;
        private LocalDate birthDate;
        private Division division;
        private double salary;

        /**
         * Конструктор создания человека
         * @param id - ид человека
         * @param name - имя
         * @param gender - пол
         * @param birthDate - дата рождения
         * @param division - подразделение
         * @param salary - зарплата
         */
        public Person(int id, String name, String gender, LocalDate birthDate, Division division, double salary) {
            this.id = id;
            this.name = name;
            this.gender = gender;
            this.birthDate = birthDate;
            this.division = division;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", birthDate=" + birthDate +
                    ", division=" + division +
                    ", salary=" + salary +
                    '}';
        }
    }

    public static void main(String[] args) {
        String filePath = "foreign_names.csv"; // Файл должен быть в resources или укажи абсолютный путь
        List<Person> people = readPeopleFromCsv(filePath);
        people.forEach(System.out::println);
    }

    /**
     * Считывает csv файл и формирует список людей
     * @param filePath - путь к файлу
     * @return - список обьектов Person
     */
    public static List<Person> readPeopleFromCsv(String filePath) {
        List<Person> people = new ArrayList<>();
        Map<String, Division> divisionMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Main.class.getClassLoader().getResourceAsStream(filePath)))) {

            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] fields = line.split(";");
                if (fields.length < 6) continue;

                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String gender = fields[2];
                LocalDate birthDate = LocalDate.parse(fields[3], formatter);
                String divisionName = fields[4];
                double salary = Double.parseDouble(fields[5]);

                Division division = divisionMap.computeIfAbsent(divisionName, Division::new);
                Person person = new Person(id, name, gender, birthDate, division, salary);
                people.add(person);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }
}
