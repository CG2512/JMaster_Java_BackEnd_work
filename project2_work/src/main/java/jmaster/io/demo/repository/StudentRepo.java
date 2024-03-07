package jmaster.io.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.entity.Student;

public interface StudentRepo extends JpaRepository<Student,Integer> {
	
	@Query("Select s FROM Student s WHERE s.studentCode LIKE :code")
	Page<Student> searchByCode(@Param("code") String code,Pageable pageable);
}
