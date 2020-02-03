package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 16:45
 * DESC
 */
@Getter
@Setter
public class FunctionGroupDto {

    private String id;

    private String groupCode;

    private String parentId;

    private String groupName;

    private List<FunctionGroupDto> functionGroupDtoList;

    public FunctionGroupDto(String id, String groupCode, String parentId, String groupName) {
        this.setId(id);
        this.setGroupCode(groupCode);
        this.setParentId(parentId);
        this.setGroupName(groupName);
    }

    public FunctionGroupDto(String id, String groupCode, String parentId, String groupName, List<FunctionGroupDto> functionGroupDtoList) {
        this.setId(id);
        this.setGroupCode(groupCode);
        this.setParentId(parentId);
        this.setGroupName(groupName);
        this.setFunctionGroupDtoList(functionGroupDtoList);
    }
}
