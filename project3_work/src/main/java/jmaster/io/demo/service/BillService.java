package jmaster.io.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.BillDTO;
import jmaster.io.demo.dto.BillItemDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.entity.Bill;
import jmaster.io.demo.entity.BillItem;
import jmaster.io.demo.entity.User;
import jmaster.io.demo.repository.BillRepo;
import jmaster.io.demo.repository.ProductRepo;
import jmaster.io.demo.repository.UserRepo;

public interface BillService {

	void create(BillDTO billDTO);

	void delete(int id);

	void update(BillDTO billDTO);

	BillDTO getById(int id);

	PageDTO<List<BillDTO>> searchByUsername(SearchDTO searchDTO);

	List<Bill> newBillScan(Date date);
}

@Service
class BillServiceImpl implements BillService {

	@Autowired
	BillRepo billRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Transactional
	@Override
	public void create(BillDTO billDTO) {
		User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);

		Bill bill = new Bill();
		bill.setUser(user);
		bill.setStatus(billDTO.getStatus());

		List<BillItemDTO> billItemsDTO = billDTO.getBillItems();
		List<BillItem> billItems = new ArrayList<>();
		for (BillItemDTO billItemDTO : billItemsDTO) {
			BillItem billItem = new BillItem();
			billItem.setBill(bill);
			billItem.setProduct(
					productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));

			billItem.setBuyPrice(billItemDTO.getBuyPrice());
			billItem.setQuantity(billItemDTO.getQuantity());

			billItems.add(billItem);
		}

		bill.setBillItems(billItems);
		billRepo.save(bill);
		// Display Product/User name for email
		for (BillItemDTO billItemDTO : billItemsDTO) {
			billItemDTO.setProduct(productService.getById(billItemDTO.getProduct().getId()));
		}
		emailService.billEmail(user.getUsername(), user.getEmail(), billItemsDTO);

	}

	@Transactional
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		billRepo.deleteById(id);
	}

	@Transactional
	@Override
	public void update(BillDTO billDTO) {
		// TODO Auto-generated method stub
		Bill bill = billRepo.findById(billDTO.getId()).orElseThrow(NoResultException::new);
		User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);

		bill.setUser(user);
		bill.getBillItems().clear();
		;
		for (BillItemDTO billItemDTO : billDTO.getBillItems()) {
			BillItem billItem = new BillItem();
			billItem.setBill(bill);
			billItem.setProduct(
					productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));

			billItem.setBuyPrice(billItemDTO.getBuyPrice());
			billItem.setQuantity(billItemDTO.getQuantity());

			bill.getBillItems().add(billItem);
		}
		billRepo.save(bill);
	}

	@Override
	public BillDTO getById(int id) {
		// TODO Auto-generated method stub
		Bill bill = billRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(bill);
	}

	@Override
	public PageDTO<List<BillDTO>> searchByUsername(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(10);
		}
		if (searchDTO.getKeyword() == null) {
			searchDTO.setKeyword("");
		}

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize());
		Page<Bill> page = null;
		if (StringUtils.hasText(searchDTO.getKeyword())) {
			page = billRepo.searchByUsername("%" + searchDTO.getKeyword() + "%", pageRequest);
		} else {
			page = billRepo.findAll(pageRequest);
		}
		PageDTO<List<BillDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<BillDTO> billDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());

		pageDTO.setData(billDTOs);
		return pageDTO;
	}

	private BillDTO convert(Bill bill) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper.map(bill, BillDTO.class);
	}

	@Override
	public List<Bill> newBillScan(Date date) {
		List<Bill> bills = billRepo.searchByDate(date);

		//List<BillDTO> billDTOs=bills.stream().map(b -> convert(b)).collect(Collectors.to);
		//ModelMapper can't convert List to List
		return bills;
	}

}
