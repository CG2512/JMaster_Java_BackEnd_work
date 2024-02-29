package jmaster.io.demo.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String clientName;
	private String clientPhone;
	
	private String clientComplaint;
	private String answer;
	
	private boolean status;

	@CreatedDate
	@Column(updatable=false)
	private Date createdAt;

	private Date processDate;

	@ManyToOne
	private Department department;
}
