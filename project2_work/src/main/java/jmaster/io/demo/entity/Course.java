package jmaster.io.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Course extends TimeAuditable {
	
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	private String name;
	
	
}
