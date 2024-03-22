package jmaster.io.demo.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.entity.User;
import jmaster.io.demo.repository.RoleRepo;
import jmaster.io.demo.repository.UserRepo;

//@Component //nhu @Service
@Service // tao bean,new Userservice , quan li boi SpringContainer

public class UserService {
	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	EmailService emailService;

	@Transactional // rollback data neu ham co van de khi CRUD
	
	public void create(UserDTO userDTO) {

		User user = new ModelMapper().map(userDTO, User.class);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepo.save(user);
	}

	@Transactional // rollback data neu ham co van de khi CRUD
	
	public void delete(int id) {
		userRepo.deleteById(id);
	}

	@Transactional
	
	public void update(UserDTO userDTO) {

		User currentUser = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);

		currentUser.setName(userDTO.getName());
		currentUser.setBirthdate(userDTO.getBirthdate());

		currentUser.setRoles(userDTO.getRoles().stream().map(role -> roleRepo.findById(role.getId()).orElse(null))
				.filter(r -> r != null).collect(Collectors.toList()));
		
		if (userDTO.getAvatarURL() != null){
			currentUser.setAvatarURL(userDTO.getAvatarURL());
		}
		currentUser.setEmail(userDTO.getEmail());
		userRepo.save(currentUser);
	}

	@Transactional // create/update dung ham save cua JPA
	
	public void updatePassword(UserDTO userDTO) {
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		if (currentUser != null) {
			currentUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

			userRepo.save(currentUser);
		}
	}

	public UserDTO getById(int id) {
		// Optional <T>
		User user = userRepo.findById(id).orElse(null);
		if (user != null) {
			return convert(user);
		}
		return null;
	}

	public List<UserDTO> getAll() {
		List<User> userList = userRepo.findAll();
		return userList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

	public PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO) {

		Sort sortBy = Sort.by("name"); // sap xep du lieu trong page theo
																					// thu tu thuoc tinh

		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}

		if (searchDTO.getSize() == null) {
			searchDTO.setSize(10);
		}
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<User> page = userRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);

		PageDTO<List<UserDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<UserDTO> userDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());

		// T: List<UserDTO>
		pageDTO.setData(userDTOs);
		return pageDTO;
	}
	

	private UserDTO convert(User user) {

		return new ModelMapper().map(user, UserDTO.class);
	}

	
	@Transactional
	public void forgetPassword(String username) {
	User user=userRepo.findByUsername(username);
	if (user != null) {
		String password=createPassword();
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		userRepo.save(user);
		
		emailService.sendPassword(user.getEmail(),password);
	}
		
	}
    
	private String createPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		   Random random = new SecureRandom();
		   
		   StringBuilder sb = new StringBuilder(6);
	        for (int i = 0; i < 6; i++) {
	            sb.append(characters.charAt(random.nextInt(characters.length())));
	        }

	        return sb.toString();
	}

	public UserDTO findByUsername(String username) {
		User user=userRepo.findByUsername(username);
		if (user == null)
			throw new NoResultException();
		return convert(user);
	}
	
}
