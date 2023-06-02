package se.yrgo.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String subjectName;
    private int numberOfSemesters;

    @ManyToMany
    private Set<Tutor> tutors;

    public Subject() {
    }

    public Subject(String subjecName, int numberOfSemesters) {
        this.id = id;
        this.subjectName = subjecName;
        this.numberOfSemesters = numberOfSemesters;
        this.tutors = new HashSet<Tutor>();

    }

    public void addTutorToSubject(Tutor tutor) {
        this.tutors.add(tutor);
        tutor.getSubjects().add(this);
    }

    public String subjectName() {
        return subjectName;
    }

    public int id() {
        return id;
    }

    public int numberOfSemesters() {
        return numberOfSemesters;
    }

    public Set<Tutor> getTutors() {
        return this.tutors;
    }
}
