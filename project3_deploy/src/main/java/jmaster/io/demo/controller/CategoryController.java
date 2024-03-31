package jmaster.io.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jmaster.io.demo.dto.CategoryDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.service.CategoryService;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@PostMapping("/")
	public ResponseDTO<Void> newCategory(@RequestBody @Valid CategoryDTO categoryDTO)
			throws IllegalStateException, IOException {
		categoryService.create(categoryDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@DeleteMapping("/{id}") // ?id=1000
	public ResponseDTO<Void> delete(@PathVariable("id") int id) {
		categoryService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> edit(@RequestBody @Valid CategoryDTO categoryDTO)
			throws IllegalStateException, IOException {

		categoryService.update(categoryDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/search")
	public ResponseDTO<PageDTO<List<CategoryDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {

		PageDTO<List<CategoryDTO>> pageRole = categoryService.search(searchDTO);

		return ResponseDTO.<PageDTO<List<CategoryDTO>>>builder().status(200).data(pageRole).build();
	}
	
	@GetMapping("/{id}") // 10
	public ResponseDTO<CategoryDTO> get(@PathVariable("id") int id) {
		CategoryDTO categoryDTO = categoryService.getById(id);
		return ResponseDTO.<CategoryDTO>builder().status(200).data(categoryDTO).build();
	}
	
}
