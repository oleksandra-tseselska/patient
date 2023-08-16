package com.bloodmatch.bloodstorage.business.repository;

import com.bloodmatch.bloodstorage.business.repository.model.BloodGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodGroupRepository extends JpaRepository<BloodGroupEntity, Long> {
}
