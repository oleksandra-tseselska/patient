package com.bloodmatch.bloodstorage.business.web.controller;

import com.bloodmatch.bloodstorage.business.model.BloodGroup;
import com.bloodmatch.bloodstorage.business.service.BloodGroupService;
import com.bloodmatch.bloodstorage.swagger.DescriptionVariables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {DescriptionVariables.BLOOD_GROUP})
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blood/group")
public class BloodGroupController {
    private final BloodGroupService service;

    @GetMapping
    @ApiOperation(value = "Finds all blood groups",
            notes = "Returns the entire list of blood groups",
            response = BloodGroup.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded", response = BloodGroup.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    public ResponseEntity<List<BloodGroup>> findAllBloodGroups() {
        log.info("Retrieve list of Blood groups");
        List<BloodGroup> bloodGroups = service.findAllBloodGroups();
        log.debug("Blood groups is found. Size: {}", bloodGroups::size);
        return ResponseEntity.ok(bloodGroups);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find the blood group by id",
            notes = "Provide an id to search specific blood group in database",
            response = BloodGroup.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    public ResponseEntity<BloodGroup> findBloodGroupById(@ApiParam(value = "id of the blood group", required = true)
                                                         @NonNull @PathVariable Long id) {
        log.info("Find Blood group by passing id:{} ", id);
        return ResponseEntity.ok(service.findBloodGroupById(id));
    }
}
