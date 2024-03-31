package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ProductDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.entity.Category;
import jmaster.io.demo.entity.Product;
import jmaster.io.demo.repository.CategoryRepo;
import jmaster.io.demo.repository.ProductRepo;

public interface ProductService {
	void create(ProductDTO productDTO);

	void delete(int id);

	void update(ProductDTO productDTO);

	ProductDTO getById(int id);

	PageDTO<List<ProductDTO>> searchByName(SearchDTO searchDTO);
}

@Service
class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepo productRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Transactional
	@Override
	public void create(ProductDTO productDTO) {
		Category category = categoryRepo.findById(productDTO.getCategory().getId()).orElseThrow(NoResultException::new);

		Product product = new ModelMapper().map(productDTO, Product.class);
		product.setCategory(category);

		productRepo.save(product);

	}

	@Transactional
	@Override
	public void delete(int id) {
		productRepo.deleteById(id);

	}

	@Transactional
	@Override
	public void update(ProductDTO productDTO) {
		Product product = productRepo.findById(productDTO.getId()).orElseThrow(NoResultException::new);
		Category category = categoryRepo.findById(productDTO.getCategory().getId()).orElseThrow(NoResultException::new);

		product.setCategory(category);

		productRepo.save(product);
	}

	@Override
	public PageDTO<List<ProductDTO>> searchByName(SearchDTO searchDTO) {
		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(20);
		}
		if (searchDTO.getKeyword() == null) {
			searchDTO.setKeyword("");
		}

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize());
		Page<Product> page = null;
		if (StringUtils.hasText(searchDTO.getKeyword())) {
			page = productRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		} else {
			page = productRepo.findAll(pageRequest);
		}
		PageDTO<List<ProductDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<ProductDTO> productDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T: List<UserDTO>
		pageDTO.setData(productDTOs);
		return pageDTO;
	}

	@Override
	public ProductDTO getById(int id) {
		Product product = productRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(product);
	}

	private ProductDTO convert(Product product) {
		return new ModelMapper().map(product, ProductDTO.class);
	}

}
