package com.abhi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.abhi.dto.Dashboard;
import com.abhi.entiey.Counsellor;
import com.abhi.service.CounsellorService;
import com.abhi.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	@Autowired
	private CounsellorService counsellorService;

	@Autowired
	private EnquiryService enquiryService;

	@GetMapping("/logout")
	public String logout(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false); // get session
		session.invalidate();
		return "redirect:/login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "registerView";
	}

	@PostMapping("/register")
	public String handleRegister(Counsellor counsellor, Model model) {
		boolean status = counsellorService.saveCounsellor(counsellor);
		if (status) {
			model.addAttribute("smsg", "Counsellor Saved");
		} else {
			model.addAttribute("emsg", "Faild to save");
		}
		return "registerView";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "index";
	}

	@PostMapping("/login")
	public String handleLogin(Counsellor counsellor, HttpServletRequest req, Model model) {
		Counsellor c = counsellorService.getCounsellor(counsellor.getEmail(), counsellor.getPwd());
		if (c == null) {
			model.addAttribute("emsg", "Invaild Credentials");
			return "index";
		} else {

			// set counsellor-id in session
			HttpSession session = req.getSession(true); // always new session
			session.setAttribute("cid", c.getCounsellorId());
            return "redirect:dashboard";
		}
	}

	@GetMapping("/dashboard")
	public String buildDashboard(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");
		Dashboard dbinfo = enquiryService.getDashboardInfo(cid);
		model.addAttribute("dashboard", dbinfo);
		return "dashboard";
	}
}
