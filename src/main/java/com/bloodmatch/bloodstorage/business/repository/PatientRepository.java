package com.bloodmatch.bloodstorage.business.repository;

import com.bloodmatch.bloodstorage.business.repository.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, Long>, JpaSpecificationExecutor<PatientEntity> {
    boolean existsByFirstNameAndLastNameAndUrgencyAndPhoneNumber(
            String firstName,
            String lastName,
            Boolean urgent,
            String phoneNumber
    );

    boolean existsByUniqueNumber(String uniqueNumber);

    Optional<PatientEntity> findByUniqueNumber(String uniqueNumber);
}
