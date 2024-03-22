package jmaster.io.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

	@Query("SELECT c FROM Category c WHERE c.name LIKE :x ")
	Page<Category>searchByName(@Param("x") String name,Pageable pageable);
	
	Category findByName(String name);
}
