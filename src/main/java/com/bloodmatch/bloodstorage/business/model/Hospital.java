package com.bloodmatch.bloodstorage.business.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@ApiModel(description = "Model of hospital data.")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class Hospital {
    @ApiModelProperty(name = "The unique id of the hospital.",
            value = "when saving a new object, it is itself generated in the database.")
    private Long id;
    @ApiModelProperty(value = "The unique name of hospital.",
            required = true)
    @NotNull(message = "Hospital name is mandatory and mustn't be null.")
    private String name;
    @ApiModelProperty(value = "The unique email of hospital")
    @NotBlank(message = "Email is mandatory and can't be blank")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE
            , message = "Example of valid email \"name@gmail.com\"")
    private String email;
    @ApiModelProperty(value = "The unique address of hospital")
    @NotBlank(message = "Hospital address is mandatory and can't be blank")
    private String address;
    @ApiModelProperty(value = "List of hospital patients.")
    private List<Long> patientIds;
}
