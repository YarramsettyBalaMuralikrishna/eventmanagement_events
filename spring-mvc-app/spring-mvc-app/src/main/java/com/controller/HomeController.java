package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dto.UserDTO;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String sayHello() {
		return "home";
	}
	
	@PostMapping("/save")
	public ModelAndView processData(@RequestParam("name") String name,
			@RequestParam("gender") String gender, @RequestParam("city") String city
			) {
		
		UserDTO user=new UserDTO(name,gender,city);
		//creating an object of ModelAndView
		ModelAndView mv=new ModelAndView("next");
		mv.addObject("user",user);
		
		return mv;
	}
	
	@PostMapping("/saveold")
	public String processDataOld(@RequestParam("name") String name,
			@RequestParam("gender") String gender, @RequestParam("city") String city,
			Model model) {
		
		UserDTO user=new UserDTO(name,gender,city);
		//add the object to the Model map
		model.addAttribute("user",user);
		
		return "next";
	}

}
