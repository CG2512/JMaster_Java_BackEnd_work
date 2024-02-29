package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchTicketDTO;
import jmaster.io.demo.dto.TicketDTO;
import jmaster.io.demo.entity.Ticket;
import jmaster.io.demo.repository.TicketRepo;

public interface TicketService {
	void create(TicketDTO ticketDTO);

	void update(TicketDTO ticketDTO);

	void delete(int id);

	TicketDTO getById(int id);

	PageDTO<List<TicketDTO>> search(SearchTicketDTO searchDTO);

}

@Service
class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepo ticketRepo;
	
	@Transactional
	@Override
	public void create(TicketDTO ticketDTO) {
		// TODO Auto-generated method stub
		Ticket ticket = new ModelMapper().map(ticketDTO, Ticket.class);
		ticketRepo.save(ticket);
	}
	
	@Transactional
	@Override
	public void update(TicketDTO ticketDTO) {
		// TODO Auto-generated method stub
		Ticket ticket = ticketRepo.findById(ticketDTO.getId()).orElse(null);
		// neu co thi update thuoc tinh
		if (ticket != null) {
			
			ticket.setAnswer(ticketDTO.getAnswer());
			ticket.setProcessDate(ticketDTO.getProcessDate());
			//need to find way to not set status to true if no answer given
			ticket.setStatus(true);
			
			
			ticketRepo.save(ticket);

		}
	}
	
	@Transactional
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		ticketRepo.deleteById(id);
	}

	@Override
	public TicketDTO getById(int id) {
		// TODO Auto-generated method stub
		Ticket ticket= ticketRepo.findById(id).orElse(null);
		if (ticket != null) {
			return convert(ticket);
		}
		return null;
	}

	@Override
	public PageDTO<List<TicketDTO>> search(SearchTicketDTO searchDTO) {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by("createdAt").ascending();
		// sap xep du lieu trong page theo thu tu thuoc tinh

		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}

		if (searchDTO.getSize() == null) {
			searchDTO.setSize(10);
		}

		if (searchDTO.getKeyword() == null) {
			searchDTO.setKeyword("");
		}

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<Ticket> page = ticketRepo.findAll(pageRequest);
		
		if (searchDTO.getStart() != null && searchDTO.getEnd() != null) {
			page = ticketRepo.searchByDate(searchDTO.getStart(), searchDTO.getEnd(), pageRequest);
		}
		else if (searchDTO.getStart() != null 
				&& searchDTO.getEnd() != null
				&& StringUtils.hasText(searchDTO.getKeyword())) {
			page = ticketRepo.searchByNameAndDate(searchDTO.getKeyword(),searchDTO.getStart(), searchDTO.getEnd(), pageRequest);
		}
		else if (searchDTO.getStart() != null 
				&& searchDTO.getEnd() == null
				&& StringUtils.hasText(searchDTO.getKeyword())) {
			page = ticketRepo.searchByNameAndStart(searchDTO.getKeyword(),searchDTO.getStart(), pageRequest);
		}
		else if (searchDTO.getStart() == null 
				&& searchDTO.getEnd() != null
				&& StringUtils.hasText(searchDTO.getKeyword())) {
			page = ticketRepo.searchByNameAndEnd(searchDTO.getKeyword(), searchDTO.getEnd(), pageRequest);
		}
		else if(searchDTO.getStart() != null && searchDTO.getEnd() == null) {
			page = ticketRepo.searchByStart(searchDTO.getStart(), pageRequest);
		}
		else if(searchDTO.getStart() == null && searchDTO.getEnd() != null) {
			page = ticketRepo.searchByEnd(searchDTO.getEnd(), pageRequest);
		}
		else if (searchDTO.getDepartmentId() != null) {
			page = ticketRepo.searchByDepartmentId(searchDTO.getDepartmentId(), pageRequest);
		}
		
		else if (StringUtils.hasText(searchDTO.getKeyword())) {
			page = ticketRepo.searchByName(searchDTO.getKeyword(), pageRequest);
		}
		else if (searchDTO.getTicketId() != null) { 
			page =  ticketRepo.findById(searchDTO.getTicketId(), pageRequest);
			}
		 

		PageDTO<List<TicketDTO>> pageDTO = new PageDTO();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<TicketDTO> TicketDTOs = page.get().map(t -> convert(t)).collect(Collectors.toList());
		// T: List<UserDTO>
		pageDTO.setData(TicketDTOs);
		return pageDTO;
	}

	private TicketDTO convert(Ticket ticket) {

		return new ModelMapper().map(ticket, TicketDTO.class);
	}

}