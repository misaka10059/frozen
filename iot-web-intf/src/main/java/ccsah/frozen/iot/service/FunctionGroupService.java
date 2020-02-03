package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.FunctionGroupDto;
import ccsah.frozen.iot.domain.dto.FunctionGroupParentDto;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 15:51
 * DESC 功能类别服务
 */
public interface FunctionGroupService {

    FunctionGroupDto addFunctionGroup(String groupName, String parentId);

    FunctionGroupDto deleteFunctionGroupById(String functionGroupId);

    FunctionGroupDto updateFunctionGroup(String functionGroupId, String groupName, String parentId);

    List<FunctionGroupDto> listFunctionGroupByTopNode();

    List<FunctionGroupDto> listFunctionGroupByGroupName(String groupName);

    FunctionGroupDto listFunctionGroupTreeById(String id, int deep);

    FunctionGroupParentDto listFunctionGroupSub(String functionGroupId);

}
