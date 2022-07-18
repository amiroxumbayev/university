package com.company.university.service.validator;

import org.springframework.stereotype.Component;

import com.company.university.domain.Group;

@Component
public class GroupValidator extends AbstractValidator<Group> {

    @Override
    public void validate(Group entity) {

        if (entity == null) {
            throw new IllegalArgumentException("Group is null");
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new IllegalArgumentException("Group name is null or empty");
        }
        if (entity.getFaculty() == null || entity.getFaculty().getId() != null) {
            throw new IllegalArgumentException("Group faculty is null or empty");
        }
    }
}
