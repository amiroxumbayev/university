package com.company.university.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.university.domain.Subject;
import com.company.university.service.FacultyService;
import com.company.university.service.SubjectService;
import com.company.university.view.FacultyView;
import com.company.university.domain.Faculty;

@Component
public class FacultyController {

    private final FacultyView facultyView;
    private final FacultyService facultyService;
    private final SubjectService subjectDao;
    
    @Autowired
    public FacultyController(FacultyView facultyView, FacultyService facultyService, SubjectService subjectDao) {
        this.facultyView = facultyView;
        this.facultyService = facultyService;
        this.subjectDao = subjectDao;
    }

    public void provideView() {
        boolean isFinish = false;
        while (!isFinish) {
            int choice = facultyView.provideView();
            if (choice == 1) {
                List<Faculty> faculties = facultyService.findAll();
                facultyView.showAllFaculties(faculties);
            } else if (choice == 2) {
                Faculty faculty = facultyView.createEntity();
                List<Subject> subjects = provideChooseSubjects();
                facultyService.save(new Faculty(null, faculty.getTitle(), subjects));
            } else if (choice == 3) {
                Faculty faculty = facultyView.updateEntity();
                facultyService.update(faculty);
            } else if (choice == 4) {
                Long id = facultyView.deleteEntity();
                facultyService.deleteById(id);
            } else if (choice == 5) {
                isFinish = true;
            }
        }
    }

    private List<Subject> provideChooseSubjects() {
        List<Subject> subjects = subjectDao.findAll();
        List<Subject> chosenSubjects = new ArrayList<>();
        boolean isFinish = false;
        while (!isFinish) {
            Long choice = facultyView.chooseSubjects(subjects);
            if (choice > 0) {
                chosenSubjects.add(getSubject(choice));
            } else if (choice == 0) {
                isFinish = true;
            }
        }
        return chosenSubjects;
    }

    private Subject getSubject(Long id) {
        return subjectDao.findById(id);
    }
}
