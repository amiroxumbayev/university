package com.company.university.service.validator;

import org.springframework.stereotype.Component;

import com.company.university.domain.Faculty;

@Component
public class FacultyValidator extends AbstractValidator<Faculty> {

    @Override
    public void validate(Faculty entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Faculty is null");
        }
        if (entity.getTitle() == null || entity.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Faculty title is null or empty");
        }
        if (entity.getSubjects() == null || entity.getSubjects().isEmpty()) {
            throw new IllegalArgumentException("Faculty subjects is null or empty");
        }
    }
}
