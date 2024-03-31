package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.RoleDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.entity.Role;
import jmaster.io.demo.repository.RoleRepo;

public interface RoleService {
	void create(RoleDTO roleDTO);

	void update(RoleDTO roleDTO);

	void delete(int id);

	RoleDTO getById(int id);

	PageDTO<List<RoleDTO>> searchName(SearchDTO searchDTO);
}

@Service
class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepo roleRepo;

	@Transactional
	@Override
	public void create(RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		Role role = new ModelMapper().map(roleDTO, Role.class);
		roleRepo.save(role);
	}

	@Transactional
	@Override
	public void update(RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		Role role = roleRepo.findById(roleDTO.getId()).orElseThrow(NoResultException::new);

		role.setName(roleDTO.getName());

		roleRepo.save(role);
	}

	@Transactional
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		roleRepo.deleteById(id);
	}

	@Override
	public RoleDTO getById(int id) {

		Role role = roleRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(role);
	}

	@Override
	public PageDTO<List<RoleDTO>> searchName(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by("name").ascending();

		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}
		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(10);
		}
		if (searchDTO.getKeyword() == null) {
			searchDTO.setKeyword("");
		}

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<Role> page = null;
		if (StringUtils.hasText(searchDTO.getKeyword())) {
			page = roleRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		} else {
			page = roleRepo.findAll(pageRequest);
		}
		
		return PageDTO.<List<RoleDTO>>builder().totalPages(page.getTotalPages()).totalElements(page.getTotalElements())
				.data(page.get().map(u -> convert(u)).collect(Collectors.toList())).build();
	
	}

	private RoleDTO convert(Role role) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper.map(role, RoleDTO.class);
	}
}
