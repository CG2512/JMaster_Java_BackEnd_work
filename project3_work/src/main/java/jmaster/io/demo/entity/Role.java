package jmaster.io.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Role {
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto genererate ID,tang dan
	private Integer id;
	@Column(unique = true)
	private String name;
}
