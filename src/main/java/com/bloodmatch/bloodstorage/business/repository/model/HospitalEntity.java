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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "hospital")
public class HospitalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    Long id;

    @Column(name = "hospital_name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "address")
    String address;

    @OneToMany(mappedBy = "hospitalId", fetch = FetchType.LAZY)
    private List<PatientEntity> patientIds;

    public HospitalEntity(Long id) {
        this.id = id;
    }
}
