package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.DepartmentDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.entity.Department;
import jmaster.io.demo.entity.User;
import jmaster.io.demo.repository.UserRepo;

//@Component //nhu @Service
@Service //tao bean,new Userservice , quan li boi SpringContainer

public class UserService {
	@Autowired
	UserRepo userRepo;
	
	@Transactional //rollback data neu ham co van de khi CRUD
	public void create(UserDTO userDTO) {
		//Convert UserDTO-> User
		/*
		 * User user=new User(); user.setId(userDTO.getId());
		 * user.setAge(userDTO.getAge()); user.setName(userDTO.getName());
		 * user.setAvatarURL(userDTO.getAvatarURL());
		 * user.setUsername(userDTO.getUsername());
		 * user.setPassword(userDTO.getPassword());
		 * user.setHomeAddress(userDTO.getHomeAddress());
		 */
		
		User user=new ModelMapper().map(userDTO,User.class);
		//System.out.println("Department is "+user.getDepartment().getId());
		userRepo.save(user);
	}
	
	@Transactional //rollback data neu ham co van de khi CRUD
	public void delete(int id) {
		userRepo.deleteById(id);
	}
	
	@Transactional // create/update dung ham save cua JPA
	public void update(UserDTO userDTO) {
	
		//check xem User co ton tai truoc ko de update
		User currentUser=userRepo.findById(userDTO.getId()).orElse(null);
		//neu co thi update thuoc tinh
		if (currentUser != null) {
			
			  currentUser.setName(userDTO.getName()); currentUser.setAge(userDTO.getAge());
			  currentUser.setHomeAddress(userDTO.getHomeAddress());
			  currentUser.setBirthdate(userDTO.getBirthdate());
			  
			  DepartmentDTO newDepartmentDTO=userDTO.getDepartment();
			  
			  currentUser.setDepartment (new ModelMapper().map(newDepartmentDTO,
			  Department.class));
			 
		userRepo.save(currentUser);
		}
	}
	
	@Transactional // create/update dung ham save cua JPA
	public void updatePassword(UserDTO userDTO) {
		
		
		//check xem User co ton tai truoc ko de update
		User currentUser=userRepo.findById(userDTO.getId()).orElse(null);
		//neu co thi update thuoc tinh
		if (currentUser != null) {
		currentUser.setPassword(userDTO.getPassword());
		
		userRepo.save(currentUser);
		}
	}
	
	
	public UserDTO getById(int id) {
		//Optional <T>
		User user=userRepo.findById(id).orElse(null);
		if (user != null) {
			return convert(user);
		}
		return null;
	}
	
	public List<UserDTO> getAll(){
		List<User> userList=userRepo.findAll();
		/*
		 * List<UserDTO> userDTOList=new ArrayList<UserDTO>(); for (User user :
		 * userList) {
		 * 
		 * userDTOList.add(convert(user)); }
		 */
		//return userDTOList;
		
		//java 8
		
		return userList.stream().map(u -> convert(u))
				.collect(Collectors.toList());
	}
	
	public PageDTO<List<UserDTO>> searchName(
			SearchDTO searchDTO){
		
		Sort sortBy=Sort.by("name").ascending()
				.and(Sort.by("age").descending()); //sap xep du lieu trong page theo thu tu thuoc tinh
		
		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy=Sort.by(searchDTO.getSortedField()).ascending();
		}
		
		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(10);
		}
		PageRequest pageRequest=PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(),sortBy);
		Page<User> page=userRepo.searchByName("%"+ searchDTO.getKeyword() + "%",pageRequest);
		
		PageDTO<List<UserDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
			
		List<UserDTO> userDTOs= page.get().map(u -> convert(u))
				.collect(Collectors.toList());
		
		//T: List<UserDTO>
		pageDTO.setData(userDTOs);
		return pageDTO;
	}
	
	private UserDTO convert(User user) {
		/*
		 * UserDTO userDTO=new UserDTO();
		 * 
		 * userDTO.setId(user.getId()); userDTO.setAge(user.getAge());
		 * userDTO.setName(user.getName()); userDTO.setAvatarURL(user.getAvatarURL());
		 * userDTO.setUsername(user.getUsername());
		 * userDTO.setPassword(user.getPassword());
		 * userDTO.setHomeAddress(user.getHomeAddress());
		 */
		
		return new ModelMapper().map(user,UserDTO.class);
	}
}
