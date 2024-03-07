package jmaster.io.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.service.DepartmentService;
import jmaster.io.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired // DI
	UserService userService;

	@Autowired
	DepartmentService departmentService;

	@GetMapping("/list")
	public ResponseDTO<List<UserDTO>> list() {

		List<UserDTO> userDTOs = userService.getAll();

		return ResponseDTO.<List<UserDTO>>builder().status(200).data(userDTOs).build();
	}

	@PostMapping("/")
	public ResponseDTO<Void> newUser(@ModelAttribute("user") @Valid UserDTO userDTO)
			throws IllegalStateException, IOException {
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

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/download") // ?filename=abc.jpg (Example)
	public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws IOException {
		File file = new File("E:/" + filename);
		Files.copy(file.toPath(), resp.getOutputStream());
	}

	@DeleteMapping("/") // ?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		userService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@PostMapping("/search")
	public ResponseDTO<PageDTO<List<UserDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {

		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);

		return ResponseDTO.<PageDTO<List<UserDTO>>>builder().status(200).data(pageUser).build();

	}

	@PutMapping("/")
	public ResponseDTO<Void> edit(@ModelAttribute("user") @Valid UserDTO userDTO)
			throws IllegalStateException, IOException {

		/*
		 * if (!userDTO.getFile().isEmpty()) {
		 * 
		 * MultipartFile uploadFile = userDTO.getFile(); String fileName =
		 * uploadFile.getOriginalFilename();
		 * 
		 * File saveFile = new File("E:/" + fileName); uploadFile.transferTo(saveFile);
		 * 
		 * userDTO.setAvatarURL(fileName); }
		 */
		userService.update(userDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

}
