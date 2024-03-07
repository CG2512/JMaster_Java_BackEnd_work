package jmaster.io.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Entity
@Data
public class Student {
	@Id
	private int userId; //user_id
	
	@OneToOne(cascade=CascadeType.ALL,
			fetch=FetchType.EAGER)
	@PrimaryKeyJoinColumn
	@MapsId 
	private User user; //user_id
	private String studentCode;
	
	@OneToMany(mappedBy="student")
	private List<Score> scores;
}
