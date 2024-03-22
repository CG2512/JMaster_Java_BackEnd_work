package jmaster.io.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Bill extends TimeAuditable {
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto genererate ID,tang dan
	private Integer id;
	private String status;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL,orphanRemoval=true)
	private List<BillItem> billItems;
}
