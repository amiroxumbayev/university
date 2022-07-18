package com.company.university.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Group {
    private final Long id;
    private final String name;
    private final Faculty faculty;
    private final List<Student> students;
    private final Map<Long, List<Schedule>> schedule;
    private final Map<Long, List<Task>> workPrograms;
    
    private Group(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.faculty = builder.faculty;
        this.students = builder.students;
        this.schedule = builder.schedule;
        this.workPrograms = builder.workPrograms;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Map<Long, List<Schedule>> getSchedule() {
        return schedule;
    }

    public Map<Long, List<Task>> getWorkPrograms() {
        return workPrograms;
    }

    @Override
    public int hashCode() {
        return Objects.hash(faculty, id, name, schedule, students, workPrograms);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Group other = (Group) obj;
        return Objects.equals(faculty, other.faculty) &&
                Objects.equals(id, other.id) &&
                Objects.equals(name, other.name) &&
                Objects.equals(schedule, other.schedule) &&
                Objects.equals(students, other.students) &&
                Objects.equals(workPrograms, other.workPrograms);
    }
    
    @Override
    public String toString() {
        return "Group [id=" + id + ", name=" + name + ", faculty=" + faculty + ", students=" + students + ", schedule="
                + schedule + ", workPrograms=" + workPrograms + "]";
    }

    public static class Builder {
        
        private Long id;
        private String name;
        private Faculty faculty;
        private List<Student> students;
        private Map<Long, List<Schedule>> schedule;
        private Map<Long, List<Task>> workPrograms;

        private Builder() {

        }
        
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withFaculty(Faculty faculty) {
            this.faculty = faculty;
            return this;
        }

        public Builder withStudents(List<Student> students) {
            this.students = students;
            return this;
        }

        public Builder withSchedule(Map<Long, List<Schedule>> schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder withWorkPrograms(Map<Long, List<Task>> workPrograms) {
            this.workPrograms = workPrograms;
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }
    
}
