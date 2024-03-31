package jmaster.io.emailservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data

public class BillItemDTO {
	private Integer id;

	//@JsonBackReference
	@JsonIgnore
	private BillDTO bill;

	private ProductDTO product;
	@Min(0)
	private int quantity;
	@Min(0)
	private double buyPrice;
}
