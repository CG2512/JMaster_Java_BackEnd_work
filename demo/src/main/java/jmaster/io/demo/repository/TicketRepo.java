package jmaster.io.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.entity.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
	@Query("SELECT t FROM Ticket t WHERE t.clientPhone LIKE :x ")
	Page<Ticket> searchByName(@Param("x") String s, Pageable pageable);

	@Query("SELECT t FROM Ticket t " + "WHERE t.createdAt >= :start and t.createdAt <= :end")
	Page<Ticket> searchByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);

	@Query("SELECT t FROM Ticket t JOIN t.department d" + " WHERE d.id = :x ")
	
	Page<Ticket> searchByDepartmentId(@Param("x") int dId, Pageable pageable);

	@Query("SELECT t FROM Ticket t JOIN t.department d" + " WHERE d.name = :x ")
	Page<Ticket> searchByDepartmentName(@Param("x") String dName, Pageable pageable);

	Page<Ticket> findByStatus(boolean status, Pageable pageable);
	
	
}
