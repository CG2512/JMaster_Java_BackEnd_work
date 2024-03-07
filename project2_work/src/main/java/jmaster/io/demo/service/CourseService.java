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
import jmaster.io.demo.dto.CourseDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.entity.Course;
import jmaster.io.demo.repository.CourseRepo;

public interface CourseService {
	void create(CourseDTO courseDTO);

	void update(CourseDTO courseDTO);

	void delete(int id);

	CourseDTO getById(int id);

	PageDTO<List<CourseDTO>> searchName(SearchDTO searchDTO);

}

@Service
class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepo courseRepo;

	@Transactional
	@Override
	public void create(CourseDTO courseDTO) {
		// TODO Auto-generated method stub
		Course course = new ModelMapper().map(courseDTO, Course.class);

		courseRepo.save(course);
	}

	@Transactional
	@Override
	public void update(CourseDTO courseDTO) {
		// TODO Auto-generated method stub
		Course course = courseRepo.findById(courseDTO.getId()).orElse(null);
		if (course != null) {
			course.setName(courseDTO.getName());
			courseRepo.save(course);
		}
	}

	@Transactional
	@Override
	public void delete(int id) {

		courseRepo.deleteById(id);
	}

	@Override
	public CourseDTO getById(int id) {

		Course course = courseRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(course);
	}

	private CourseDTO convert(Course course) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper.map(course, CourseDTO.class);
	}

	@Override
	public PageDTO<List<CourseDTO>> searchName(SearchDTO searchDTO) {

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
		Page<Course> page=null;
		if (StringUtils.hasText(searchDTO.getKeyword())) {
			 page = courseRepo.searchName("%" + searchDTO.getKeyword() + "%", pageRequest);
		}else {
			 page = courseRepo.findAll(pageRequest);
		}
		PageDTO<List<CourseDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<CourseDTO> courseDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T: List<UserDTO>
		pageDTO.setData(courseDTOs);
		return pageDTO;
	}

}
