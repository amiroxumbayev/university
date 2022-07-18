package com.company.university.domain;

import java.util.Objects;

public class Subject {
    private final Long id;
    private final String name;
    private final int credit;
    
    public Subject(Long id, String name, int credit) {
        this.id = id;
        this.name = name;
        this.credit = credit;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(credit, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Subject other = (Subject) obj;
        return credit == other.credit &&
                Objects.equals(id, other.id) &&
                Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Subject [id=" + id + ", name=" + name + ", credit=" + credit + "]";
    }
}
