package jmaster.io.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jmaster.io.demo.dto.AvgScoreByCourse;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.ScoreDTO;
import jmaster.io.demo.dto.SearchScoreDTO;
import jmaster.io.demo.service.ScoreService;

//REST
@RestController
@RequestMapping("/score")
public class ScoreController {

	@Autowired
	ScoreService scoreService;

	@GetMapping("/")
	public ResponseDTO<ScoreDTO> get(@RequestParam("id") int id) {

		return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreService.getById(id)).build();
	}

	@PostMapping("/")
	public ResponseDTO<Void> newScore(@RequestBody @Valid ScoreDTO scoreDTO) {

		scoreService.create(scoreDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();

	}

	@PutMapping("/")
	public ResponseDTO<ScoreDTO> edit(@RequestBody @Valid ScoreDTO scoreDTO) {
		scoreService.update(scoreDTO);

		return ResponseDTO.<ScoreDTO>builder().status(200).msg("ok").build();
	}

	@DeleteMapping("/") // ?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		scoreService.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@PostMapping("/search") // jackson library
	public ResponseDTO<PageDTO<List<ScoreDTO>>> search(@RequestBody SearchScoreDTO searchScoreDTO) {

		PageDTO<List<ScoreDTO>> pageDTO = scoreService.search(searchScoreDTO);

		return ResponseDTO.<PageDTO<List<ScoreDTO>>>builder().status(200).data(pageDTO).build();
	}

	@GetMapping("/avg-score-by-course")
	public ResponseDTO<PageDTO<List<AvgScoreByCourse>>> avgScoreByCourse() {
		return ResponseDTO.<PageDTO<List<AvgScoreByCourse>>>builder().status(200).data(scoreService.avgScoreByCourse()).build();

	}

}
