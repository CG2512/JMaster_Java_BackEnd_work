package jmaster.io.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.StudentDTO;
import jmaster.io.demo.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired // DI
	StudentService studentService;

	@PostMapping("/")
	public ResponseDTO<Void> newUser(@RequestBody @Valid StudentDTO studentDTO) {

		studentService.create(studentDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/")
	public ResponseDTO<StudentDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<StudentDTO>builder().status(200).data(studentService.getById(id)).build();
	}

	@GetMapping("/list")
	public ResponseDTO<List<StudentDTO>> getAll() {
		return ResponseDTO.<List<StudentDTO>>builder().status(200).data(studentService.getAll()).build();
	}

	@DeleteMapping("/") // ?id=1000 public ResponseDTO<Void>
	public ResponseDTO delete(@RequestParam("id") int id) {
		studentService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> edit(@ModelAttribute("student") @Valid StudentDTO studentDTO)
			throws IllegalStateException, IOException {

		studentService.update(studentDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@PostMapping("/search") // jackson library
	public ResponseDTO<PageDTO<List<StudentDTO>>> search(@RequestBody SearchDTO searchDTO) {

		PageDTO<List<StudentDTO>> pageDTO = studentService.searchByCode(searchDTO);

		return ResponseDTO.<PageDTO<List<StudentDTO>>>builder().status(200).data(pageDTO).build();
	}
}
