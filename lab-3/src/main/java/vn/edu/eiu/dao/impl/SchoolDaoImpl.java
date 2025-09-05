package vn.edu.eiu.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eiu.dao.SchoolDao;
import vn.edu.eiu.model.School;
import vn.edu.eiu.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class SchoolDaoImpl implements SchoolDao {

    @Override
    public Optional<School> findById(String id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(School.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<School> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<School> query = em.createQuery("SELECT s FROM School s", School.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public School save(School school) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(school);
            em.getTransaction().commit();
            return school;
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
    public School update(School school) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            School updatedSchool = em.merge(school);
            em.getTransaction().commit();
            return updatedSchool;
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
    public void delete(School school) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(school)) {
                school = em.merge(school);
            }
            em.remove(school);
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
