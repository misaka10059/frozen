package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.dao.DepartmentDao;
import ccsah.frozen.iot.domain.dto.DepartmentDto;
import ccsah.frozen.iot.domain.dto.DepartmentParentDto;
import ccsah.frozen.iot.domain.entity.Department;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 8:30
 * DESC 部门服务
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/6 8:37
     * DESC 添加Department
     */
    @Override
    @Transactional
    public DepartmentDto addDepartment(String departmentName, String parentId) {
        parentId = getParentId(parentId);
        if (getDepartmentByDepartmentNameAndParentId(departmentName, parentId) != null) {
            throw new ServiceException(560, ExceptionCode.DEPARTMENT560);
        }
        Department department = Department.create(departmentName, parentId);
        return getDepartmentDto(department);
    }

    /**
     * DATE 2019/12/11 16:26
     * DESC 删除指定id的department及其下属的所有department
     */
    @Override
    @Transactional
    public DepartmentDto deleteDepartmentById(String departmentId) {
        Department department = baseService.getDepartmentById(departmentId);
        department.deleteLogical();
        List<Department> departmentList = getDepartmentListByParentId(departmentId);
        if (departmentList.isEmpty()) {
            return getDepartmentDto(department);
        }
        List<DepartmentDto> departmentDtoList = departmentList.stream()
                .map(d -> deleteDepartmentById(d.getId()))
                .collect(Collectors.toList());
        return new DepartmentDto(
                department.getId(),
                department.getParentId(),
                department.getDepartmentName(),
                departmentDtoList);
    }

    /**
     * DATE 2019/12/11 8:37
     * DESC  更新department
     */
    @Override
    @Transactional
    public DepartmentDto updateDepartment(String departmentId, String departmentName, String parentId) {
        Department department = baseService.getDepartmentById(departmentId);
        department.setDepartmentName(departmentName);
        department.setParentId(getParentId(parentId));
        return getDepartmentDto(department);
    }

    /**
     * DATE 2019/12/11 8:43
     * DESC 获取顶层节点拥有的节点列表
     */
    @Override
    public List<DepartmentDto> listDepartmentByTopNode() {
        return getDepartmentDto(getDepartmentListByParentId(BaseString.baseId));
    }

    /**
     * DATE 2019/12/11 8:41
     * DESC 通过departmentName模糊查询DepartmentList
     */
    @Override
    public List<DepartmentDto> listDepartmentByDepartmentName(String departmentName) {
        List<Department> departmentList = getDepartmentListByDepartmentNameLike(departmentName);
        return getDepartmentDto(departmentList);
    }

    /**
     * DATE 2019/12/9 11:37
     * DESC 获取指定Department及下属所有Department记录，并使用depth指定展开层级
     */
    @Override
    public DepartmentDto listDepartmentTreeListById(String departmentId, int depth) {
        Department department = baseService.getDepartmentById(departmentId);
        List<Department> departmentList = getDepartmentListByParentId(departmentId);
        if (depth <= 1 || departmentList.isEmpty()) {
            return getDepartmentDto(department);
        }
        List<DepartmentDto> departmentDtoList = departmentList.stream()
                .map(d -> listDepartmentTreeListById(d.getId(), depth - 1))
                .collect(Collectors.toList());
        DepartmentDto departmentDto = getDepartmentDto(department);
        departmentDto.setDepartmentDtoList(departmentDtoList);
        return departmentDto;
    }

    /**
     * DATE 2019/12/27 16:51
     * DESC 根据当前节点查询当前节点至顶层节点之间的各个节点
     */
    private DepartmentParentDto listDepartmentParent(String departmentId) {
        Department department = baseService.getDepartmentById(departmentId);
        if (department.getParentId().equals(BaseString.baseId)) {
            return getDepartmentParentDto(department);
        }
        DepartmentParentDto newDto = listDepartmentParent(department.getParentId());
        return new DepartmentParentDto(
                department.getId(),
                department.getParentId(),
                department.getDepartmentName(),
                newDto);
    }

    /**
     * DATE 2019/12/27 16:53
     * DESC 反转查询listDepartmentParent的节点列表，使层级结构为由大到小
     */
    @Override
    public DepartmentParentDto listDepartmentSub(String departmentId) {
        DepartmentParentDto departmentParentDto = listDepartmentParent(departmentId);
        DepartmentParentDto prev = null;
        DepartmentParentDto now = departmentParentDto;
        while (now != null) {
            DepartmentParentDto next = now.getDepartmentParentDto();
            now.setDepartmentParentDto(prev);
            prev = now;
            now = next;
        }
        return prev;
    }

    /**
     * DATE 2019/12/13 9:21
     * DESC
     */
    private String getParentId(String parentId) {
        if (StringUtil.isNullOrEmpty(parentId)) {
            parentId = departmentDao.findDepartmentByDepartmentNameAndIsDeletedFalse(BaseString.departmentBaseName).getId();
        }
        return parentId;
    }


    /**
     * DATE 2019/12/16 8:39
     * DESC
     */
    private Department getDepartmentByDepartmentNameAndParentId(String departmentName, String parentId) {
        return departmentDao.findDepartmentByDepartmentNameAndParentIdAndIsDeletedFalse(departmentName, parentId);
    }

    /**
     * DATE 2019/12/16 8:41
     * DESC
     */
    private List<Department> getDepartmentListByParentId(String parentId) {
        return departmentDao.findDepartmentsByParentIdAndIsDeletedFalseOrderByDepartmentName(parentId);
    }

    /**
     * DATE 2019/12/16 8:45
     * DESC
     */
    private List<Department> getDepartmentListByDepartmentNameLike(String departmentName) {
        return departmentDao.findDepartmentsByDepartmentNameLikeAndIsDeletedFalseOrderByDepartmentName("%" + departmentName + "%");
    }

    private DepartmentDto getDepartmentDto(Department department) {
        return new DepartmentDto(department.getId(), department.getParentId(), department.getDepartmentName());
    }

    private List<DepartmentDto> getDepartmentDto(List<Department> departmentList) {
        return departmentList.stream().map(this::getDepartmentDto).collect(Collectors.toList());
    }

    private DepartmentParentDto getDepartmentParentDto(Department department) {
        return new DepartmentParentDto(
                department.getId(),
                department.getParentId(),
                department.getDepartmentName());
    }
}
