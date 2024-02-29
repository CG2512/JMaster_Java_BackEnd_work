package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.DepartmentDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.entity.Department;
import jmaster.io.demo.repository.DepartmentRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	@Override
	@Transactional
	public void create(DepartmentDTO departmentDTO) {
		// TODO Auto-generated method stub
		Department department = new ModelMapper().map(departmentDTO, Department.class);
		departmentRepo.save(department);
	}

	@Override
	@Transactional
	public void update(DepartmentDTO departmentDTO) {
		// TODO Auto-generated method stub
		Department department = departmentRepo.findById(departmentDTO.getId()).orElse(null);
		// neu co thi update thuoc tinh
		if (department != null) {
			department.setName(departmentDTO.getName());

			departmentRepo.save(department);

		}
	}
	
	@Override
	@Transactional
	public void delete(int id) {
		// TODO Auto-generated method stub
		
		departmentRepo.deleteById(id);
	}
	
	
	@Override
	public DepartmentDTO getById(int id) {
		// TODO Auto-generated method stub
		Department department = departmentRepo.findById(id).orElseThrow(NoResultException::new);

		return new ModelMapper().map(department, DepartmentDTO.class);

	}

	@Override
	public PageDTO<List<DepartmentDTO>> search(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by("name").ascending();
		// sap xep du lieu trong page theo thu tu thuoc tinh

		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}

		if (searchDTO.getSize() == null) {
			searchDTO.setSize(10);
		}
		
		if(searchDTO.getKeyword() == null) {
			searchDTO.setKeyword("");
		}
		
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<Department> page = departmentRepo.searchName("%" + searchDTO.getKeyword() + "%", pageRequest);

		PageDTO<List<DepartmentDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<DepartmentDTO> departmentDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T: List<UserDTO>
		pageDTO.setData(departmentDTOs);
		return pageDTO;
	}

	private DepartmentDTO convert(Department department) {
		/*
		 * UserDTO userDTO=new UserDTO();
		 * 
		 * userDTO.setId(user.getId()); userDTO.setAge(user.getAge());
		 * userDTO.setName(user.getName()); userDTO.setAvatarURL(user.getAvatarURL());
		 * userDTO.setUsername(user.getUsername());
		 * userDTO.setPassword(user.getPassword());
		 * userDTO.setHomeAddress(user.getHomeAddress());
		 */

		return new ModelMapper().map(department, DepartmentDTO.class);
	}
}
