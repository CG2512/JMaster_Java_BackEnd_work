package jmaster.io.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Score extends TimeAuditable {
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	private double score;
	
	@ManyToOne
	private Course course;
	
	@ManyToOne
	private Student student;
}
