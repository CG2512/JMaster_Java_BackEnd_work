package jmaster.io.demo.entity; //Package map voi DB

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Table(name="db_user") //map to table SQL
@Entity //BEAN: new object,Spring quan li
public class User {
	//MAP voi bang
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Auto genererate ID,tang dan
	private int id;
	
	 //bat buoc phai la kieu du lieu Entity
	//Many user to One Department
	//@JoinColumn(name="department_id") //Dung voi Many to One
	@ManyToOne
	private Department department;
	//Trung ten voi column nen ko can phai map
	private int age;
	private String name;
	//luu ten file path
	private String avatarURL;
	//Duy nhat trong db,ap dung voi username
	@Column(unique=true)
	private String username;
	
	private String password;
	
	//home_address trong SQL
	private String homeAddress;
	
	@Temporal(TemporalType.DATE)
	private Date birthdate;
}
