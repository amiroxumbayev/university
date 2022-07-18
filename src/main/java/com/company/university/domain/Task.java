package com.company.university.domain;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Task {
    private final Long id;
    private final String title;
    private final Subject subject;
    private final Type type;
    private final String description;
    private final List<File> files;

    private Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.subject = builder.subject;
        this.type = builder.type;
        this.description = builder.description;
        this.files = builder.files;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Subject getSubject() {
        return subject;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public List<File> getFiles() {
        return files;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, files, id, subject, title, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        return Objects.equals(description, other.description) &&
                Objects.equals(files, other.files) &&
                Objects.equals(id, other.id) &&
                Objects.equals(subject, other.subject) &&
                Objects.equals(title, other.title) &&
                type == other.type;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", subject=" + subject + ", type=" + type + ", description="
                + description + ", files=" + files + "]";
    }

    public static class Builder {
        private Long id;
        private String title;
        private Subject subject;
        private Type type;
        private String description;
        private List<File> files;
        
        private Builder() {

        }
        
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder withSubject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withFiles(List<File> files) {
            this.files = files;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    public enum Type {
        ATTENDENCE, TEST, LABORATORY, PRACTICE, LECTURE
    }
}
