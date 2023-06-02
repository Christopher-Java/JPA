package se.yrgo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private int id;


    private String enrollmentID;
     private String name;

    @Embedded
    private Address address;

    public Student(String name, String enrollmentID, String street, String city,
            String zipcode) {
        this.name=name;
        this.enrollmentID = enrollmentID;
        this.address = new Address(street, city, zipcode);
    }

    public Student() {
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress() {
        this.address = address;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enrollmentID == null) ? 0 : enrollmentID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (enrollmentID == null) {
            if (other.enrollmentID != null)
                return false;
        } else if (!enrollmentID.equals(other.enrollmentID))
            return false;
        return true;
    }

     public int getId() {
     return id;
     }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public String toString() {
        return name + " lives at " + address;
    }

}
