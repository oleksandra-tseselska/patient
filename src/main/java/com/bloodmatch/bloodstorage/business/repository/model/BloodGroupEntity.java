package com.bloodmatch.bloodstorage.business.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blood_group")
public class BloodGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blood_group_id")
    private Long id;
    @Column(name = "blood_group")
    private String group;
    @OneToMany(mappedBy = "bloodGroupId", fetch = FetchType.LAZY)
    private List<PatientEntity> patientIds;

    public BloodGroupEntity(Long id) {
        this.id = id;
    }
}
