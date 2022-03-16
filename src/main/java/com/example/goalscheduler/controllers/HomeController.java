package com.example.goalscheduler.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.goalscheduler.entities.AvailableTime;
import com.example.goalscheduler.entities.ProjectInformation;
import com.example.goalscheduler.entities.ProjectMember;
import com.example.goalscheduler.services.GoalSetterService;

@Controller
public class HomeController {

	@Autowired
	private GoalSetterService gsService;

//////////////////////////// Index

	@GetMapping("/")
	public String goToIndexPage() {
		return "index";
	}

	@PostMapping("login")
	public String getLoginSuccessPage(@RequestParam String username, @RequestParam String password, HttpSession session,
			Model model) {
		int verifiedMemberId = gsService.verifyMember(username, password);
		int verifiedProjectId = gsService.verifyProject();

		if (verifiedMemberId >= 0 && verifiedProjectId > 0) {
			session.setAttribute("id", verifiedMemberId);
			List<ProjectMember> allMembers = gsService.getAllMembers();
			model.addAttribute("allMembers", allMembers);
			List<AvailableTime> allAvailable = gsService.getAllAvailable();
			model.addAttribute("allAvailable", allAvailable);
			List<ProjectInformation> allGoals = gsService.getAllGoals();
			model.addAttribute("allGoals", allGoals);
			return "projectHome";
		} else if (verifiedMemberId >= 0 && verifiedProjectId < 0) {
			ProjectMember member = gsService.getMember(verifiedMemberId);
			session.setAttribute("id", verifiedMemberId);
			if (member.isProjectLeader()) {
				model.addAttribute("info", new ProjectInformation());
				List<ProjectInformation> allGoals = gsService.getAllGoals();
				model.addAttribute("allGoals", allGoals);
				return "projectCreator";
			} else {
				model.addAttribute("noProjectExists", 1);
				return "errorNoProject";
			}
		} else {
			model.addAttribute("invalidFlag", 1);
			return goToIndexPage();
		}
	}

//////////////////////////// Logout

	@PostMapping("logout")
	public String getLogoutPage(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

//////////////////////////// Register

	@GetMapping("register")
	public String createUserInstance(Model model) {
		model.addAttribute("user", new ProjectMember());
		return "register";
	}

	@PostMapping("/register")
	public String registerMember(@RequestParam String username, ProjectMember member, Model model) {
		int verifiedUsername = gsService.verifyUsername(username);
		if (verifiedUsername > 0) {
			gsService.registerMember(member);
			return "index";
		} else {
			model.addAttribute("notUniqueUsername", 1);
			return createUserInstance(model);
		}

	}

//////////////////////////// Project Creator

	@GetMapping("projectCreator")
	public String getProjectCreator(Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		model.addAttribute("info", new ProjectInformation());
		List<ProjectInformation> allGoals = gsService.getAllGoals();
		model.addAttribute("allGoals", allGoals);
		if (allGoals.size() >= 1) {
			model.addAttribute("projectsExist", 1);
		} else {
			model.addAttribute("projectsExist", 0);
		}
		return "projectCreator";
	}

	@PostMapping("projectCreatorAdd")
	public String registerGoal(ProjectInformation proInfo, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		gsService.registerGoal(proInfo);
		model.addAttribute("info", new ProjectInformation());
		List<ProjectInformation> allGoals = gsService.getAllGoals();
		model.addAttribute("allGoals", allGoals);
		if (allGoals.size() >= 1) {
			model.addAttribute("projectsExist", 1);
		} else {
			model.addAttribute("projectsExist", 0);
		}
		return "redirect:/projectCreator";
	}

	@PostMapping("projectCreator")
	public String deleteGoal(@RequestParam(value = "button") int goalId, ProjectInformation proInfo, Model model,
			HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		gsService.deleteGoal(goalId);
		List<ProjectInformation> allGoals = gsService.getAllGoals();
		model.addAttribute("allGoals", allGoals);
		if (allGoals.size() >= 1) {
			model.addAttribute("projectsExist", 1);
		} else {
			model.addAttribute("projectsExist", 0);
		}
		return "redirect:/projectCreator";
	}

//////////////////////////// Select Goal

	@GetMapping("selectGoal")
	public String navToAvailableTimes(Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		model.addAttribute("info", new ProjectInformation());
		List<ProjectInformation> allGoals = gsService.getAllGoals();
		model.addAttribute("allGoals", allGoals);
		if (allGoals.size() >= 1) {
			model.addAttribute("projectsExist", 1);
		}
		return "selectGoal";
	}

//////////////////////////// Available Times

	@GetMapping("assignAvailableTimes/{id}")
	public String setUpAvailableTimes(@PathVariable("id") int id, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/index";
		}
		model.addAttribute("id", id);
		model.addAttribute("currentDate", gsService.getCurrentDate());
		model.addAttribute("currentDatePlusTenYears", gsService.getMaxDate());
		model.addAttribute("available", new AvailableTime());
		List<ProjectInformation> allGoals = gsService.getAllGoals();
		model.addAttribute("allGoals", allGoals);
		if (allGoals.size() >= 1) {
			model.addAttribute("projectsExist", 1);
		}
		return "availableTimes";
	}

