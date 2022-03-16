package com.example.goalscheduler.repos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.goalscheduler.entities.ProjectMember;

@Repository
public interface ProjectMemberRepo extends JpaRepository<ProjectMember, Integer>{

	ProjectMember save(ProjectMember proMember);
	
	ProjectMember findById(int id);	
	
	ProjectMember findByUsername(String username);
	
}
