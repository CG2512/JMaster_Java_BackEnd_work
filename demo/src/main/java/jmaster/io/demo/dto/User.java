package jmaster.io.demo.dto;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
//ORM framework: Object-Record Table
//JPA-Hibernate
//JDBC-MySQL

@Data
@Table(name="db_user") //map to table SQL
@Entity //BEAN: new object,Spring quan li
public class User {
	//MAP voi bang
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Auto genererate ID,tang dan
	private int id;
	
	//Trung ten voi column nen ko can phai map
	private int age;
	private String name;
	
	//Duy nhat trong db,ap dung voi username
	@Column(unique=true)
	private String username;
	
	private String password;
	
	//home_address trong SQL
	private String homeAddress;
}
