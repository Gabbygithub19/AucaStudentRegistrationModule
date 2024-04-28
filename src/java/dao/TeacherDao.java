package dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Teacher;

import java.util.List;

public class TeacherDao {

    private EntityManager entityManager;

    public TeacherDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Teacher findById(String id) {
        return entityManager.find(Teacher.class, id);
    }

    public List<Teacher> findAll() {
        String queryString = "SELECT t FROM teacher t";
        TypedQuery<Teacher> query = entityManager.createQuery(queryString, Teacher.class);
        return query.getResultList();
    }

    public void save(Teacher teacher) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(teacher);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Teacher teacher) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(teacher);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Teacher teacher = entityManager.find(Teacher.class, id);
            if (teacher != null) {
                entityManager.remove(teacher);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
