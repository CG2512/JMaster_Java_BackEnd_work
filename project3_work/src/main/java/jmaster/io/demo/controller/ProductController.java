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
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ProductDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;

	@PostMapping("/")
	public ResponseDTO<Void> newProduct(@RequestBody @Valid ProductDTO productDTO)
			throws IllegalStateException, IOException {
		productService.create(productDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@DeleteMapping("/{id}") // ?id=1000
	public ResponseDTO<Void> delete(@PathVariable("id") int id) {
		productService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> edit(@RequestBody @Valid ProductDTO productDTO)
			throws IllegalStateException, IOException {

		productService.update(productDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/search")
	public ResponseDTO<PageDTO<List<ProductDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {

		PageDTO<List<ProductDTO>> pageProduct = productService.searchByName(searchDTO);

		return ResponseDTO.<PageDTO<List<ProductDTO>>>builder().status(200).data(pageProduct).build();
	}

	@GetMapping("/{id}") // 10
	public ResponseDTO<ProductDTO> get(@PathVariable("id") int id) {
		ProductDTO productDTO = productService.getById(id);
		return ResponseDTO.<ProductDTO>builder().status(200).data(productDTO).build();
	}

}
