package jmaster.io.demo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import jmaster.io.demo.entity.Role;
import jmaster.io.demo.entity.User;
import jmaster.io.demo.repository.RoleRepo;
import jmaster.io.demo.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DemoData implements ApplicationRunner {

	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		log.info("BEGIN INSERT DUMP");
		Role role = new Role();
		role.setId(10);
		role.setName("ROLE_ADMIN");
		if (roleRepo.findByName(role.getName()) == null) {
			try {
				roleRepo.save(role);
				log.info("INSERT DUMP");
				User user=new User();
				user.setUsername("sysadmin");
				user.setName("SYS_ADMIN");
				user.setPassword(new BCryptPasswordEncoder().encode("123456"));
				user.setEmail("admin@gmail.com");
				user.setBirthdate(new Date());
				List<Role> roles=new ArrayList<Role>();
				roles.add(role);
				user.setRoles(roles);
				
				userRepo.save(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
