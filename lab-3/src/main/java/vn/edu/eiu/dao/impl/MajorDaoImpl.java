package vn.edu.eiu.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.edu.eiu.dao.MajorDao;
import vn.edu.eiu.model.Major;
import vn.edu.eiu.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class MajorDaoImpl implements MajorDao {

    @Override
    public Optional<Major> findById(String id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Major.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Major> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Major> query = em.createQuery("SELECT m FROM Major m", Major.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Major save(Major major) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(major);
            em.getTransaction().commit();
            return major;
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
    public Major update(Major major) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Major updatedMajor = em.merge(major);
            em.getTransaction().commit();
            return updatedMajor;
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
    public void delete(Major major) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(major)) {
                major = em.merge(major);
            }
            em.remove(major);
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
