package com.xiepuxin.incident.dto;

import com.xiepuxin.incident.validate.AddGroup;
import com.xiepuxin.incident.validate.EditGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDTO implements Serializable {
//    @NotNull(message = "Id cannot be null", groups = {EditGroup.class})
    private Long id;

    @NotEmpty(message = "Type cannot be empty", groups = {AddGroup.class,EditGroup.class})
    private String type;

    @NotEmpty(message = "Title cannot be empty", groups = {AddGroup.class,EditGroup.class})
    private String title;

    @NotEmpty(message = "Description cannot be empty", groups = {AddGroup.class,EditGroup.class})
    private String description;

    @NotNull(message = "Status cannot be null", groups = {AddGroup.class,EditGroup.class})
    private Integer status;
}
