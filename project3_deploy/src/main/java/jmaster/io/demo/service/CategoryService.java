package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.CategoryDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.entity.Category;
import jmaster.io.demo.repository.CategoryRepo;

public interface CategoryService {

	void create(CategoryDTO categoryDTO);

	void delete(int id);

	void update(CategoryDTO categoryDTO);

	CategoryDTO getById(int id);

	PageDTO<List<CategoryDTO>> search(SearchDTO searchDTO);

}

@Service
class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;

	@Transactional
	@Override
	public void create(CategoryDTO categoryDTO) {
		Category category = new ModelMapper().map(categoryDTO, Category.class);
		categoryRepo.save(category);
	}

	@Transactional
	@Override
	public void delete(int id) {
		categoryRepo.deleteById(id);
	}

	@Transactional
	@Override
	public void update(CategoryDTO categoryDTO) {
		Category category = categoryRepo.findById(categoryDTO.getId()).orElseThrow(NoResultException::new);
		category.setName(categoryDTO.getName());
		categoryRepo.save(category);
	}

	@Override
	public CategoryDTO getById(int id) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(category);
	}

	private CategoryDTO convert(Category category) {
		return new ModelMapper().map(category, CategoryDTO.class);
	}

	@Override
	public PageDTO<List<CategoryDTO>> search(SearchDTO searchDTO) {
		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(10);
		}
		if (searchDTO.getKeyword() == null) {
			searchDTO.setKeyword("");
		}

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize());
		Page<Category> page = null;
		if (StringUtils.hasText(searchDTO.getKeyword())) {
			page = categoryRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		} else {
			page = categoryRepo.findAll(pageRequest);
		}
		PageDTO<List<CategoryDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<CategoryDTO> categoryDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T: List<UserDTO>
		pageDTO.setData(categoryDTOs);
		return pageDTO;
	}
}
