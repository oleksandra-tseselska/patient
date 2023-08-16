package com.bloodmatch.bloodstorage.business.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "Model of blood group data.")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BloodGroup {
    @ApiModelProperty(name = "The unique id of the blood group.",
            value = "when saving a new object, it is itself generated in the database.")
    private Long id;
    @ApiModelProperty(value = "The unique name of blood group.",
            required = true)
    @NotNull(message = "Blood group is mandatory and mustn't be null.")
    private String group;
    @ApiModelProperty(value = "List of patients this this type of blood.")
    private List<Long> patientIds;
}
