package com.example.goalscheduler.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "PROJECT_INFORMATION")
public class ProjectInformation {
	
	@Id
	@Column(name = "GOAL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int goalId;
	
	@Column(name = "PROJECT_GOAL")
	private String projectGoal;
	@Column(name = "GOAL_DURATION")
	private int goalDuration;


	@OneToMany(mappedBy = "info")
	private List<AvailableTime> available = new ArrayList<>();
	
	public ProjectInformation(String projectGoal, int goalDuration) {
		this.projectGoal = projectGoal;
		this.goalDuration = goalDuration;
	}

	public ProjectInformation() {
	}

	public int getGoalId() {
		return goalId;
	}

	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public String getProjectGoal() {
		return projectGoal;
	}

	public void setProjectGoal(String projectGoal) {
		this.projectGoal = projectGoal;
	}

	public int getGoalDuration() {
		return goalDuration;
	}

	public void setGoalDuration(int goalDuration) {
		this.goalDuration = goalDuration;
	}

	public List<AvailableTime> getAvailable() {
		return available;
	}

	public void setAvailable(List<AvailableTime> available) {
		this.available = available;
	}

	public void addInfoToAvailable(AvailableTime time) {
		this.available.add(time);
		time.setInfo(this);
	}
	
	
}

