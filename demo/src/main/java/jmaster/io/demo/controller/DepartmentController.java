package jmaster.io.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jmaster.io.demo.dto.DepartmentDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.service.DepartmentService;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	
	@GetMapping("/list")
	public String list(

			Model model) {
		
		/*
		 * SearchDTO searchDTO=new SearchDTO(); searchDTO.setKeyword("");
		 * 
		 * PageDTO<List<DepartmentDTO>> pageDepartment =
		 * departmentService.search(searchDTO);
		 * 
		 * model.addAttribute("departmentList", pageDepartment.getData()); // tra
		 * List<User> model.addAttribute("totalPage",pageDepartment.getTotalPages());
		 * model.addAttribute("totalElements", pageDepartment.getTotalElements());
		 * model.addAttribute("searchDTO", searchDTO);
		 */

		return "redirect:/department/search";
	}
	@GetMapping("/search")
	public String search(Model model, @ModelAttribute @Valid SearchDTO searchDTO, BindingResult bindingResult) {
		// khi empty,mac dinh la null.Neu la int thi bat buoc phai la so
		// tuy bien: bindingResult.rejectValue("size","something")
		if (bindingResult.hasErrors()) {
			return "department/departments.html"; // khi co loi thi tra view(se bi mat du lieu)
		}
		
		
		PageDTO<List<DepartmentDTO>> pageDepartment = departmentService.search(searchDTO);

		model.addAttribute("departmentList", pageDepartment.getData()); // tra List<User>
		model.addAttribute("totalPage",pageDepartment.getTotalPages());
		model.addAttribute("totalElements", pageDepartment.getTotalElements());
		model.addAttribute("searchDTO", searchDTO);

		return "department/departments.html";
	}
	
	@GetMapping("/new")
	public String create(Model model) {
		model.addAttribute("department", new DepartmentDTO());
		return "department/new-department.html";
	}
	
	@PostMapping("/new")
	public String newDepartment(@ModelAttribute("department") @Valid DepartmentDTO departmentDTO, BindingResult bindingResult)
			throws IllegalStateException, IOException {
		if (bindingResult.hasErrors()) {
			return "department/new-department.html";
		}
				departmentService.create(departmentDTO);

		return "redirect:/department/list";
	}
	
	@GetMapping("/edit") // ?id=1000
	public String edit(@RequestParam("id") int id, Model model) {
		DepartmentDTO departmentDTO = departmentService.getById(id);
		model.addAttribute("department", departmentDTO); // day user qua view
		return "department/edit-department.html";
	}

	@PostMapping("/edit")
	public String edit(@ModelAttribute DepartmentDTO departmentDTO) {
		departmentService.update(departmentDTO);

		return "redirect:/department/list";
	}
	
	@GetMapping("/delete") // ?id=1000
	public String delete(@RequestParam("id") int id) {
		departmentService.delete(id);
		return "redirect:/department/list";
	}

}
