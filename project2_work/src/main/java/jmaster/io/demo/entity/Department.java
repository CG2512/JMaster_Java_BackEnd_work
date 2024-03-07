package jmaster.io.demo.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@OneToMany(mappedBy="department",
			fetch=FetchType.LAZY) //ko bat buoc
			//default is lazy fetch
	private List<User> users;

	
}
