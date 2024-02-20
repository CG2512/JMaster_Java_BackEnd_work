package jmaster.io.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jmaster.io.demo.dto.User;
import jmaster.io.demo.service.UserService;

@Controller
public class UserController {
	
	@Autowired //DI
	UserService userService;
	
	@GetMapping("/user/list")
	public String list(
			
			Model model) {
		
		List<User> users=userService.getAll();
			
		//req.setAttribute("userList", users);
		model.addAttribute("userList", users);
		return "users.html";
	}
	
	@GetMapping("/user/new")
	public String newUser() {
		return "new-user.html";
	}

	
	@PostMapping("/user/new")
	public String newUser(
						@ModelAttribute User user) {		
		userService.create(user);
		
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/delete") //?id=1000
	public String delete(@RequestParam("id") int id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/search")
	public String search(Model model,
			@RequestParam("keyword") String keyword) {
		List<User> users=userService.searchName(keyword);
		
		model.addAttribute("userList", users);
		return "users.html";
	}
	
	@GetMapping("/user/edit") //?id=1000
	public String edit(@RequestParam("id") int id,
			Model model) {
		User user = userService.getById(id);
		model.addAttribute("user", user); //day user qua view
		return "edit-user.html";
	}
	
	@PostMapping("/user/edit")
	public String edit(@ModelAttribute User user) {
		userService.update(user);
		
		return "redirect:/user/list";
	}
	
}
