package com.company.university.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.company.university.annotation.View;
import com.company.university.domain.Subject;

@View
public class SubjectView {

    public int provideView() {
        System.out.println("Subject:");
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
        System.out.println("1. List of subjects");
        System.out.println("2. Create subject");
        System.out.println("3. Update subject");
        System.out.println("4. Delete subject");
        System.out.println("5. Back");
    }
    
    public void showAllSubjects(List<Subject> subjects) {
        subjects.forEach(subject -> System.out.println(subject.toString()));
    }

    public Subject createEntity() {
        String name = null;
        int credit = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Type name:");
            name = bufferedReader.readLine();
            System.out.println("Credit:");
            credit = Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return new Subject(null, name, credit);
    }

    public Subject updateEntity() {
        Long id = null;
        String name = null;
        int credit = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Id:");
            id = Long.parseLong(bufferedReader.readLine());
            System.out.println("Type name:");
            name = bufferedReader.readLine();
            System.out.println("Credit:");
            credit = Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return new Subject(id, name, credit);
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
