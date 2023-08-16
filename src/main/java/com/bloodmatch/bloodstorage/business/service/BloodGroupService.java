package com.bloodmatch.bloodstorage.business.service;

import com.bloodmatch.bloodstorage.business.model.BloodGroup;

import java.util.List;

public interface BloodGroupService {
    List<BloodGroup> findAllBloodGroups();

    BloodGroup findBloodGroupById(Long id);
}
