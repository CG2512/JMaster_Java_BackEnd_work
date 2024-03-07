package jmaster.io.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
public class ScoreDTO  {

	private int id;
	private double score;
	
	@JsonIgnoreProperties("scores")
	private StudentDTO student;

	private CourseDTO course;
	
	@JsonFormat(pattern="dd/MM/YYYY",timezone="Asia/Ho_Chi_Minh")
	private Date createdAt;
	@JsonFormat(pattern="dd/MM/YYYY",timezone="Asia/Ho_Chi_Minh")
	private Date updatedAt;
}
