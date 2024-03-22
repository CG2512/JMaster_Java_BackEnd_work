package jmaster.io.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jmaster.io.demo.dto.BillDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillController {
	@Autowired
	BillService billService;

	@PostMapping("/")
	public ResponseDTO<Void> newBill(@RequestBody @Valid BillDTO billDTO) throws IllegalStateException, IOException {
		billService.create(billDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@DeleteMapping("/{id}") // ?id=1000
	public ResponseDTO<Void> delete(@PathVariable("id") int id) {
		billService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> edit(@RequestBody @Valid BillDTO billDTO) throws IllegalStateException, IOException {

		billService.update(billDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/search")
	public ResponseDTO<PageDTO<List<BillDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {

		PageDTO<List<BillDTO>> pageRole = billService.searchByUsername(searchDTO);

		return ResponseDTO.<PageDTO<List<BillDTO>>>builder().status(200).data(pageRole).build();
	}

	@GetMapping("/{id}") // 10
	public ResponseDTO<BillDTO> get(@PathVariable("id") int id) {
		BillDTO billDTO = billService.getById(id);
		return ResponseDTO.<BillDTO>builder().status(200).data(billDTO).build();
	}

}
