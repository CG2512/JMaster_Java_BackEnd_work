package jmaster.io.demo.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
//tim annotation @CreatedDate va tu generate thoi gian
public class Department{
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(unique=true)
	private String name;
	
	@CreatedDate //auto gen new date
	@Column(updatable=false)
	private Date createdAt;
	
	@LastModifiedDate
	private Date updatedAt;
	
	@OneToMany(mappedBy="department") //ko bat buoc
	//One department to many users
	//MappedBy la ten bien ManyToOne entity users
	private List<User> users;

	

	/*
	 * public Object orElseThrow(Object object) { // TODO Auto-generated method stub
	 * return object; }
	 */



	/*
	 * public Department orElse(Object object) { // TODO Auto-generated method stub
	 * return null; }
	 */

	

	
}
