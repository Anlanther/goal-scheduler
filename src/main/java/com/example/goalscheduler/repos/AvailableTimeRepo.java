package com.example.goalscheduler.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.goalscheduler.entities.AvailableTime;


@Repository
public interface AvailableTimeRepo extends JpaRepository<AvailableTime, Integer>{
	AvailableTime save(AvailableTime time);
	
	AvailableTime findById(int id);
	
} 
