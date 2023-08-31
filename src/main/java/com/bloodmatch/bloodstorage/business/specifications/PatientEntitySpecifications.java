package com.bloodmatch.bloodstorage.business.specifications;

import com.bloodmatch.bloodstorage.business.repository.model.HospitalEntity;
import com.bloodmatch.bloodstorage.business.repository.model.PatientEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class PatientEntitySpecifications {

    public static Specification<PatientEntity> findByBloodGroupId(Long bloodGroupId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bloodGroupId"), bloodGroupId);
    }

    public static Specification<PatientEntity> findByFirstName(String firstName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
    }

    public static Specification<PatientEntity> findByLastName(String lastName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
    }

    public static Specification<PatientEntity> findByUniqueNumber(String uniqueNumber) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("uniqueNumber"), "%" + uniqueNumber + "%");
    }

    public static Specification<PatientEntity> isPatientUrgency(Boolean urgency) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("urgency"), urgency);
    }

    public static Specification<PatientEntity> presencePatientByHospitalAddress(String address) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<HospitalEntity, PatientEntity> presenceAddress = root.join("hospitalId");
            return criteriaBuilder.like(presenceAddress.get("address"), "%" + address + "%");
        };
    }
}
