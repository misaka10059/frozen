package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 16:46
 * DESC
 */
@Getter
@Setter
public class DepartmentParentDto {

    private String id;

    private String parentId;

    private String departmentName;

    private DepartmentParentDto departmentParentDto;

    public DepartmentParentDto(String id,
                               String parentId,
                               String departmentName) {
        this.setId(id);
        this.setParentId(parentId);
        this.setDepartmentName(departmentName);
    }

    public DepartmentParentDto(String id,
                               String parentId,
                               String departmentName,
                               DepartmentParentDto departmentParentDto) {
        this.setId(id);
        this.setParentId(parentId);
        this.setDepartmentName(departmentName);
        this.setDepartmentParentDto(departmentParentDto);
    }
}
