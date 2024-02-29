package jmaster.io.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jmaster.io.demo.dto.DepartmentDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.service.DepartmentService;
import jmaster.io.demo.service.UserService;

@Controller
public class UserController {

	@Autowired // DI
	UserService userService;
	
	@Autowired
	DepartmentService departmentService;

	@GetMapping("/user/list")
	public String list(

			Model model) {

		List<UserDTO> users = userService.getAll();
		
		model.addAttribute("userList", users);

		model.addAttribute("searchDTO", new SearchDTO());
		return "user/users.html";
	}

	@GetMapping("/user/new")
	public String newUser(Model model) {
		
		PageDTO<List<DepartmentDTO>> pageDTO
		=departmentService.search(new SearchDTO());
				
		model.addAttribute("user", new UserDTO());
		model.addAttribute("departmentList", pageDTO.getData());
		return "user/new-user.html";
	}

	@PostMapping("/user/new")
	public String newUser(@ModelAttribute("user") @Valid UserDTO userDTO
			,BindingResult bindingResult
			,Model model)
			throws IllegalStateException, IOException {
		if (bindingResult.hasErrors()) {
			PageDTO<List<DepartmentDTO>> pageDTO
			=departmentService.search(new SearchDTO());
			
			model.addAttribute("departmentList", pageDTO.getData());
			return "user/new-user.html";
		}
		if (!userDTO.getFile().isEmpty()) {
			// ten file
			MultipartFile uploadFile = userDTO.getFile();
			String fileName = uploadFile.getOriginalFilename();
			// luu lai file vao o cung may chu
			File saveFile = new File("E:/" + fileName);
			uploadFile.transferTo(saveFile);
			// luu ten file vao DATABASE
			userDTO.setAvatarURL(fileName);
		}
		userService.create(userDTO);
		

		return "redirect:/user/list";
	}

	@GetMapping("/user/download") // ?filename=abc.jpg (Example)
	public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws IOException {
		File file = new File("E:/" + filename);
		Files.copy(file.toPath(), resp.getOutputStream());
	}

	@GetMapping("/user/delete") // ?id=1000
	public String delete(@RequestParam("id") int id) {
		userService.delete(id);
		return "redirect:/user/list";
	}

	@GetMapping("/user/search")
	public String search(Model model, @ModelAttribute @Valid SearchDTO searchDTO, BindingResult bindingResult) {
		// khi empty,mac dinh la null.Neu la int thi bat buoc phai la so
		// tuy bien: bindingResult.rejectValue("size","something")
		if (bindingResult.hasErrors()) {
			return "user/users.html"; // khi co loi thi tra view(se bi mat du lieu)
		}

		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);

		model.addAttribute("userList", pageUser.getData()); // tra List<User>
		model.addAttribute("totalPage", pageUser.getTotalPages());
		model.addAttribute("totalElements", pageUser.getTotalElements());
		model.addAttribute("searchDTO", searchDTO);

		return "user/users.html";
	}

	@GetMapping("/user/edit") // ?id=1000
	public String edit(@RequestParam("id") int id, Model model) {
		UserDTO userDTO = userService.getById(id);
		model.addAttribute("user", userDTO); // day user qua view
		
		PageDTO<List<DepartmentDTO>> pageDTO
		=departmentService.search(new SearchDTO());
		
		model.addAttribute("departmentList", pageDTO.getData());
		model.addAttribute("user", userDTO); // day user qua view
		return "user/edit-user.html";
	}

	@PostMapping("/user/edit")
	public String edit(@ModelAttribute("user") @Valid UserDTO userDTO
			,BindingResult bindingResult
			,Model model) {

		if (bindingResult.hasErrors()) {
					
			PageDTO<List<DepartmentDTO>> pageDTO
			=departmentService.search(new SearchDTO());
			
			model.addAttribute("departmentList", pageDTO.getData());
			return "user/edit-user.html";
		}
		userService.update(userDTO);

		return "redirect:/user/list";
	}

}
