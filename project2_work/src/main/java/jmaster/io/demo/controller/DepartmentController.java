package jmaster.io.demo.controller;

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
import jmaster.io.demo.dto.DepartmentDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.service.DepartmentService;

//REST
@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@PostMapping("/search") //jackson library
	public ResponseDTO<PageDTO<List<DepartmentDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {

		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(searchDTO);

	 return ResponseDTO.<PageDTO<List<DepartmentDTO>>>builder()
				.status(200)
				.data(pageDTO)
				.build();

	}
	
	@GetMapping("/")
	public ResponseDTO<DepartmentDTO> get(@RequestParam("id") int id) {
		
		return ResponseDTO.<DepartmentDTO>builder()
				.status(200)
				.data(departmentService.getById(id))
				.build();
	}
	/*
	 * @GetMapping("/new") public String create(Model model) {
	 * model.addAttribute("department", new DepartmentDTO()); return
	 * "department/new-department.html"; }
	 */

	@PostMapping("/")
	public ResponseDTO<Void> newDepartment(@ModelAttribute("department") @Valid DepartmentDTO departmentDTO) {

		departmentService.create(departmentDTO);
		
		return ResponseDTO.<Void>builder()
				.status(200).msg("ok").build();
			  
	}
	//Dung RequestBody neu upload bang json, chi upload duoc text
	@PostMapping("/json")
	public ResponseDTO<Void> createNewJSON(@RequestBody @Valid DepartmentDTO departmentDTO) {

		departmentService.create(departmentDTO);
		
		return ResponseDTO.<Void>builder()
				.status(200).msg("ok").build();
			  
	}

	@PutMapping("/")
	public ResponseDTO<DepartmentDTO> edit(@ModelAttribute DepartmentDTO departmentDTO) {
		departmentService.update(departmentDTO);

		return ResponseDTO.<DepartmentDTO>builder()
				.status(200).msg("ok").build();
	}

	@DeleteMapping("/") // ?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		departmentService.delete(id);
		
		return ResponseDTO.<Void>builder()
				.status(200).msg("ok").build();
	}

}