	@PostMapping("assignAvailableTimes/{id}")
	public String setUpAvailableTimes(@PathVariable("id") int id, @RequestParam String availableFrom,
			@RequestParam String availableTo, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/index";
		}
		Date fromDate = gsService.convertStringToDate(availableFrom);
		Date toDate = gsService.convertStringToDate(availableTo);
		AvailableTime availableTime = new AvailableTime(fromDate, toDate);
		availableTime.setInfo(gsService.getGoal(id));
		availableTime.setMember(gsService.getMember((int) session.getAttribute("id")));
		gsService.saveAvailableTime(availableTime);
		return setUpAvailableTimes(id, model, session);
	}

	@PostMapping("deleteAllAvailableTimes")
	public String deleteAllAvailable(@RequestParam(value = "button") int goalId, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		gsService.deleteAllGoalAvailableTime(goalId);
		model.addAttribute("currentDate", gsService.getCurrentDate());
		model.addAttribute("currentDatePlusTenYears", gsService.getMaxDate());
		model.addAttribute("available", new AvailableTime());
		List<ProjectInformation> allGoals = gsService.getAllGoals();
		model.addAttribute("allGoals", allGoals);
		if (allGoals.size() >= 1) {
			model.addAttribute("projectsExist", 1);
		} else {
			model.addAttribute("projectsExist", 0);
		}
		return "availableTimes";
	}

	@PostMapping("deleteAvailableTime")
	public String deleteOneAvailable(@RequestParam(value = "button") int availableId, Model model,
			HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		gsService.deleteOneAvailableTime(availableId);
		model.addAttribute("currentDate", gsService.getCurrentDate());
		model.addAttribute("currentDatePlusTenYears", gsService.getMaxDate());
		model.addAttribute("available", new AvailableTime());
		List<ProjectInformation> allGoals = gsService.getAllGoals();
		model.addAttribute("allGoals", allGoals);
		if (allGoals.size() >= 1) {
			model.addAttribute("projectsExist", 1);
		} else {
			model.addAttribute("projectsExist", 0);
		}
		return "availableTimes";
	}

//////////////////////////// Project Home Summary

	@GetMapping("projectHome")
	public String getGoalSetterSummary(HttpSession session, Model model) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		List<ProjectMember> allMembers = gsService.getAllMembers();
		model.addAttribute("allMembers", allMembers);
		List<AvailableTime> allAvailable = gsService.getAllAvailable();
		model.addAttribute("allAvailable", allAvailable);
		List<ProjectInformation> allGoals = gsService.getAllGoals();
		model.addAttribute("allGoals", allGoals);
		return "projectHome";

	}

//////////////////////////// Miscellaneous

	@PostMapping("delete")
	public String deleteAll(HttpSession session) {
		gsService.deleteAll();
		session.invalidate();
		return "redirect:/";
	}
}
