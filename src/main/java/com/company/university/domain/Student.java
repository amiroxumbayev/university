package com.company.university.domain;

import java.util.List;
import java.util.Objects;

public class Student extends Person {
    
    private final int year;
    private final double avgGpa;
    private final List<Grade> grades;

    private Student(StudentBuilder builder) {
        super(builder);
        this.year = builder.year;
        this.avgGpa = builder.avgGpa;
        this.grades = builder.grades;
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    public int getYear() {
        return year;
    }

    public double getAvgGpa() {
        return avgGpa;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(avgGpa, grades, year);
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
        Student other = (Student) obj;
        return Double.doubleToLongBits(avgGpa) == Double.doubleToLongBits(other.avgGpa) &&
                Objects.equals(grades, other.grades) &&
                year == other.year;
    }

    @Override
    public String toString() {
        return "Student [year=" + year + ", avgGpa=" + avgGpa + ", grades=" + grades + "]";
    }
    
    public static class StudentBuilder extends PersonBuilder<StudentBuilder>{
        
        private int year;
        private double avgGpa;
        private List<Grade> grades;

        private StudentBuilder() {

        }
        
        @Override
        public StudentBuilder self() {
            return this;
        }

        public StudentBuilder withYear(int year) {
            this.year = year;
            return self();
        }

        public StudentBuilder withAvgGpa(double avgGpa) {
            this.avgGpa = avgGpa;
            return self();
        }

        public StudentBuilder withGrades(List<Grade> grades) {
            this.grades = grades;
            return self();
        }

        public Student build() {
            return new Student(self());
        }
    }

}
