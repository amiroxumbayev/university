package com.company.university.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.company.university.annotation.View;
import com.company.university.domain.Faculty;
import com.company.university.domain.Subject;

@View
public class FacultyView {

    public int provideView() {
        System.out.println("Faculty:");
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
        System.out.println("1. List of faculty");
        System.out.println("2. Create faculty");
        System.out.println("3. Update faculty");
        System.out.println("4. Delete faculty");
        System.out.println("5. Back");
    }
    
    public void showAllFaculties(List<Faculty> faculties) {
        faculties.forEach(faculty -> System.out.println(faculty.toString()));
    }
    
    public Faculty createEntity() {
        String title = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Type title:");
            title = bufferedReader.readLine();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return new Faculty(null, title, null);
    }
    
    public Long chooseSubjects(List<Subject> subjects) {
        System.out.println("Choose subjects by id or type 0 to finish:");
        subjects.forEach(subject -> System.out.println(subject.toString()));
        Long choice = 0L;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice = Long.parseLong(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return choice;
    }
    
    public Faculty updateEntity() {
        Long id = null;
        String title = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Id:");
            id = Long.parseLong(bufferedReader.readLine());
            System.out.println("Type title:");
            title = bufferedReader.readLine();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return new Faculty(id, title, null);
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
