package jmaster.io.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data

public class CourseDTO {
	
	
	private int id;
	private String name;
	@JsonFormat(pattern="dd/MM/YYYY",timezone="Asia/Ho_Chi_Minh")
	private Date createdAt;
	@JsonFormat(pattern="dd/MM/YYYY",timezone="Asia/Ho_Chi_Minh")
	private Date updatedAt;
	
	
}
