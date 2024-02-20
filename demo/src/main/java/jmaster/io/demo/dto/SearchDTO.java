package jmaster.io.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class SearchDTO {
//	@NotBlank(message="{not.blank}")
//	@Size(min = 1,max = 20,message = "{size.msg}")
	private String keyword;
	
	private Integer currentPage;
	private Integer size;
	private String sortedField;
	
	
	
	
}
