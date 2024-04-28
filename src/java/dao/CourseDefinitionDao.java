package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.*;

public class CourseDefinitionDao {
	
	   private EntityManager entityManager;

	    public CourseDefinitionDao (EntityManager entityManager) {
	        this.entityManager = entityManager;
	    }

	    public void save(CourseDefinition definition) {
	        EntityTransaction transaction = entityManager.getTransaction();
	        try {
	            transaction.begin();
	            entityManager.persist(definition);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null && transaction.isActive()) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

}
