package com.company.university.service.validator;

import org.springframework.stereotype.Component;

import com.company.university.domain.Teacher;

@Component
public class TeacherValidator extends AbstractValidator<Teacher> {

    @Override
    public void validate(Teacher entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Teacher is null");
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new IllegalArgumentException("Teacher name is null or empty");
        }
        if (entity.getEmail() == null || entity.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Teacher email is null or empty");
        }
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Teacher password is null or empty");
        }
    }

}
