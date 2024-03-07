package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.StudentDTO;
import jmaster.io.demo.entity.Student;
import jmaster.io.demo.repository.StudentRepo;
import jmaster.io.demo.repository.UserRepo;

public interface StudentService {
	void create(StudentDTO studentDTO);

	void update(StudentDTO studentDTO);

	void delete(int id);

	StudentDTO getById(int id);

	List<StudentDTO> getAll();

	PageDTO<List<StudentDTO>> searchByCode(SearchDTO searchDTO);

}

@Service
class StudentServicelmpl implements StudentService {

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	UserRepo userRepo;

	@Override
	@Transactional
	public void create(StudentDTO studentDTO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Student student = modelMapper.map(studentDTO, Student.class);

		student.setStudentCode(studentDTO.getStudentCode());

		studentRepo.save(student);
	}

	@Override
	@Transactional
	public void update(StudentDTO studentDTO) {
		// TODO Auto-generated method stub
		Student student = studentRepo.findById(studentDTO.getUser().getId()).orElseThrow(NoResultException::new);

		if (student != null) {
			// maybe edit score?
			// do later
			student.setStudentCode(studentDTO.getStudentCode());
			student.getUser().setName(studentDTO.getUser().getName());

			studentRepo.save(student);

		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		// TODO Auto-generated method stub
		studentRepo.deleteById(id);
	}

	@Override
	public StudentDTO getById(int id) {
		// TODO Auto-generated method stub

		Student student = studentRepo.findById(id).orElseThrow(NoResultException::new);

		return convert(student);
	}

	private StudentDTO convert(Student student) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper.map(student, StudentDTO.class);
	}

	public List<StudentDTO> getAll() {
		List<Student> studentList = studentRepo.findAll();
		return studentList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

	@Override
	public PageDTO<List<StudentDTO>> searchByCode(SearchDTO searchDTO) {

		

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
		Page<Student> page = studentRepo.searchByCode("%" + searchDTO.getKeyword() + "%", pageRequest);

		PageDTO<List<StudentDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<StudentDTO> studentDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T: List<UserDTO>
		pageDTO.setData(studentDTOs);
		return pageDTO;
	}

}
