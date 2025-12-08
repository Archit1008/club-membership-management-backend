package com.hcl.guvi.capstonproject.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.guvi.capstonproject.eventmanagement.entity.ClubEntity;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity,Long>{

}
