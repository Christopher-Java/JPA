package se.yrgo.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String tutorId;
    private String name;
    private int salary;

    @ManyToMany(mappedBy = "tutors")
    private Set<Subject> subjectsToTeach;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TUTOR_FK")
    private Set<Student> teachingGroup;

    public Tutor(String tutorId, String name, int salary) {
        this.name = name;
        this.tutorId = tutorId;
        this.salary = salary;
        this.teachingGroup = new HashSet<Student>();
        this.subjectsToTeach = new HashSet<Subject>();
    }

    public Tutor() {

    }

     public void createStudentAndAddtoTeachingGroup(String studentName, String
     enrollmentID, String street, String city,
     String zipcode) {
     Student student = new Student(studentName, enrollmentID, street, city,
     zipcode);
     this.addStudentToTeachingGroup(student);
     }

    public void addSubjectsToTeach(Subject subject) {
        this.subjectsToTeach.add(subject);
        subject.getTutors().add(this);
    }

    public void addStudentToTeachingGroup(Student newStudent) {
        this.teachingGroup.add(newStudent);
    }

    public Set<Student> getTeachingGroup() {
        Set<Student> unmodifiable = Collections.unmodifiableSet(this.teachingGroup);
        return unmodifiable;
    }

    public Set<Subject> getSubjects() {
        return this.subjectsToTeach;
    }

    public String getTutorId() {
        return tutorId;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Tutor [tutorId=" + tutorId + ", name=" + name + ", salary=" + salary + "]";
    }

}
