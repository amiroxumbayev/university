package com.company.university.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.university.domain.Faculty;
import com.company.university.domain.Group;
import com.company.university.domain.Schedule;
import com.company.university.domain.Subject;
import com.company.university.domain.Task;
import com.company.university.service.FacultyService;
import com.company.university.service.GroupService;
import com.company.university.service.SubjectService;
import com.company.university.view.GroupView;

@Component
public class GroupController {

    private final GroupView groupView;
    private final GroupService groupService;
    private final FacultyService facultyService;
    private final SubjectService subjectService;
    
    @Autowired
    public GroupController(GroupView groupView, GroupService groupService, FacultyService facultyService, SubjectService subjectService) {
        this.groupView = groupView;
        this.groupService = groupService;
        this.facultyService = facultyService;
        this.subjectService = subjectService;
    }

    public void provideView() {
        boolean isFinish = false;
        while (!isFinish) {
            int choice = groupView.provideView();
            if (choice == 1) {
                List<Group> groups = groupService.findAll();
                groupView.showAllGroups(groups);
            } else if (choice == 2) {
                Group group = groupView.createEntity();
                Faculty faculty = provideChooseFaculty();
                Map<Long, List<Schedule>> schedule = provideMakeSchedule();
                Map<Long, List<Task>> workPrograms = provideMakeWorkPrograms();
                groupService.save(Group.builder()
                        .withName(group.getName())
                        .withFaculty(faculty)
                        .withStudents(new ArrayList<>())
                        .withSchedule(schedule)
                        .withWorkPrograms(workPrograms)
                        .build());
            } else if (choice == 3) {
                Group group = groupView.updateEntity();
                Faculty faculty = provideChooseFaculty();
                groupService.update(Group.builder()
                                    .withId(group.getId())
                                    .withName(group.getName())
                                    .withFaculty(faculty)
                                    .build());
            } else if (choice == 4) {
                Long id = groupView.deleteEntity();
                groupService.deleteById(id);
            } else if (choice == 5) {
                isFinish = true;
            }
        }
    }
    
    private Faculty provideChooseFaculty() {
        List<Faculty> faculties = facultyService.findAll();
        Faculty faculty = new Faculty(null, null, null);
        boolean isFinish = false;
        while (!isFinish) {
            Long choice = groupView.chooseFaculty(faculties);
            if (choice > 0 ) {
                faculty = getFaculty(choice);
                isFinish = true;
            }
        }
        return faculty;
    }
    
    private Map<Long, List<Schedule>> provideMakeSchedule() {
        List<Subject> subjects = subjectService.findAll();
        Map<Long, List<Schedule>> schedule = new HashMap<>();
        boolean isFinish = false;
        while (!isFinish) {
            Long id = groupView.makeSchedule(subjects);
            Subject subject = getSubject(id);
            schedule.put(subject.getId(), groupView.fillSubjectSchedule(subject));
            String answer = groupView.makeSubjectSchedule();
            if ("no".equals(answer)) {
                isFinish = true;
            }
        }
        return schedule;
    }
    
    private Map<Long, List<Task>> provideMakeWorkPrograms() {
        List<Subject> subjects = subjectService.findAll();
        Map<Long, List<Task>> workPrograms = new HashMap<>();
        boolean isFinish = false;
        while (!isFinish) {
            Long id = groupView.makeWorkPrograms(subjects);
            Subject subject = getSubject(id);
            workPrograms.put(subject.getId(), groupView.fillSubjectWorkProgram(subject));
            String answer = groupView.makeSubjectWorkProgram();
            if ("no".equals(answer)) {
                isFinish = true;
            }
        }
        return workPrograms;
    }

    public Faculty getFaculty(Long id) {
        return facultyService.findById(id);
    }

    public Subject getSubject(Long id) {
        return subjectService.findById(id);
    }
}
