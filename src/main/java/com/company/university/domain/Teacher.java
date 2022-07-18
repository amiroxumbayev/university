package com.company.university.domain;

import java.util.List;
import java.util.Objects;

public class Teacher extends Person {

    private final List<Group> groups;

    private Teacher(TeacherBuilder builder) {
        super(builder);
        this.groups = builder.groups;
    }
    
    public static TeacherBuilder builder() {
        return new TeacherBuilder();
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(groups);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        Teacher other = (Teacher) obj;
        return Objects.equals(groups, other.groups);
    }

    @Override
    public String toString() {
        return "Teacher [groups=" + groups + "]";
    }
    
    public static class TeacherBuilder extends PersonBuilder<TeacherBuilder>{
        
        private List<Group> groups;

        private TeacherBuilder() {

        }
        
        @Override
        public TeacherBuilder self() {
            return this;
        }
        
        public TeacherBuilder withGroups(List<Group> groups) {
            this.groups = groups;
            return self();
        }

        public Teacher build() {
            return new Teacher(self());
        }
    }
}
