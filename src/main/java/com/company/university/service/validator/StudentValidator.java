package com.company.university.service.validator;

import org.springframework.stereotype.Component;

import com.company.university.domain.Student;

@Component
public class StudentValidator extends AbstractValidator<Student> {

    @Override
    public void validate(Student entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Student is null");
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new IllegalArgumentException("Student name is null or empty");
        }
        if (entity.getEmail() == null || entity.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Student email is null or empty");
        }
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Student password is null or empty");
        }
        if (entity.getYear() < 1) {
            throw new IllegalArgumentException("Student entered year less than 1");
        }
        if (entity.getAvgGpa() < 0 && entity.getAvgGpa() > 4) {
            throw new IllegalArgumentException("Student gpa incorrect");
        }
    }
}
