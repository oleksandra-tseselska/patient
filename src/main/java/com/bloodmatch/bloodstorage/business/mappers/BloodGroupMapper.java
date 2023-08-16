package com.bloodmatch.bloodstorage.business.mappers;

import com.bloodmatch.bloodstorage.business.model.BloodGroup;
import com.bloodmatch.bloodstorage.business.repository.model.BloodGroupEntity;
import com.bloodmatch.bloodstorage.business.repository.model.PatientEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Mapper(componentModel = "spring", uses = {
        PatientMapper.class})
public interface BloodGroupMapper {
    BloodGroupEntity bloodGroupToBloodGroupEntity(BloodGroup bloodGroup);

    BloodGroup bloodGroupEntityToBloodGroup(BloodGroupEntity bloodGroupEntity);

    default List<PatientEntity> patientIdsToPatientEntities(List<Long> patientIds) {
        List<PatientEntity> patientEntities = new ArrayList<>();
        if (isNotEmpty(patientIds)) {
            patientIds.forEach(patientId -> patientEntities.add(new PatientEntity(patientId)));
        }
        return patientEntities;
    }

    default List<Long> patientEntitiesToPatientIds(List<PatientEntity> patientEntities) {
        List<Long> patientIds = new ArrayList<>();
        if (isNotEmpty(patientEntities)) {
            patientEntities.forEach(patientEntity -> patientIds.add(patientEntity.getId()));
        }
        return patientIds;
    }
}
