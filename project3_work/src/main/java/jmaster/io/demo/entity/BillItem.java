package jmaster.io.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class BillItem {
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto genererate ID,tang dan
	private Integer id;

	@ManyToOne
	private Bill bill;
	
	@ManyToOne
	private Product product;
	private int quantity;
	private double buyPrice;
}
