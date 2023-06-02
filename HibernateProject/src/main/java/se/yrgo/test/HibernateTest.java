package se.yrgo.test;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

public class HibernateTest {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        setUpData();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Task-1- Navigating across relationships (Using member of)
        Subject science = em.find(Subject.class, 2);
        TypedQuery<Student> query = em.createQuery(
                "select student from Tutor as tutor join tutor.teachingGroup as student where :subject member of tutor.subjectsToTeach",
                Student.class);
        query.setParameter("subject", science);
        List<Student> studentGroup = query.getResultList();
        for (Student student : studentGroup) {
            System.out.println(student + "\nUppgift 1");
        }
        // Task-2- Report Query- Multiple fields (Use join)
        List<Object[]> results = em
                .createQuery("select student.name, tutor.name from Tutor tutor join tutor.teachingGroup student")
                .getResultList();
        for (Object[] obj : results) {
            String studentName = (String) obj[0];
            String tutorName = (String) obj[1];
            System.out.println("Students Name: " + studentName);
            System.out.println("Tutors Name: " + tutorName  + "\nUppgift 2");
        }

        // Task-3-Report Query- Aggregation
        double averageSemester = (Double) em.createQuery("select avg(numberOfSemesters)from Subject subject")
                .getSingleResult();
        System.out.println( averageSemester  + "\nUppgift 3");

        // Task-4-Query With Aggregation
        int maxSalary = (int) em.createQuery("select max(salary)from Tutor tutor")
                .getSingleResult();
        System.out.println( maxSalary  + "\nUppgift 4");

        //Task-5- NamedQuery 
        List<Tutor> salary = em.createNamedQuery("highSalary", Tutor.class).setParameter("salary", 10000)
                .getResultList();
        for (Tutor tutor : salary) {
            System.out.println(tutor  + "\nUppgift 5");
        }

        tx.commit();
        em.close();
    }

    public static void setUpData() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Subject mathematics = new Subject("Mathematics", 2);
        Subject science = new Subject("Science", 2);
        Subject programming = new Subject("Programming", 3);
        em.persist(mathematics);
        em.persist(science);
        em.persist(programming);

        Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
        t1.addSubjectsToTeach(mathematics);
        t1.addSubjectsToTeach(science);

        Tutor t2 = new Tutor("DEF456", "Sara Svensson", 20000);
        t2.addSubjectsToTeach(mathematics);
        t2.addSubjectsToTeach(science);

        // This tutor is the only tutor who can teach History
        Tutor t3 = new Tutor("GHI678", "Karin Lindberg", 0);
        t3.addSubjectsToTeach(programming);

        em.persist(t1);
        em.persist(t2);
        em.persist(t3);

        t1.createStudentAndAddtoTeachingGroup("Jimi Hendriks", "1-HEN-2019", "Street 1", "city 2", "1212");
        t1.createStudentAndAddtoTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
        t3.createStudentAndAddtoTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");

        tx.commit();
        em.close();
    }

}