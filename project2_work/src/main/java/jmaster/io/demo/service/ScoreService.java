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
import jmaster.io.demo.dto.AvgScoreByCourse;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ScoreDTO;
import jmaster.io.demo.dto.SearchScoreDTO;
import jmaster.io.demo.entity.Course;
import jmaster.io.demo.entity.Score;
import jmaster.io.demo.entity.Student;
import jmaster.io.demo.repository.CourseRepo;
import jmaster.io.demo.repository.ScoreRepo;
import jmaster.io.demo.repository.StudentRepo;

public interface ScoreService {
	void create(ScoreDTO scoreDTO);

	void update(ScoreDTO scoreDTO);

	void delete(int id);

	ScoreDTO getById(int id);

	PageDTO<List<ScoreDTO>> search(SearchScoreDTO searchScoreDTO);

	PageDTO<List<AvgScoreByCourse>> avgScoreByCourse();
}

@Service
class ScoreServiceImpl implements ScoreService {

	@Autowired
	ScoreRepo scoreRepo;

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	CourseRepo courseRepo;

	@Override
	@Transactional
	public void create(ScoreDTO scoreDTO) {
		// TODO Auto-generated method stub
		Score score = new ModelMapper().map(scoreDTO, Score.class);
		scoreRepo.save(score);
	}

	@Override
	@Transactional
	public void update(ScoreDTO scoreDTO) {
		Score score = scoreRepo.findById(scoreDTO.getId()).orElseThrow(NoResultException::new);

		score.setScore(scoreDTO.getScore());

		Student student = studentRepo.findById(scoreDTO.getStudent().getUser().getId())
				.orElseThrow(NoResultException::new);
		score.setStudent(student);

		Course course = courseRepo.findById(scoreDTO.getCourse().getId()).orElseThrow(NoResultException::new);
		score.setCourse(course);
		scoreRepo.save(score);

	}

	@Override
	@Transactional
	public void delete(int id) {
		// TODO Auto-generated method stub
		scoreRepo.deleteById(id);
	}

	@Override
	public ScoreDTO getById(int id) {
		// TODO Auto-generated method stub
		Score score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(score);
	}

	@Override
	public PageDTO<List<ScoreDTO>> search(SearchScoreDTO searchScoreDTO) {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by("score").ascending();

		if (StringUtils.hasText(searchScoreDTO.getSortedField())) {
			sortBy = Sort.by(searchScoreDTO.getSortedField()).ascending();
		}

		if (searchScoreDTO.getCurrentPage() == null) {
			searchScoreDTO.setCurrentPage(0);
		}

		if (searchScoreDTO.getSize() == null) {
			searchScoreDTO.setSize(10);
		}

		if (searchScoreDTO.getKeyword() == null) {
			searchScoreDTO.setKeyword("");
		}

		PageRequest pageRequest = PageRequest.of(searchScoreDTO.getCurrentPage(), searchScoreDTO.getSize(), sortBy);
		Page<Score> page = null;

		if (searchScoreDTO.getCourseId() != null) {
			page = scoreRepo.searchByCourse(searchScoreDTO.getCourseId(), pageRequest);
		} else if (searchScoreDTO.getStudentId() != null) {
			page = scoreRepo.searchByStudent(searchScoreDTO.getStudentId(), pageRequest);
		} else {
			page = scoreRepo.findAll(pageRequest);
		}

		PageDTO<List<ScoreDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<ScoreDTO> scoreDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T: List<UserDTO>
		pageDTO.setData(scoreDTOs);
		return pageDTO;
	}

	@Override
	public PageDTO<List<AvgScoreByCourse>> avgScoreByCourse() {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by("avg").ascending();

		PageRequest pageRequest = PageRequest.of(0, 20, sortBy);
		List<AvgScoreByCourse> avgScores = scoreRepo.avgScoreByCourse();

		PageDTO<List<AvgScoreByCourse>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(0);
		pageDTO.setTotalElements(20);

		pageDTO.setData(avgScores);
		return pageDTO;
	}

	private ScoreDTO convert(Score score) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper.map(score, ScoreDTO.class);
	}

}
