package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.*;
import java.util.List;

public class StudentDao {

    private EntityManager entityManager;
    
    public StudentDao() {
    	
    }

    public StudentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Student student) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Student findById(String id) {
        return entityManager.find(Student.class, id);
    }

    public List<Student> getStudentsByDepartmentId(String departmentId) {
        String jpql = "SELECT s FROM Student s WHERE s.department.id = :departmentId";
        TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
    }

    
    public List<Student> findAll() {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

    public void update(Student student) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(student);
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
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                entityManager.remove(student);
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
