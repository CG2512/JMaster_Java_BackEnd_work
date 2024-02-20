package jmaster.io.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.User;
import jmaster.io.demo.repository.UserRepo;

//@Component //nhu @Service
@Service //tao bean,new Userservice , quan li boi SpringContainer

public class UserService {
	@Autowired
	UserRepo userRepo;
	
	@Transactional //rollback data neu ham co van de khi CRUD
	public void create(User user) {
		userRepo.save(user);
	}
	
	@Transactional //rollback data neu ham co van de khi CRUD
	public void delete(int id) {
		userRepo.deleteById(id);
	}
	
	@Transactional // create/update dung ham save cua JPA
	public void update(User user) {
		//check xem User co ton tai truoc ko de update
		User currentUser=userRepo.findById(user.getId()).orElse(null);
		//neu co thi update thuoc tinh
		if (currentUser != null) {
		currentUser.setName(user.getName());
		currentUser.setAge(user.getAge());
		currentUser.setHomeAddress(user.getHomeAddress());
		userRepo.save(currentUser);
		}
	}
	
	@Transactional // create/update dung ham save cua JPA
	public void updatePassword(User user) {
		//check xem User co ton tai truoc ko de update
		User currentUser=userRepo.findById(user.getId()).orElse(null);
		//neu co thi update thuoc tinh
		if (currentUser != null) {
		currentUser.setPassword(user.getPassword());
		
		userRepo.save(currentUser);
		}
	}
	
	
	public User getById(int id) {
		//Optional <T>
		return userRepo.findById(id).orElse(null);
	}
	
	public List<User> getAll(){
		return userRepo.findAll();
	}
	
	public List<User> searchName(String name){
		return userRepo.searchByName(name);
	}
}
