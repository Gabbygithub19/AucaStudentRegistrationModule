package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

import model.AcademicUnit;

public class AcademicUnitDao {

    private EntityManager entityManager;

    public AcademicUnitDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(AcademicUnit academicUnit) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(academicUnit);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public AcademicUnit findById(String id) {
        return entityManager.find(AcademicUnit.class, id);
    }
    
    public AcademicUnit getAcademicUnitById(String academicUnitId) {
        return entityManager.find(AcademicUnit.class, academicUnitId);
    }

    public List<AcademicUnit> findAll() {
        TypedQuery<AcademicUnit> query = entityManager.createQuery("SELECT a FROM AcademicUnit a", AcademicUnit.class);
        return query.getResultList();
    }
    public List<AcademicUnit> findDepartments() {
        TypedQuery<AcademicUnit> query = entityManager.createQuery("SELECT a FROM AcademicUnit a WHERE a.type = 'DEPARTMENT'", AcademicUnit.class);
        return query.getResultList();
    }


    public void update(AcademicUnit academicUnit) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(academicUnit);
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
            AcademicUnit academicUnit = entityManager.find(AcademicUnit.class, id);
            if (academicUnit != null) {
                entityManager.remove(academicUnit);
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
