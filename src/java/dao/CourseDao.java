package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.*;


import java.util.List;

public class CourseDao {

    private EntityManager entityManager;

    public CourseDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Course findById(String id) {
        return entityManager.find(Course.class, id);
    }

    public List<Course> findAll() {
        String queryString = "SELECT c FROM Course c";
        TypedQuery<Course> query = entityManager.createQuery(queryString, Course.class);
        return query.getResultList();
    }

    public List<Course> getCoursesByAcademicUnitAndSemester(String academicUnitId, String semesterId) {
        String jpql = "SELECT c FROM Course c " +
                      "WHERE c.department.id = :academicUnitId " +
                      "AND c.semester.id = :semesterId";

        TypedQuery<Course> query = entityManager.createQuery(jpql, Course.class);
        query.setParameter("academicUnitId", academicUnitId);
        query.setParameter("semesterId", semesterId);

        return query.getResultList();
    }

    public List<Course> getCoursesByStudentId(String studentId) {
        String jpql = "SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId";
        TypedQuery<Course> query = entityManager.createQuery(jpql, Course.class);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    public List<Course> getCoursesByAcademicUnit(String academicUnitId) {
        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c WHERE c.department.id = :academicUnitId", Course.class);
        query.setParameter("academicUnitId", academicUnitId);
        return query.getResultList();
    }

    public List<Course> getCoursesBySemester(String semesterId) {
        TypedQuery<Course> query = entityManager.createQuery(
            "SELECT c FROM Course c WHERE c.semester.id = :semesterId", Course.class);
        query.setParameter("semesterId", semesterId);
        return query.getResultList();
    }

    public void save(Course course) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(course);
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
            Course course = entityManager.find(Course.class, id);
            if (course != null) {
                entityManager.remove(course);
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
