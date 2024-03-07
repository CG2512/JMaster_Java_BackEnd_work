package jmaster.io.demo.dto;

import lombok.Data;


@Data
public class StudentDTO {	
	private UserDTO user; //user_id
	private String studentCode;	
	/* not need yet
	 * @JsonIgnoreProperties("student") 
	 * private List<ScoreDTO> scores;
	 */
}
