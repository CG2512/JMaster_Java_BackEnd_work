package jmaster.io.demo.entity; //Package map voi DB

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Table(name = "db_user") 
@Entity 
public class User extends TimeAuditable {
	// MAP voi bang
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto genererate ID,tang dan
	private int id;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="user_role",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="role_id"))
	private List<Role> roles;
	
	private String name;
	@Column(unique = true)
	private String username;
	private String password;
	private String email;
	private String avatarURL;
	@Temporal(TemporalType.DATE)
	private Date birthdate;
}
