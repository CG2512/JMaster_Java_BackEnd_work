package jmaster.io.demo.dto; //Package dung cho Map Object voi View

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

	private int id;

	@NotBlank(message = "{not.blank}")
	private String name;
	private String avatarURL;
	private String username;
	private String password;
	private String email;
	private List<RoleDTO> roles;

	@JsonIgnore
	private MultipartFile file;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
	private Date birthdate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createdAt;
}
