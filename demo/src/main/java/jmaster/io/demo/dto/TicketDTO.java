package jmaster.io.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jmaster.io.demo.entity.Department;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TicketDTO   {
	
	private Integer id;
	
	@NotBlank(message="{not.blank}")
	private String clientName;
	@NotBlank(message="{not.blank}")
	private String clientPhone;
	
	@NotBlank(message="{not.blank}")
	private String clientComplaint;
	
	private String answer;
	
	private boolean status;
	
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date createdAt;
	
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date processDate;

	private Department department;
}
