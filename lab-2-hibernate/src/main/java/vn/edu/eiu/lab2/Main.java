package vn.edu.eiu.lab2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import vn.edu.eiu.lab2.entity.Student;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu1-mysql-masterapp");

    public static void main(String[] args) {
//        insertStudent();
        getStudentById("1");
        getAllStudents();
        getStudentsByConditions("Student2", 3.7);

        // Update student with ID "2"
        Student updatedStu = new Student("2", "Updated Name", 3.8, 2001);
        updateStudentById("2", updatedStu);
        getStudentById("2");

        // Delete student with ID "3"
        deleteStudentById("3");
        getAllStudents();
    }


    public static void insertStudent() {
        EntityManager em = emf.createEntityManager();

        Student stu = new Student("4", "Student name", 3.7, 2003);
        Student stu4 = new Student("1", "Student name", 3.7, 2003);
        Student stu1 = new Student("2", "Student1 name", 4, 2002);
        Student stu2 = new Student("3", "Student2 name", 3.3, 2003);
        List<Student> lst = new ArrayList<>();
        lst.add(stu);
        lst.add(stu1);
        lst.add(stu4);
        lst.add(stu2);
        em.getTransaction().begin();
        em.persist(stu);
        em.persist(stu1);
        em.persist(stu4);
        em.persist(stu2);
        em.getTransaction().commit();
        lst.forEach(student -> System.out.println(student));
        em.close();


    }

    public static void getStudentById(String id) {
        EntityManager em = emf.createEntityManager();
        Student stu = em.find(Student.class, id);
        System.out.println("Student found with id " + id + ": " + stu.toString());
    }

    public static void getAllStudents() {
        EntityManager em = emf.createEntityManager();
        List<Student> lst = em.createQuery("Select s from Student s", Student.class).getResultList();
        StringBuilder sb = new StringBuilder();

        System.out.println("LIST STUDENTS: ");
        for (Student student : lst) {
            sb.append("\t").append(student.toString()).append("\n");
        }

        System.out.println(sb.toString());
    }

    public static void getStudentsByConditions(String name, double gpa) {
        EntityManager em = emf.createEntityManager();
        List<Student> lst = em.createQuery("Select s from Student s where s.name like :pName and s.GPA > :pGPA").setParameter("pName", "%" + name + "%").setParameter("pGPA", gpa).getResultList();
        System.out.println("Student found with name contains the word" + name + " and have gpa > " + gpa + ": ");
        lst.forEach(stu -> System.out.println(stu.toString()));
    }

    public static void updateStudentById(String id, Student stuFix) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student stu = em.find(Student.class, id);

        if (stu != null) {
            stu.setName(stuFix.getName());
            stu.setGPA(stuFix.getGPA());
            stu.setYob(stuFix.getYob());
            em.getTransaction().commit();
            System.out.println("Student with id " + id + " updated!");
        } else {
            System.out.println("Student with id " + id + " not found.");
            em.getTransaction().rollback();
        }

        em.close();
    }


    public static void deleteStudentById(String id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student stu = em.find(Student.class, id);
        em.remove(stu);
        em.getTransaction().commit();
        System.out.println("Student with id " + id + " deleted!");
        em.close();
    }

}