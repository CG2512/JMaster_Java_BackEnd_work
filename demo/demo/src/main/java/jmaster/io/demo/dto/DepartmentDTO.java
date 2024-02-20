package jmaster.io.demo.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jmaster.io.demo.entity.User;
import lombok.Data;

@Data
public class DepartmentDTO {
	
	private int id;
	
	@NotBlank(message="{not.blank}")
	private String name;
	
	private Date createdAt;
	
	private Date updatedAt;

}
