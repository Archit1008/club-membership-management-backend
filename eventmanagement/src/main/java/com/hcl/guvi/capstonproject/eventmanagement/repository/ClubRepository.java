package com.hcl.guvi.capstonproject.eventmanagement.repository;

import com.hcl.guvi.capstonproject.eventmanagement.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.guvi.capstonproject.eventmanagement.entity.ClubEntity;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity,Long>{
    List<ClubEntity> findAllByOwnerName(String ownerName);

}
