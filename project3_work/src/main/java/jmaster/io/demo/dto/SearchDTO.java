package jmaster.io.demo.dto;

import lombok.Data;

@Data
public class SearchDTO {

	private String keyword;
	
	private Integer currentPage;
	private Integer size;
	private String sortedField;
	
	
	
	
}
