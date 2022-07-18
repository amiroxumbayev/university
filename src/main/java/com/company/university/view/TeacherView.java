package com.company.university.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.company.university.annotation.View;
import com.company.university.domain.Group;
import com.company.university.domain.Teacher;

@View
public class TeacherView {

      public int provideView() {
        System.out.println("Teacher:");
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
        System.out.println("1. List of teachers");
        System.out.println("2. Add teacher");
        System.out.println("3. Update teacher information");
        System.out.println("4. Remove teacher");
        System.out.println("5. Back");
    }
    
    public void showAllTeachers(List<Teacher> teachers) {
        teachers.forEach(teacher -> System.out.println(teacher.toString()));
    }
    
    public Teacher createEntity() {
        String name= null;
        String email= null;
        String password= null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Type name:");
            name = bufferedReader.readLine();
            System.out.println("Type email:");
            email = bufferedReader.readLine();
            System.out.println("Type password:");
            password = bufferedReader.readLine();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return Teacher.builder()
                .withName(name)
                .withEmail(email)
                .withPassword(password)
                .build();
    }
    
    public Long attachGroups(List<Group> groups) {
        System.out.println("Choose groups by id or type 0 to finish:");
        groups.forEach(subject -> System.out.println(subject.toString()));
        Long choice = 0L;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice = Long.parseLong(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        if(choice <= 0) {
            System.out.println("Incorrect input");
        }
        return choice;
    }
    
    public Teacher updateEntity() {
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
        return Teacher.builder()
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
