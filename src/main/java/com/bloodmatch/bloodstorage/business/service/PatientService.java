package com.bloodmatch.bloodstorage.business.service;

import com.bloodmatch.bloodstorage.business.model.Patient;

import java.util.List;

public interface PatientService {
    Patient savePatient(Patient patient);

    Patient etitPatient(Patient updatedPatient, Long id);

    List<Patient> findPatientByBloodGroupAndOrUrgencyAndOrHospitalAddress(Long bloodGroupId, Boolean urgent, String hospitalAddress);

    List<Patient> findPatientByFirstNameAndLastNameOrUniqueNumber(String firstName, String lastName, String uniqueNumber);

    Long findPatientBloodIdByUniqueNumber(String uniqueNumber);
}
