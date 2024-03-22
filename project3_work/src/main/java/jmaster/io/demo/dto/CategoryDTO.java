package jmaster.io.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {

	private int id;
	
	@NotBlank
	@Size(min = 0, max = 20)
	private String name;
}
