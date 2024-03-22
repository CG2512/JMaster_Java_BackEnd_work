package jmaster.io.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Color {
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto genererate ID,tang dan
	private Integer id;
	private String name;
}
