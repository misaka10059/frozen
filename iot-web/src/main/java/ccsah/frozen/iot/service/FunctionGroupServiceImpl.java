package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.dao.FunctionGroupDao;
import ccsah.frozen.iot.domain.dto.FunctionGroupDto;
import ccsah.frozen.iot.domain.dto.FunctionGroupParentDto;
import ccsah.frozen.iot.domain.entity.FunctionGroup;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 15:51
 * DESC 功能类别服务
 */
@Service
public class FunctionGroupServiceImpl implements FunctionGroupService {

    @Autowired
    private FunctionGroupDao functionGroupDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/5 15:56
     * DESC 添加FunctionGroup
     */
    @Override
    @Transactional
    public FunctionGroupDto addFunctionGroup(String groupName, String parentId) {
        parentId = getParentId(parentId);
        if (getFunctionGroupByGroupNameAndParentId(groupName, parentId) != null) {
            throw new ServiceException(590, ExceptionCode.FUNCTION_GROUP590);
        }
        FunctionGroup functionGroup = FunctionGroup.create(getFunctionGroupCode(), groupName, parentId);
        return getFunctionGroupDto(functionGroup);
    }

    /**
     * DATE 2019/12/11 16:59
     * DESC 删除FunctionGroup
     */
    @Override
    @Transactional
    public FunctionGroupDto deleteFunctionGroupById(String functionGroupId) {
        FunctionGroup functionGroup = baseService.getFunctionGroupById(functionGroupId);
        functionGroup.deleteLogical();
        List<FunctionGroup> functionGroupList = getFunctionGroupListByParentId(functionGroupId);
        if (functionGroupList.isEmpty()) {
            return getFunctionGroupDto(functionGroup);
        }
        List<FunctionGroupDto> functionGroupDtoList = functionGroupList.stream()
                .map(f -> deleteFunctionGroupById(f.getId()))
                .collect(Collectors.toList());
        return new FunctionGroupDto(
                functionGroup.getId(),
                functionGroup.getGroupCode(),
                functionGroup.getParentId(),
                functionGroup.getGroupName(),
                functionGroupDtoList);
    }

    /**
     * DATE 2019/12/10 16:25
     * DESC 更新FunctionGroup
     */
    @Override
    @Transactional
    public FunctionGroupDto updateFunctionGroup(String functionGroupId,
                                                String groupName,
                                                String parentId) {
        FunctionGroup functionGroup = baseService.getFunctionGroupById(functionGroupId);
        functionGroup.setGroupName(groupName);
        functionGroup.setParentId(getParentId(parentId));
        return getFunctionGroupDto(functionGroup);
    }

    /**
     * DATE 2019/12/10 16:29
     * DESC 获取顶层节点拥有的节点列表
     */
    @Override
    public List<FunctionGroupDto> listFunctionGroupByTopNode() {
        return getFunctionGroupDto(getFunctionGroupListByParentId(BaseString.baseId));
    }

    /**
     * DATE 2019/12/10 16:35
     * DESC 通过groupName模糊查询FunctionGroup
     */
    @Override
    public List<FunctionGroupDto> listFunctionGroupByGroupName(String groupName) {
        List<FunctionGroup> functionGroupList = getFunctionGroupListByGroupNameLike(groupName);
        return getFunctionGroupDto(functionGroupList);
    }

    /**
     * DATE 2019/12/9 14:06
     * DESC 获取指定FunctionGroup及下属所有FunctionGroup记录，并使用deep指定展开层级
     */
    @Override
    public FunctionGroupDto listFunctionGroupTreeById(String id, int deep) {
        FunctionGroup functionGroup = baseService.getFunctionGroupById(id);
        List<FunctionGroup> functionGroupList = getFunctionGroupListByParentId(id);
        if (deep <= 1 || functionGroupList.isEmpty()) {
            return getFunctionGroupDto(functionGroup);
        }
        List<FunctionGroupDto> functionGroupDtoList = functionGroupList.stream()
                .map(f -> listFunctionGroupTreeById(f.getId(), deep - 1))
                .collect(Collectors.toList());
        FunctionGroupDto functionGroupDto = getFunctionGroupDto(functionGroup);
        functionGroupDto.setFunctionGroupDtoList(functionGroupDtoList);
        return functionGroupDto;
    }

