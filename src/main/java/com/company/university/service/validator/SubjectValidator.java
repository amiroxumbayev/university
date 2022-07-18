package com.company.university.service.validator;

import org.springframework.stereotype.Component;

import com.company.university.domain.Subject;

@Component
public class SubjectValidator extends AbstractValidator<Subject> {

    @Override
    public void validate(Subject entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Subject is null");
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new IllegalArgumentException("Subject name is null or empty");
        }
        if (entity.getCredit() < 1) {
            throw new IllegalArgumentException("Credit number less than 1");
        }
    }
}
