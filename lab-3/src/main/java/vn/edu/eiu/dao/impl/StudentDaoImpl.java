package vn.edu.eiu.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eiu.dao.StudentDao;
import vn.edu.eiu.model.Student;
import vn.edu.eiu.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {

    @Override
    public Optional<Student> findById(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Student.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Student> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Student save(Student student) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return student;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Student update(Student student) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Student updatedStudent = em.merge(student);
            em.getTransaction().commit();
            return updatedStudent;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Student student) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(student)) {
                student = em.merge(student);
            }
            em.remove(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
