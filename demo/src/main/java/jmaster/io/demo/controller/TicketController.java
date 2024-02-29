package jmaster.io.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jmaster.io.demo.dto.DepartmentDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.SearchTicketDTO;
import jmaster.io.demo.dto.TicketDTO;
import jmaster.io.demo.service.DepartmentService;
import jmaster.io.demo.service.TicketService;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/new")
	public String create(Model model) {
		PageDTO<List<DepartmentDTO>> pageDTO
		=departmentService.search(new SearchDTO());
				
		model.addAttribute("departmentList", pageDTO.getData());
		model.addAttribute("ticket", new TicketDTO());
		return "ticket/new-ticket.html";
	}
	
	@PostMapping("/new")
	public String newTicket(@ModelAttribute("ticket") @Valid TicketDTO ticketDTO
			, BindingResult bindingResult
			,Model model)
			throws IllegalStateException, IOException {
		if (bindingResult.hasErrors()) {
			PageDTO<List<DepartmentDTO>> pageDTO
			=departmentService.search(new SearchDTO());
					
			model.addAttribute("departmentList", pageDTO.getData());
			return "ticket/new-ticket.html";
		}
			ticketService.create(ticketDTO);

		return "redirect:/ticket/search";
	}
	
	
	@GetMapping("/search")
	public String search(Model model, @ModelAttribute @Valid SearchTicketDTO searchDTO
			, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			PageDTO<List<DepartmentDTO>> pageDTO
			=departmentService.search(new SearchDTO());
					
			model.addAttribute("departmentList", pageDTO.getData());
			
			return "ticket/tickets.html"; // khi co loi thi tra view(se bi mat du lieu)
		}
		PageDTO<List<DepartmentDTO>> pageDTO
		=departmentService.search(new SearchDTO());
		
		PageDTO<List<TicketDTO>> pageTicket = ticketService.search(searchDTO);

		model.addAttribute("ticketList", pageTicket.getData()); // tra List<User>
		model.addAttribute("totalPage",pageTicket.getTotalPages());
		model.addAttribute("totalElements", pageTicket.getTotalElements());
		model.addAttribute("searchDTO", searchDTO);
		model.addAttribute("departmentList", pageDTO.getData());

		return "ticket/tickets.html";
	}
	
	
	
	@GetMapping("/delete") // ?id=1000
	public String delete(@RequestParam("id") int id) {
		ticketService.delete(id);
		return "redirect:/ticket/search";
	}
	
	@GetMapping("/answer") // ?id=1000
	public String edit(@RequestParam("id") int id, Model model) {
		TicketDTO ticketDTO = ticketService.getById(id);
		model.addAttribute("ticket", ticketDTO); // day ticket qua view
		
		return "ticket/answer.html";
	}
	
	@PostMapping("/answer")
	public String answer(@ModelAttribute("ticket") @Valid TicketDTO ticketDTO
			,BindingResult bindingResult
			,Model model) {

		if (bindingResult.hasErrors()) {
					
			return "ticket/answer.html";
		}
		ticketService.update(ticketDTO);

		return "redirect:/ticket/search";
	}
}
