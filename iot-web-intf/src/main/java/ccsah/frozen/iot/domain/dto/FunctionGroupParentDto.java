package ccsah.frozen.iot.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 16:32
 * DESC
 */
@Getter
@Setter
public class FunctionGroupParentDto {

    private String id;

    private String parentId;

    private String groupName;

    private FunctionGroupParentDto groupParentDto;

    public FunctionGroupParentDto(String id,
                                  String parentId,
                                  String groupName) {
        this.setId(id);
        this.setParentId(parentId);
        this.setGroupName(groupName);
    }

    public FunctionGroupParentDto(String id,
                                  String parentId,
                                  String groupName,
                                  FunctionGroupParentDto groupParentDto) {
        this.setId(id);
        this.setParentId(parentId);
        this.setGroupName(groupName);
        this.setGroupParentDto(groupParentDto);
    }
}
