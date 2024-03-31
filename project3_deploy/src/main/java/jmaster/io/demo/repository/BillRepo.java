package jmaster.io.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.entity.Bill;

public interface BillRepo extends JpaRepository<Bill, Integer> {
	@Query("SELECT b FROM Bill b JOIN b.user u JOIN FETCH b.billItems bi WHERE b.createdAt >= :x")
	List<Bill> searchByDate(@Param("x") Date s);

	@Query("SELECT b FROM Bill b JOIN b.user u WHERE b.user.username LIKE :x")
	Page<Bill> searchByUsername(@Param("x") String s,Pageable pageable);
}
