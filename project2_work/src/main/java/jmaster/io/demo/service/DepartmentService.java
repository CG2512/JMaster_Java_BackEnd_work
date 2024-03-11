package jmaster.io.demo.service;

import java.util.List;

import jmaster.io.demo.dto.DepartmentDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;

public interface DepartmentService {
	void create(DepartmentDTO departmentDTO);

	DepartmentDTO update(DepartmentDTO departmentDTO);

	void delete(int id);

	DepartmentDTO getById(int id);

	PageDTO<List<DepartmentDTO>> search(SearchDTO searchDTO);
	
	
}


