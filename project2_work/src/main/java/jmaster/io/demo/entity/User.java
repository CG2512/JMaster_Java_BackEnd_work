package jmaster.io.demo.entity; //Package map voi DB

import java.util.Date;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Table(name="db_user") //map to table SQL
@Entity //BEAN: new object,Spring quan li
public class User extends TimeAuditable {
	//MAP voi bang
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Auto genererate ID,tang dan
	private int id;
	
	//ko phai tao Entity rieng
	//tu tao bang de map vao
	//CollectionTable ap dung neu table co <= 2 columns (ko tinh them id)
	@ElementCollection
	@CollectionTable(name="user_role",
	joinColumns = @JoinColumn(name="user_id"))//foreign key
	
	@Column(name="role")
	private List<String> roles;
	
	private int age;
	private String name;
	
	private String avatarURL;
	
	@Column(unique=true)
	private String username;
	
	private String password;
	
	//home_address trong SQL
	private String homeAddress;
	
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@ManyToOne  
	private Department department;
	
	private String email;
}
