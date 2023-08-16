package com.bloodmatch.bloodstorage.business.mappers;

import com.bloodmatch.bloodstorage.business.model.Patient;
import com.bloodmatch.bloodstorage.business.repository.model.BloodGroupEntity;
import com.bloodmatch.bloodstorage.business.repository.model.HospitalEntity;
import com.bloodmatch.bloodstorage.business.repository.model.PatientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        BloodGroupMapper.class,
        HospitalMapper.class})
public interface PatientMapper {
    PatientEntity patientToPatientEntity(Patient patient);

    Patient patientEntityToPatient(PatientEntity patientEntity);

    default BloodGroupEntity bloodGroupIdToBloodGroupEntity(Long bloodGroupId) {
        if (bloodGroupId == null) {
            return null;
        }
        return new BloodGroupEntity(bloodGroupId);
    }

    default Long bloodGroupEntityToBloodGroupId(BloodGroupEntity bloodGroupEntity) {
        if (bloodGroupEntity == null) {
            return null;
        }
        return bloodGroupEntity.getId();
    }

    default HospitalEntity hospitalIdToHospitalEntity(Long hospitalId) {
        if (hospitalId == null) {
            return null;
        }
        return new HospitalEntity(hospitalId);
    }

    default Long hospitalEntityToHospitalId(HospitalEntity hospitalEntity) {
        if (hospitalEntity == null) {
            return null;
        }
        return hospitalEntity.getId();
    }
}
