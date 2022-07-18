package com.company.university.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.university.domain.Subject;
import com.company.university.service.SubjectService;
import com.company.university.view.SubjectView;

@Component
public class SubjectController {

    private final SubjectView subjectView;
    private final SubjectService subjectService;
    
    @Autowired
    public SubjectController(SubjectView subjectView, SubjectService subjectService) {
        this.subjectView = subjectView;
        this.subjectService = subjectService;
    }

    public void provideView() {
        boolean isFinish = false;
        while (!isFinish) {
            int choice = subjectView.provideView();
            if (choice == 1) {
                List<Subject> subjects = subjectService.findAll();
                subjectView.showAllSubjects(subjects);
            } else if (choice == 2) {
                Subject subject = subjectView.createEntity();
                subjectService.save(subject);
            } else if (choice == 3) {
                Subject subject = subjectView.updateEntity();
                subjectService.update(subject);
            } else if (choice == 4) {
                Long id = subjectView.deleteEntity();
                subjectService.deleteById(id);
            } else if (choice == 5) {
                isFinish = true;
            }
        }
    }
}
