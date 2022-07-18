package com.company.university.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.company.university.annotation.View;
import com.company.university.domain.Faculty;
import com.company.university.domain.Group;
import com.company.university.domain.Schedule;
import com.company.university.domain.Schedule.Week;
import com.company.university.domain.Subject;
import com.company.university.domain.Task;
import com.company.university.domain.Task.Type;

@View
public class GroupView {
    
    public int provideView() {
        System.out.println("Group:");
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
        System.out.println("1. List of group");
        System.out.println("2. Create group");
        System.out.println("3. Update group");
        System.out.println("4. Delete group");
        System.out.println("5. Back");
    }
    
    public void showAllGroups(List<Group> groups) {
        groups.forEach(group -> System.out.println(group.toString()));
    }
    
    public Group createEntity() {
        String name= null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Type name:");
            name = bufferedReader.readLine();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return Group.builder()
                .withName(name)
                .build();
    }
    
    public Long chooseFaculty(List<Faculty> faculties) {
        System.out.println("Choose faculty by id:");
        faculties.forEach(e -> System.out.println(e.toString()));
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
    
    public Long makeSchedule(List<Subject> subjects) {
        System.out.println("Creating shedule:");
        System.out.println("Choose subject by id:");
        subjects.forEach(subject -> System.out.println(subject.toString()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            id = Long.parseLong(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return id;
    }
    
    public String makeSubjectSchedule() {
        System.out.println("Make new subject schedule?(yes, no)");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        try {
            answer = bufferedReader.readLine().toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
    
    public List<Schedule> fillSubjectSchedule(Subject subject) {
        List<Schedule> schedule = new ArrayList<>();
        boolean isFinish = false;
        try {
            while (!isFinish) {
                System.out.println("Make schedule of subject:");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                Week week;
                week = chooseDayOfWeek();
                System.out.println("Set start time:");
                LocalDateTime startTime = setTime();
                System.out.println("Set end time:");
                LocalDateTime endTime = setTime();
                schedule.add(Schedule.builder()
                                .withSubject(subject)
                                .withWeek(week)
                                .withStartTime(startTime)
                                .withEndTime(endTime)
                                .build());
                System.out.println("Continue filling?(yes, no)");
                String answer = bufferedReader.readLine().toLowerCase();
                if ("no".equals(answer)) {
                    isFinish = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }
    
    private Week chooseDayOfWeek() throws IOException {
        List<Week> dayWeek = Arrays.asList(Week.values());
        System.out.println("Choose day of week(word):");
        dayWeek.forEach(day -> System.out.println(day.toString()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return Week.valueOf(bufferedReader.readLine().toUpperCase());
    }
    
    private LocalDateTime setTime() throws NumberFormatException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hour:");
        Integer hour = Integer.parseInt(bufferedReader.readLine());
        System.out.println("Minute:");
        Integer minute = Integer.parseInt(bufferedReader.readLine());
        LocalTime localTime = LocalTime.of(hour, minute);
        return LocalDateTime.of(LocalDate.now(), localTime);
    }
    
    public Long makeWorkPrograms(List<Subject> subjects) {
        System.out.println("Creating work programs:");
        System.out.println("Choose subject by id:");
        subjects.forEach(subject -> System.out.println(subject.toString()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            id = Long.parseLong(bufferedReader.readLine());
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return id;
    }
    
    public String makeSubjectWorkProgram() {
        System.out.println("Make new subject work program?(yes, no)");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        try {
            answer = bufferedReader.readLine().toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
    
    public List<Task> fillSubjectWorkProgram(Subject subject) {
        List<Task> workProgram = new ArrayList<>();
        boolean isFinish = false;
        try {
            while (!isFinish) {
                System.out.println("Make work program of subject:");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Type title:");
                String title;
                title = bufferedReader.readLine();
                Type type = chooseTaskType();
                System.out.println("Type description:");
                String description= bufferedReader.readLine();
                workProgram.add(Task.builder()
                        .withTitle(title)
                        .withSubject(subject)
                        .withType(type)
                        .withDescription(description)
                        .build());
                System.out.println("Continue filling?(yes, no)");
                String answer = bufferedReader.readLine().toLowerCase();
                if ("no".equals(answer)) {
                    isFinish = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workProgram;
    }
    
    private Type chooseTaskType() throws IOException {
        List<Type> types = Arrays.asList(Type.values());
        System.out.println("Choose type of task(word):");
        types.forEach(type -> System.out.println(type.toString()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return Type.valueOf(bufferedReader.readLine().toUpperCase());
    }
    
    public Group updateEntity() {
        Long id = null;
        String name = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Id:");
            id = Long.parseLong(bufferedReader.readLine());
            System.out.println("Type name:");
            name = bufferedReader.readLine();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return Group.builder()
                .withId(id)
                .withName(name)
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
