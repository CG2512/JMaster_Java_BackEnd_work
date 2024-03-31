package jmaster.io.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	@Query("SELECT r FROM Role r WHERE r.name LIKE :x ")
	Page<Role> searchByName(@Param("x") String name, Pageable pageable);
	
	Role findByName(String name);
}
