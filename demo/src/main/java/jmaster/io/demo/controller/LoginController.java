package jmaster.io.demo.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		//map url vao 1 ham,tra ve ten file view
		return "login.html";
	}
	
	@PostMapping("/login")
	public String login(
			HttpSession session,
			@RequestParam("username") String username,
			@RequestParam("password") String password
			) throws IOException {
		//String username= req.getParameter("username");
		//String password= req.getParameter("password");
		
		if (username.equals("admin") && password.equals("123") ) {
			//login dung
			//SESSION - luu tam data
			
			session.setAttribute("username", username);
			
			//redirect
			return "redirect:/hello";
		
		} else {
			//redirect - yeu cau client goi lai
			return "redirect:/login";
		}
	}
	
}
