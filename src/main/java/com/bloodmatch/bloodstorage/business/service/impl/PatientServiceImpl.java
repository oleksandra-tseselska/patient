package com.bloodmatch.bloodstorage.business.service.impl;

import com.bloodmatch.bloodstorage.business.exceptions.ExistInDataBaseException;
import com.bloodmatch.bloodstorage.business.exceptions.NotExistInDBException;
import com.bloodmatch.bloodstorage.business.exceptions.SomethingWentWrongException;
import com.bloodmatch.bloodstorage.business.mappers.PatientMapper;
import com.bloodmatch.bloodstorage.business.model.Patient;
import com.bloodmatch.bloodstorage.business.repository.PatientRepository;
import com.bloodmatch.bloodstorage.business.repository.model.PatientEntity;
import com.bloodmatch.bloodstorage.business.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bloodmatch.bloodstorage.business.specifications.PatientEntitySpecifications.findByBloodGroupId;
import static com.bloodmatch.bloodstorage.business.specifications.PatientEntitySpecifications.findByFirstName;
import static com.bloodmatch.bloodstorage.business.specifications.PatientEntitySpecifications.findByLastName;
import static com.bloodmatch.bloodstorage.business.specifications.PatientEntitySpecifications.findByUniqueNumber;
import static com.bloodmatch.bloodstorage.business.specifications.PatientEntitySpecifications.isPatientUrgency;
import static com.bloodmatch.bloodstorage.business.specifications.PatientEntitySpecifications.presencePatientByHospitalAddress;

@Log4j2
@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;
    private final PatientMapper mapper;

    @Override
    public Patient savePatient(Patient patient) {
        autogenerateUniqueNumber(patient);
        PatientEntity savedPatient = repository.save(mapper.patientToPatientEntity(patient));
        return mapper.patientEntityToPatient(savedPatient);
    }

    @Override
    public Patient etitPatient(Patient updatedPatient, Long id) {
        if (repository.existsById(id)) {
            existInRepositoryThrowEIDBException(updatedPatient);
            updatedPatient.setId(id);
            PatientEntity savedChangesToDB = repository.save(mapper.patientToPatientEntity(updatedPatient));
            log.info("Patient with id {} is updated", id);
            return mapper.patientEntityToPatient(savedChangesToDB);
        }
        log.warn("Patient with id {} is not found", id);
        throw new NotExistInDBException("Patient with id " + id + " is not found");
    }

    @Override
    public Long findPatientBloodIdByUniqueNumber(String uniqueNumber) {
        if (!repository.existsByUniqueNumber(uniqueNumber)) {
            log.warn("Patient with unique number not exists {}", uniqueNumber);
            throw new NotExistInDBException("Patient with unique number: " + uniqueNumber + " not exists");
        }
        Optional<Patient> patientByUniqueNumber = repository.findByUniqueNumber(uniqueNumber)
                .flatMap(patient -> Optional.ofNullable(mapper.patientEntityToPatient(patient)));
        log.info("Patient is found: {}", patientByUniqueNumber);
        if (patientByUniqueNumber.isPresent()) {
            return patientByUniqueNumber.get().getId();
        }
        throw new SomethingWentWrongException("Something went wrong, please check unique number and try ones more");
    }

    @Override
    public List<Patient> findPatientByBloodGroupAndOrUrgencyAndOrHospitalAddress(Long bloodGroupId, Boolean urgent, String hospitalAddress) {
        if (hospitalAddress == null) {
            return repository.findAll((isPatientUrgency(urgent))
                            .and(findByBloodGroupId(bloodGroupId)))
                    .stream().map(mapper::patientEntityToPatient).toList();
        }
        return repository.findAll((isPatientUrgency(urgent))
                        .and(findByBloodGroupId(bloodGroupId))
                        .and(presencePatientByHospitalAddress(hospitalAddress)))
                .stream().map(mapper::patientEntityToPatient).toList();
    }

    @Override
    public List<Patient> findPatientByFirstNameAndLastNameOrUniqueNumber(String firstName, String lastName, String uniqueNumber) {
        if (firstName == null && lastName == null) {
            return repository.findAll(findByUniqueNumber(uniqueNumber))
                    .stream().map(mapper::patientEntityToPatient).toList();
        }
        if (firstName == null && uniqueNumber == null) {
            return repository.findAll((findByLastName(lastName)))
                    .stream().map(mapper::patientEntityToPatient).toList();
        }
        if (lastName == null && uniqueNumber == null) {
            return repository.findAll((findByFirstName(firstName)))
                    .stream().map(mapper::patientEntityToPatient).toList();
        }
        if (firstName == null) {
            return repository.findAll((findByUniqueNumber(uniqueNumber))
                            .and(findByLastName(lastName)))
                    .stream().map(mapper::patientEntityToPatient).toList();
        }
        if (lastName == null) {
            return repository.findAll((findByUniqueNumber(uniqueNumber))
                            .and(findByFirstName(firstName)))
                    .stream().map(mapper::patientEntityToPatient).toList();
        }
        if (uniqueNumber == null) {
            return repository.findAll((findByFirstName(firstName))
                            .and(findByLastName(firstName)))
                    .stream().map(mapper::patientEntityToPatient).toList();
        }
        return repository.findAll((findByUniqueNumber(uniqueNumber))
                        .and(findByFirstName(firstName))
                        .and(findByLastName(lastName)))
                .stream().map(mapper::patientEntityToPatient).toList();
    }

    private void existInRepositoryThrowEIDBException(Patient patient) {
        if (isExistInRepository(patient)) {
            log.error("Patient with same fields already exist");
            throw new ExistInDataBaseException("Patient with same fields already exist");
        }
    }

    private boolean isExistInRepository(Patient patient) {
        return repository.existsByFirstNameAndLastNameAndUrgencyAndPhoneNumber(
                patient.getFirstName(), patient.getLastName(),
                patient.getUrgency(), patient.getPhoneNumber());
    }

    private void autogenerateUniqueNumber(Patient patient) {
        String patientUniqueNumber = UUID.randomUUID().toString().substring(0, 8).toLowerCase();
        while (repository.existsByUniqueNumber(patientUniqueNumber)) {
            autogenerateUniqueNumber(patient);
        }
        patient.setUniqueNumber(patientUniqueNumber);
    }
}
