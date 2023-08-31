package com.bloodmatch.bloodstorage.business.web.controller;

import com.bloodmatch.bloodstorage.business.model.Patient;
import com.bloodmatch.bloodstorage.business.service.impl.PatientServiceImpl;
import com.bloodmatch.bloodstorage.swagger.DescriptionVariables;
import com.bloodmatch.bloodstorage.swagger.HTMLResponseMessages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {DescriptionVariables.PATIENT})
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientServiceImpl service;

    @ApiOperation(value = "Save new patient", response = Patient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200),
            @ApiResponse(code = 400, message = HTMLResponseMessages.HTTP_400),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)
    })
    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody @Valid Patient patient) {
        Patient savedPatient = service.savePatient(patient);
        log.info("Patient entry saved: {}", savedPatient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit patient", response = Patient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200),
            @ApiResponse(code = 400, message = HTMLResponseMessages.HTTP_400),
            @ApiResponse(code = 404, message = HTMLResponseMessages.HTTP_404),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Patient> editPatientById(@PathVariable Long id, @RequestBody @Valid Patient updatedPatient) {
        Patient editedPatient = service.etitPatient(updatedPatient, id);
        log.info("Patient: {} is updated", editedPatient);
        return new ResponseEntity<>(editedPatient, HttpStatus.OK);
    }

    @ApiOperation(value = "Find patient by providing blood group id and/or patient urgency and/or hospital address",
            notes = "Returns the entry list of patients",
            response = Patient.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200),
            @ApiResponse(code = 400, message = HTMLResponseMessages.HTTP_400),
            @ApiResponse(code = 404, message = HTMLResponseMessages.HTTP_404),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)
    })
    @GetMapping()
    public ResponseEntity<List<Patient>> findPatientByBloodGroupAndOrUrgencyAndOrHospitalAddress(
            @ApiParam(value = "id of a blood group", required = true) @RequestParam Long bloodGroupId,
            @ApiParam(value = "is patient urgent", required = true) @RequestParam Boolean urgent,
            @ApiParam(value = "address of the hospital") @RequestParam(required = false) String hospitalAddress
    ) {
        List<Patient> patients = service.findPatientByBloodGroupAndOrUrgencyAndOrHospitalAddress(bloodGroupId, urgent, hospitalAddress);
        log.info("List of patients: {} found", patients);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @ApiOperation(value = "Find patient by providing first name or last name or unique number",
            notes = "Returns the entry patient",
            response = Patient.class,
            responseContainer = "Object")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HTMLResponseMessages.HTTP_200),
            @ApiResponse(code = 400, message = HTMLResponseMessages.HTTP_400),
            @ApiResponse(code = 404, message = HTMLResponseMessages.HTTP_404),
            @ApiResponse(code = 500, message = HTMLResponseMessages.HTTP_500)
    })
    @GetMapping("/find")
    public ResponseEntity<List<Patient>> findPatientByFirstNameAndLastNameOrUniqueNumber(
            @ApiParam(value = "First name") @RequestParam(required = false) String firstName,
            @ApiParam(value = "Last name") @RequestParam(required = false) String lastName,
            @ApiParam(value = "Unique number", required = true) @RequestParam String uniqueNumber
    ) {
        List<Patient> patients = service.findPatientByFirstNameAndLastNameOrUniqueNumber(firstName, lastName, uniqueNumber);
        log.info("Patient: {} found", patients);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}
