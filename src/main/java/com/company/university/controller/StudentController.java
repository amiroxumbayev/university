package com.company.university.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.university.dao.GroupDao;
import com.company.university.domain.Student;
import com.company.university.service.StudentService;
import com.company.university.view.StudentView;

@Component
public class StudentController {
    
    private final StudentView studentView;
    private final StudentService studentService;
    
    @Autowired
    public StudentController(StudentView studentView, StudentService studentService) {
        this.studentView = studentView;
        this.studentService = studentService;
    }
    
    public void provideView() {
        boolean isFinish = false;
        while (!isFinish) {
            int choice = studentView.provideView();
            if (choice == 1) {
                List<Student> students = studentService.findAll();
                studentView.showAllStudents(students);
            } else if (choice == 2) {
                Student student = studentView.createEntity();
                studentService.save(student);
            } else if (choice == 3) {
                Student student = studentView.updateEntity();
                studentService.update(student);
            } else if (choice == 4) {
                Long id = studentView.deleteEntity();
                studentService.deleteById(id);
            } else if (choice == 5) {
                isFinish = true;
            }
        }
    }
}
