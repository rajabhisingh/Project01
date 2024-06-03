package com.abhi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abhi.entiey.Enquiry;
import com.abhi.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnuiryController {

	@Autowired
	private EnquiryService enquiryService;

	// add enquiry
	@GetMapping("/enquiry")
	public String addEnquiry(Enquiry enq, Model model) {
		model.addAttribute("enq", new Enquiry());
		return "addEnq";
	}

	// save enquiry
	@PostMapping("/enquiry")
	public String saveEnquiry(Enquiry enq, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");
		boolean status = enquiryService.addEnquiry(enq, cid);
		if (status) {
			model.addAttribute("emsg", "Enquiry Saved");
		} else {
			model.addAttribute("smsg", "Enquiry not saved");
		}
		model.addAttribute("enq", new Enquiry());
		return "addEnq";
	}

	// View enquiries
	@GetMapping("/enquiries")
	public String getEnquries(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");

		List<Enquiry> list = enquiryService.getEnquiries(new Enquiry(), cid);
		model.addAttribute("enqs", list);

		model.addAttribute("enq", new Enquiry());

		return "viewEnquiries";
	}

	// filter enqs
	@PostMapping("/filter-enqs")
	public String filterEnqs(@ModelAttribute("enq") Enquiry enq, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");
		List<Enquiry> list = enquiryService.getEnquiries(enq, cid);
		model.addAttribute("enqs", list);

		return "viewEnquiries";
	}

	// Edit & Update enq
	@GetMapping("/Edit")
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model) {
		Enquiry enquiry = enquiryService.getEnquiry(enqId);
		model.addAttribute("enq", enquiry);
		return "addEnq";
	}

}
