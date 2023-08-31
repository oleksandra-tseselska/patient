package com.bloodmatch.bloodstorage.business.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(description = "Model of patient data.")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class Patient {
    @ApiModelProperty(name = "The unique id of the hospital.",
            value = "when saving a new object, it is itself generated in the database.")
    Long id;
    @ApiModelProperty(value = "First name of the hospital patient")
    @NotBlank(message = "First name is mandatory")
    String firstName;
    @ApiModelProperty(value = "Last name of the hospital patient")
    @NotBlank(message = "Last name is mandatory")
    String lastName;
    @ApiModelProperty(value = "Boolean value of urgency of the hospital patient")
    @NotNull(message = "Urgency is mandatory")
    Boolean urgency;
    @ApiModelProperty(value = "Phone number of hospital patient")
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d{8,15}", message = "Phone numbers must contain from 8 to 15 digits.")
    String phoneNumber;
    @ApiModelProperty(notes = "The unique number of patient")
    String uniqueNumber;
    @ApiModelProperty(value = "Blood group of the patient")
    @NotNull
    private Long bloodGroupId;
    @ApiModelProperty(value = "Hospital where a patient located")
    private Long hospitalId;
}
