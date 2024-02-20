package jmaster.io.demo.dto; //Package dung cho Map Object voi View

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
	
	private int id;
	@Min(value=0,message= "{age.min.msg}")
	private int age;
	@NotBlank(message="{not.blank}")
	private String name;
	//luu ten file path
	private String avatarURL;
	//Duy nhat trong db,ap dung voi username
	private String username;
	private String password;
	private String homeAddress;
	
	private MultipartFile file;
	//ManyToOne
	//swap to int departmentID later(and make findById method for DepartmentRepo)
	private DepartmentDTO departmentDTO;
}
