package com.company.university.domain;

import java.util.List;
import java.util.Objects;

public class Faculty {
    
    private final Long id;
    private final String title;
    private final List<Subject> subjects;
    
    public Faculty(Long id, String title, List<Subject> subjects) {
        this.id = id;
        this.title = title;
        this.subjects = subjects;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subjects, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Faculty other = (Faculty) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(subjects, other.subjects) &&
                Objects.equals(title, other.title);
    }

    @Override
    public String toString() {
        return "Faculty [id=" + id + ", title=" + title + ", subjects=" + subjects + "]";
    }
}
