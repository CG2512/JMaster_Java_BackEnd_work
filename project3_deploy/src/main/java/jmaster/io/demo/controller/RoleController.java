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
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.RoleDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.service.RoleService;

@RestController
@RequestMapping("/admin/role")
public class RoleController {
	@Autowired
	RoleService roleService;
	
	@PostMapping("/")
	public ResponseDTO<Void> newRole(@RequestBody @Valid RoleDTO roleDTO)
			throws IllegalStateException, IOException {
		roleService.create(roleDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@DeleteMapping("/{id}") // ?id=1000
	public ResponseDTO<Void> delete(@PathVariable("id") int id) {
		roleService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@PutMapping("/")
	public ResponseDTO<Void> edit(@RequestBody @Valid RoleDTO roleDTO)
			throws IllegalStateException, IOException {

		roleService.update(roleDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/search")
	public ResponseDTO<PageDTO<List<RoleDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {

		PageDTO<List<RoleDTO>> pageRole = roleService.searchName(searchDTO);

		return ResponseDTO.<PageDTO<List<RoleDTO>>>builder().status(200).data(pageRole).build();

	}
}
