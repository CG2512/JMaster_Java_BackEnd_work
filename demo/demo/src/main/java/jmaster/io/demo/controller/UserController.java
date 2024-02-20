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
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.service.UserService;

@Controller
public class UserController {

	@Autowired // DI
	UserService userService;

	@GetMapping("/user/list")
	public String list(

			Model model) {

		List<UserDTO> users = userService.getAll();

		// req.setAttribute("userList", users);
		model.addAttribute("userList", users);

		model.addAttribute("searchDTO", new SearchDTO());
		return "users.html";
	}

	@GetMapping("/user/new")
	public String newUser(Model model) {
		model.addAttribute("user", new UserDTO());
		return "new-user.html";
	}

	@PostMapping("/user/new")
	public String newUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult)
			throws IllegalStateException, IOException {
		if (bindingResult.hasErrors()) {
			return "new-user.html";
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
			return "users.html"; // khi co loi thi tra view(se bi mat du lieu)
		}

		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);

		model.addAttribute("userList", pageUser.getData()); // tra List<User>
		model.addAttribute("totalPage", pageUser.getTotalPages());
		model.addAttribute("totalElements", pageUser.getTotalElements());
		model.addAttribute("searchDTO", searchDTO);

		return "users.html";
	}

	@GetMapping("/user/edit") // ?id=1000
	public String edit(@RequestParam("id") int id, Model model) {
		UserDTO userDTO = userService.getById(id);
		model.addAttribute("user", userDTO); // day user qua view
		return "edit-user.html";
	}

	@PostMapping("/user/edit")
	public String edit(@ModelAttribute UserDTO userDTO) {
		userService.update(userDTO);

		return "redirect:/user/list";
	}

}
