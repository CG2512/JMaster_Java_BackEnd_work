package jmaster.io.emailservice.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data

public class BillDTO {
	private Integer id;
	private String status;
	
	private UserDTO user;
	
	//@JsonManagedReference
	private List<BillItemDTO> billItems;
	
	private Date createdAt;
}
