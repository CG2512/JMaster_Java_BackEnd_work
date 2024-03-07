package jmaster.io.demo.dto; //Package dung cho Map Object voi View

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	private List<String> roles;
	private String homeAddress;
	
	@JsonIgnore
	private MultipartFile file;
	//ManyToOne
	//swap to int departmentID later(and make findById method for DepartmentRepo)
	private DepartmentDTO department;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@JsonFormat(pattern="dd/MM/yyyy",timezone="Asia/Ho_Chi_Minh")
	private Date birthdate;
}
