package com.example.goalscheduler.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goalscheduler.entities.AvailableTime;
import com.example.goalscheduler.entities.ProjectInformation;
import com.example.goalscheduler.entities.ProjectMember;
import com.example.goalscheduler.repos.AvailableTimeRepo;
import com.example.goalscheduler.repos.ProjectInformationRepo;
import com.example.goalscheduler.repos.ProjectMemberRepo;
import com.example.goalscheduler.utils.SetDateTime;

@Service
public class GoalSetterService {

	SetDateTime stringToDate;

	@Autowired
	private AvailableTimeRepo availableRepo;
	@Autowired
	private ProjectInformationRepo infoRepo;
	@Autowired
	private ProjectMemberRepo memberRepo;

//////////////////////////// Project Members
	public int verifyMember(String username, String password) {
		ProjectMember foundMember = memberRepo.findByUsername(username);
		if (foundMember == null) {
			return -1;
		} else {
			if (foundMember.getPassword().equals(password)) {
				return foundMember.getMemberId();
			}
			return -1;
		}
	}

	public void registerMember(ProjectMember member) {
		memberRepo.save(member);
	}

	public ProjectMember getMember(int verifiedUserId) {
		ProjectMember member = memberRepo.findById(verifiedUserId);
		return member;
	}

	public List<ProjectMember> getAllMembers() {
		List<ProjectMember> projectMembers = memberRepo.findAll();
		return projectMembers;
	}

	private void deleteMembers() {
		memberRepo.deleteAll();
		memberRepo.flush();
	}

	public int verifyUsername(String username) {
		List<ProjectMember> projectMembers = memberRepo.findAll();
		for (ProjectMember projectMember : projectMembers) {
			if (projectMember.getUsername().equals(username)) {
				return -1;
			}
		}
		return 1;
	}

//////////////////////////// Project Info
	public int verifyProject() {
		List<ProjectInformation> foundInfo = infoRepo.findAll();
		if (foundInfo.size() > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	public ProjectInformation getProjectInfo(int verifiedProjectId) {
		ProjectInformation info = infoRepo.findById(verifiedProjectId);
		return info;
	}

	public List<ProjectInformation> getAllGoals() {
		List<ProjectInformation> projectInfo = infoRepo.findAll();
		return projectInfo;
	}

	public void registerGoal(ProjectInformation proInfo) {
		infoRepo.save(proInfo);
	}

	public ProjectInformation getGoal(int id) {
		return infoRepo.findById(id);
	}

	private void deleteInfo() {
		infoRepo.deleteAll();
		infoRepo.flush();

	}

	public ProjectInformation getGoalId(String projectGoal) {
		ProjectInformation goal = infoRepo.findByProjectGoal(projectGoal);
		return goal;
	}

	public void deleteGoal(int id) {
		infoRepo.deleteById(id);
		infoRepo.flush();
	}

//////////////////////////// Available Time

	public List<AvailableTime> getAllAvailable() {
		List<AvailableTime> available = availableRepo.findAll();
		return available;
	}

	public Date convertStringToDate(String fromString) {
		return SetDateTime.parse(fromString);
	}

	public String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date date = new Date();
		return formatter.format(date);
	}

	public String getMaxDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, 10);
		Date currentDatePlusTen = c.getTime();
		return formatter.format(currentDatePlusTen);

	}

	public void saveAvailableTime(AvailableTime available) {
		availableRepo.save(available);
	}

	private void deleteAvailable() {
		availableRepo.deleteAll();
		availableRepo.flush();
	}

	public void deleteAllGoalAvailableTime(int goalId) {
		List<AvailableTime> availableList = availableRepo.findAll();
		ProjectInformation goal = infoRepo.findById(goalId);

		for (AvailableTime availableTime : availableList) {
			if (availableTime.getInfo().equals(goal)) {
				availableRepo.delete(availableTime);
				availableRepo.flush();
			}
		}
	}

	public void deleteOneAvailableTime(int availableId) {
		availableRepo.delete(availableRepo.findById(availableId));
		availableRepo.flush();
	}

//////////////////////////// Miscellaneous

	public void deleteAll() {
		deleteAvailable();
		deleteMembers();
		deleteInfo();
	}

}
