package jmaster.io.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import jmaster.io.demo.entity.Bill;
import lombok.Data;

@Data

public class BillItemDTO {
	private Integer id;

	//@JsonBackReference
	@JsonIgnore
	private Bill bill;

	private ProductDTO product;
	@Min(0)
	private int quantity;
	@Min(0)
	private double buyPrice;
}