    /**
     * DATE 2019/12/27 16:42
     * DESC 根据当前节点查询当前节点至顶层节点之间的各个节点
     */
    private FunctionGroupParentDto listFunctionGroupParent(String functionGroupId) {
        FunctionGroup functionGroup = baseService.getFunctionGroupById(functionGroupId);
        if (functionGroup.getParentId().equals(BaseString.baseId)) {
            return getFunctionGroupParentDto(functionGroup);
        }
        FunctionGroupParentDto newDto = listFunctionGroupParent(functionGroup.getParentId());
        return new FunctionGroupParentDto(
                functionGroup.getId(),
                functionGroup.getParentId(),
                functionGroup.getGroupName(),
                newDto);
    }

    /**
     * DATE 2019/12/27 16:45
     * DESC 反转查询listFunctionGroupParent的节点列表，使层级结构为由大到小
     */
    @Override
    public FunctionGroupParentDto listFunctionGroupSub(String functionGroupId) {
        FunctionGroupParentDto functionGroupParentDto = listFunctionGroupParent(functionGroupId);
        FunctionGroupParentDto prev = null;
        FunctionGroupParentDto now = functionGroupParentDto;
        while (now != null) {
            FunctionGroupParentDto next = now.getGroupParentDto();
            now.setGroupParentDto(prev);
            prev = now;
            now = next;
        }
        return prev;
    }

    /**
     * DATE 2020/1/3 11:19
     * DESC 生成从3401开始的groupCode
     */
    private String getFunctionGroupCode() {
        int number = functionGroupDao.countAllBy() + 3400 + 1;
        return String.valueOf(number);
    }

    /**
     * DATE 2019/12/13 10:09
     * DESC
     */
    private List<FunctionGroup> getFunctionGroupListByParentId(String parentId) {
        return functionGroupDao.findFunctionGroupsByParentIdAndIsDeletedFalseOrderByGroupName(parentId);
    }

    /**
     * DATE 2019/12/13 10:26
     * DESC
     */
    private FunctionGroup getFunctionGroupByGroupNameAndParentId(String groupName, String parentId) {
        return functionGroupDao.findFunctionGroupByGroupNameAndParentIdAndIsDeletedFalse(groupName, parentId);
    }

    /**
     * DATE 2019/12/13 10:23
     * DESC
     */
    private List<FunctionGroup> getFunctionGroupListByGroupNameLike(String groupName) {
        return functionGroupDao.findFunctionGroupsByGroupNameLikeAndIsDeletedFalseOrderByGroupName("%" + groupName + "%");
    }

    /**
     * DATE 2019/12/13 10:13
     * DESC
     */
    private String getParentId(String parentId) {
        if (StringUtil.isNullOrEmpty(parentId)) {
            parentId = functionGroupDao.findFunctionGroupByGroupNameAndIsDeletedFalse(BaseString.functionGroupBaseName).getId();
        }
        return parentId;
    }

    private FunctionGroupDto getFunctionGroupDto(FunctionGroup functionGroup) {
        return new FunctionGroupDto(
                functionGroup.getId(),
                functionGroup.getGroupCode(),
                functionGroup.getParentId(),
                functionGroup.getGroupName());
    }

    private List<FunctionGroupDto> getFunctionGroupDto(List<FunctionGroup> functionGroupList) {
        return functionGroupList.stream().map(this::getFunctionGroupDto).collect(Collectors.toList());
    }

    private FunctionGroupParentDto getFunctionGroupParentDto(FunctionGroup functionGroup) {
        return new FunctionGroupParentDto(
                functionGroup.getId(),
                functionGroup.getParentId(),
                functionGroup.getGroupName());
    }
}
