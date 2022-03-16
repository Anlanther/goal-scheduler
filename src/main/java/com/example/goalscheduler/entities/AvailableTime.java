package com.example.goalscheduler.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "AVAILABLE_TIME")
public class AvailableTime {
	
	@Id
	@Column(name = "AVAILABLE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int availableId;
	
	@Column(name = "AVAILABLE_FROM")
	private Date availableFrom;
	@Column(name = "AVAILABLE_TO")
	private Date availableTo;

	@ManyToOne
	@JoinColumn(name = "GOAL_ID")
	private ProjectInformation info;
	
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private ProjectMember member;

	public AvailableTime(Date availableFrom, Date availableTo) {
		this.availableFrom = availableFrom;
		this.availableTo = availableTo;
	}

	public AvailableTime() {
	}

	public Date getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(Date availableFrom) {
		this.availableFrom = availableFrom;
	}

	public Date getAvailableTo() {
		return availableTo;
	}

	public void setAvailableTo(Date availableTo) {
		this.availableTo = availableTo;
	}

	public ProjectInformation getInfo() {
		return info;
	}

	public void setInfo(ProjectInformation info) {
		this.info = info;
	}
	
	public int getInfoId() {
		return info.getGoalId();
	}

	public ProjectMember getMember() {
		return member;
	}

	public void setMember(ProjectMember member) {
		this.member = member;
	}
	public int getMemberId() {
		return member.getMemberId();
	}

	public void setMemberAndInfo(ProjectInformation info, ProjectMember member) {
		setMember(member);
		setInfo(info);
	}

	public int getAvailableId() {
		return availableId;
	}

	public void setAvailableId(int availableId) {
		this.availableId = availableId;
	}

	@Override
	public String toString() {
		return availableFrom + "-" + availableTo + "\n";
	}

}
