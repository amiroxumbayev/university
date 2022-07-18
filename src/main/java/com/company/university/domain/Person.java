package com.company.university.domain;

import java.util.Objects;

public abstract class Person {
    
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    
    protected Person(PersonBuilder<? extends PersonBuilder<?>> personBuilder) {
        this.id = personBuilder.id;
        this.name = personBuilder.name;
        this.email = personBuilder.email;
        this.password = personBuilder.password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id, name, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        return Objects.equals(email, other.email) &&
                Objects.equals(id, other.id) &&
                Objects.equals(name, other.name)
                && Objects.equals(password, other.password);
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
    }
    
    protected static class PersonBuilder<T extends PersonBuilder<T>> {
        
        private Long id;
        private String name;
        private String email;
        private String password;

        protected PersonBuilder() {

        }
        
        @SuppressWarnings("unchecked")
        public T self() {
            return (T) this;
        }

        public T withId(Long id) {
            this.id = id;
            return self();
        }

        public T withName(String name) {
            this.name = name;
            return self();
        }

        public T withEmail(String email) {
            this.email = email;
            return self();
        }

        public T withPassword(String password) {
            this.password = password;
            return self();
        }
    }
    
}
