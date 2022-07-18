package com.company.university.service.validator;

import com.company.university.domain.Page;

public abstract class AbstractValidator<E> implements Validator<E> {
    
    @Override
    public void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        if (id < 1L) {
            throw new IllegalArgumentException("Id less than 1");
        }
    }
    
    @Override
    public void validatePage(Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Page is null");
        }
        if (page.getPageNumber() == null) {
            throw new IllegalArgumentException("Page number is null");
        }
        if (page.getPageNumber() < 1) {
            throw new IllegalArgumentException("Page number less than 1");
        }
        if (page.getRows() == null) {
            throw new IllegalArgumentException("Page rows number is null");
        }
        if (page.getRows() < 1) {
            throw new IllegalArgumentException("Page rows number less than 1");
        }
    }
}
