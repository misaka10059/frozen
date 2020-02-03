package ccsah.frozen.firecontrol.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/19 11:53
 * DESC
 */
@Getter
@Setter
public class EmployeeDto implements Serializable {

    private String employeeId;

    private String employeeName;

    public EmployeeDto(String employeeId, String employeeName) {
        this.setEmployeeId(employeeId);
        this.setEmployeeName(employeeName);
    }

}
