package com.example.goalscheduler.repos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.goalscheduler.entities.ProjectInformation;


@Repository
public interface ProjectInformationRepo extends JpaRepository<ProjectInformation, Integer> {
	
	ProjectInformation save(ProjectInformation proInfo);
	
	ProjectInformation findById(int id);
	
	ProjectInformation findByProjectGoal(String projectGoal);
	
}
