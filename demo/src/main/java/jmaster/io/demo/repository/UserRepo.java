package jmaster.io.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.dto.User;

public interface UserRepo
extends JpaRepository<User, Integer>{
	//tim theo username
	//Select user where username=?
	User findByUsername(String username);
	
	//where name = :s
	List<User> findByName(String s);
	
	@Query("Select u FROM User u WHERE u.name LIKE :x")
	List<User> searchByName(@Param("x") String s);
	
	@Query("Select u FROM User u WHERE u.name LIKE :x"
			+ "	OR u.username LIKE :x")
	List<User> searchByNameAndUsername(@Param("x") String s);
	
	@Modifying //Dung khi thay doi du lieu trong DB
	@Query("DELETE FROM User u WHERE u.username= :x")
	int deleteUser(@Param("x") String username);
	
	//tu generate cau lenh xoa
	void deleteByUsername(String username);
}
