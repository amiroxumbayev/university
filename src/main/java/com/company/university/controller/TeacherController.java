package com.company.university.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.university.domain.Teacher;
import com.company.university.service.GroupService;
import com.company.university.service.TeacherService;
import com.company.university.domain.Group;
import com.company.university.view.TeacherView;

@Component
public class TeacherController {
    
    private final TeacherView teacherView;
    private final TeacherService teacherService;
    private final GroupService groupService;
    
    @Autowired
    public TeacherController(TeacherView teacherView, TeacherService teacherService, GroupService groupService) {
        this.teacherView = teacherView;
        this.teacherService = teacherService;
        this.groupService = groupService;
    }
    
    public void provideView() {
        boolean isFinish = false;
        while (!isFinish) {
            int choice = teacherView.provideView();
            if (choice == 1) {
                List<Teacher> faculties = teacherService.findAll();
                teacherView.showAllTeachers(faculties);
            } else if (choice == 2) {
                Teacher teacher = teacherView.createEntity();
                List<Group> groups = provideAttachGroups();
                teacherService.save(Teacher.builder()
                                    .withName(teacher.getName())
                                    .withEmail(teacher.getEmail())
                                    .withPassword(teacher.getPassword())
                                    .withGroups(groups)
                                    .build());
            } else if (choice == 3) {
                Teacher teacher = teacherView.updateEntity();
                teacherService.update(teacher);
            } else if (choice == 4) {
                Long id = teacherView.deleteEntity();
                teacherService.deleteById(id);
            } else if (choice == 5) {
                isFinish = true;
            }
        }
    }
    
    private List<Group> provideAttachGroups() {
        List<Group> groups = groupService.findAll();
        List<Group> chosenGroups = new ArrayList<>();
        boolean isFinish = false;
        while (!isFinish) {
            Long choice = teacherView.attachGroups(groups);
            if (choice > 0 ) {
                chosenGroups.add(getGroup(choice));
            } else if (choice == 0) {
                isFinish = true;
            } 
        }
        return chosenGroups;
    }
    
    public Group getGroup(Long id) {
        return groupService.findById(id);
    }

}
