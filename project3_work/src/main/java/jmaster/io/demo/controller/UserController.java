package jmaster.io.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired // DI
	UserService userService;
	
	@Value("${upload.folder}")
	private  String UPLOAD_FOLDER;


	@GetMapping("/list")
	public ResponseDTO<List<UserDTO>> list() {

		List<UserDTO> userDTOs = userService.getAll();

		return ResponseDTO.<List<UserDTO>>builder().status(200).data(userDTOs).build();
	}

	@PostMapping("/")
	public ResponseDTO<Void> newUser(@ModelAttribute("user") @Valid UserDTO userDTO)
			throws IllegalStateException, IOException {
		if (userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {
			if (!(new File(UPLOAD_FOLDER).exists())) {
				new File(UPLOAD_FOLDER).mkdirs();
			}
			String filename = userDTO.getFile().getOriginalFilename();
			// lay dinh dang file
			String extension = filename.substring(filename.lastIndexOf("."));
			// tao ten moi
			String newFilename = UUID.randomUUID().toString() + extension;

			File newFile = new File(UPLOAD_FOLDER + newFilename);

			userDTO.getFile().transferTo(newFile);

			userDTO.setAvatarURL(newFilename);// save to db
		}
		userService.create(userDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/download") // ?filename=abc.jpg (Example)
	public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws IOException {
		File file = new File("E:\\" + filename);
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

		userService.update(userDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@PostMapping("/forget-password")
	public ResponseDTO<Void> forgetPassword(@RequestParam String username){
		
		userService.forgetPassword(username);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();

	}

}
