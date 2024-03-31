package jmaster.io.emailservice.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDTO {
	private Integer id;

	@NotBlank
	private String name;
	private String image;
	private String description;
	@Min(0)
	private double price;

	private CategoryDTO category;
	
	@JsonIgnore
	private MultipartFile file;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern="dd/MM/yyyy",timezone="Asia/Ho_Chi_Minh")
	private Date createdAt;
}
