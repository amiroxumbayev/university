package com.company.university.domain;

import java.util.Objects;

public class Grade {
    private final Long id;
    private final Task task;
    private final double score;
    
    public Grade(Long id, Task task, double score) {
        this.id = id;
        this.task = task;
        this.score = score;
    }
    
    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, task);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Grade other = (Grade) obj;
        return Objects.equals(id, other.id) &&
                Double.doubleToLongBits(score) == Double.doubleToLongBits(other.score) &&
                Objects.equals(task, other.task);
    }

    @Override
    public String toString() {
        return "Grade [id=" + id + ", task=" + task + ", score=" + score + "]";
    }
    
}
