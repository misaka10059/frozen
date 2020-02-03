package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 8:47
 * DESC
 */
@Getter
@Setter
public class DepartmentDto {

    private String id;

    private String parentId;

    private String departmentName;

    private List<DepartmentDto> departmentDtoList;

    public DepartmentDto(String id, String parentId, String departmentName) {
        this.setId(id);
        this.setParentId(parentId);
        this.setDepartmentName(departmentName);
    }

    public DepartmentDto(String id, String parentId, String departmentName, List<DepartmentDto> departmentDtoList) {
        this.setId(id);
        this.setParentId(parentId);
        this.setDepartmentName(departmentName);
        this.setDepartmentDtoList(departmentDtoList);
    }
}
