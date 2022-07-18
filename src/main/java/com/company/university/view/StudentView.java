package com.company.university.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.company.university.annotation.View;
import com.company.university.domain.Grade;
import com.company.university.domain.Student;

@View
public class StudentView {

    public int provideView() {
        System.out.println("Student:");
        System.out.println("Enter number to select:");
        drawMenu();
        int choice = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice = Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        if (!(choice >= 1 && choice <= 5)) {
            System.out.println("Incorrect input");
        }
        return choice;
    }

    private void drawMenu() {
        System.out.println("1. List of students");
        System.out.println("2. Add student");
        System.out.println("3. Update student information");
        System.out.println("4. Remove student");
        System.out.println("5. Back");
    }
    
    public void showAllStudents(List<Student> students) {
        students.forEach(student -> System.out.println(student.toString()));
    }
    
    public Student createEntity() {
        String name= null;
        String email= null;
        String password= null;
        int year= 1;
        double avgGpa= 4.;
        List<Grade> grades = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Type name:");
            name = bufferedReader.readLine();
            System.out.println("Type email:");
            email = bufferedReader.readLine();
            System.out.println("Type password:");
            password = bufferedReader.readLine();
            System.out.println("Type year:");
            year = Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return Student.builder()
                .withName(name)
                .withEmail(email)
                .withPassword(password)
                .withYear(year)
                .withAvgGpa(avgGpa)
                .withGrades(grades)
                .build();
    }
    
    public Student updateEntity() {
        Long id = null;
        String name= null;
        String email= null;
        String password= null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Id:");
            id = Long.parseLong(bufferedReader.readLine());
            System.out.println("Type name:");
            name = bufferedReader.readLine();
            System.out.println("Type email:");
            email = bufferedReader.readLine();
            System.out.println("Type password:");
            password = bufferedReader.readLine();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return Student.builder()
                .withId(id)
                .withName(name)
                .withEmail(email)
                .withPassword(password)
                .build();
    }
    
    public Long deleteEntity() {
        Long id = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Id:");
            id = Long.parseLong(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return id;
    }
}
