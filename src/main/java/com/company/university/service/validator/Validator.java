package com.company.university.service.validator;

import com.company.university.domain.Page;

public interface Validator<E> {
    void validate(E entity);
    void validateId(Long id);
    void validatePage(Page page);
    
}
