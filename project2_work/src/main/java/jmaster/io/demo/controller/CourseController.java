package jmaster.io.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jmaster.io.demo.dto.CourseDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	CourseService courseService;

	@PostMapping("/")
	public ResponseDTO<Void> newCourse(@RequestBody @Valid CourseDTO courseDTO) {

		courseService.create(courseDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/")
	public ResponseDTO<CourseDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<CourseDTO>builder().status(200).data(courseService.getById(id)).build();
	}

	@DeleteMapping("/") // ?id=1000 public ResponseDTO<Void>
	public ResponseDTO delete(@RequestParam("id") int id) {
		courseService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> edit(@RequestBody CourseDTO courseDTO) {

		courseService.update(courseDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/search")
	public ResponseDTO<PageDTO<List<CourseDTO>>> search(@RequestBody SearchDTO searchDTO){
		return ResponseDTO.<PageDTO<List<CourseDTO>>>builder().status(200).data(courseService.searchName(searchDTO)).build();
	}

}
