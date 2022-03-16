package com.example.goalscheduler.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "PROJECT_MEMBERS")
public class ProjectMember {
	
	@Id
	@Column(name = "MEMBER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int memberId;
	
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "NAME")
	private String name;
	@Column(name = "PROJECT_LEADER")
	private boolean projectLeader;
	
	@OneToMany(mappedBy = "member")
	private List<AvailableTime> available = new ArrayList<>();

	public ProjectMember(String username, String password, String name, boolean projectLeader,
			List<AvailableTime> available) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.projectLeader = projectLeader;
		this.available = available;
	}

	public ProjectMember() {
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(boolean projectLeader) {
		this.projectLeader = projectLeader;
	}

	public List<AvailableTime> getAvailable() {
		return available;
	}

	public void setAvailable(List<AvailableTime> available) {
		this.available = available;
	}

	public void addMembersToAvailable(AvailableTime time) {
		this.available.add(time);
		time.setMember(this);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ProjectMembers [memberId=" + memberId + ", username=" + username + ", password=" + password + ", name="
				+ name + ", projectLeader=" + projectLeader + ", available=" + available + "]";
	}
	
}