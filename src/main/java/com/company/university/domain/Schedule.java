package com.company.university.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Schedule {
    
    private final Long id;
    private final Subject subject;
    private final Week week;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private Schedule(Builder builder) {
        this.id = builder.id;
        this.subject = builder.subject;
        this.week = builder.week;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    public static Builder builder() {
        return new Builder();
    }
    
    public Long getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public Week getWeek() {
        return week;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(endTime, id, startTime, subject, week);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Schedule other = (Schedule) obj;
        return Objects.equals(endTime, other.endTime) &&
                Objects.equals(id, other.id) &&
                Objects.equals(startTime, other.startTime) &&
                Objects.equals(subject, other.subject) &&
                week == other.week;
    }

    @Override
    public String toString() {
        return "Schedule [id=" + id + ", subject=" + subject + ", week=" + week + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }

    public static class Builder {
        
        private Long id;
        private Subject subject;
        private Week week;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        private Builder() {

        }
        
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withSubject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public Builder withWeek(Week week) {
            this.week = week;
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Schedule build() {
            return new Schedule(this);
        }
    }

    public enum Week {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

}
