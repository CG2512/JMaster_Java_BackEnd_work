package jmaster.io.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jmaster.io.demo.entity.Bill;

@Repository
public class BillDAO {
	@PersistenceContext
	EntityManager entityManager;
	
	public List<Bill> searchByDate(Date d){
		String jpql="SELECT b FROM Bill b WHERE b.createdAt >= :x";
		
		return entityManager.createQuery(jpql).setParameter("x", d).setMaxResults(10).setFirstResult(0).getResultList();
		
	}
}
