package com.company.university.domain;

import java.util.Objects;

public class Page {

    private final Integer pageNumber;
    private final Integer rows;
    
    public Page(Integer pageNumber, Integer rows) {
        this.pageNumber = pageNumber;
        this.rows = rows;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getRows() {
        return rows;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, rows);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Page other = (Page) obj;
        return Objects.equals(pageNumber, other.pageNumber) &&
                Objects.equals(rows, other.rows);
    }

    @Override
    public String toString() {
        return "Page [pageNumber=" + pageNumber + ", rows=" + rows + "]";
    }
    
}
