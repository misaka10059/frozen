package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.DepartmentDto;
import ccsah.frozen.iot.domain.dto.DepartmentParentDto;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 8:30
 * DESC 部门服务
 */
public interface DepartmentService {

    DepartmentDto addDepartment(String departmentName, String parentId);

    DepartmentDto deleteDepartmentById(String departmentId);

    DepartmentDto updateDepartment(String departmentId, String departmentName, String parentId);

    List<DepartmentDto> listDepartmentByTopNode();

    List<DepartmentDto> listDepartmentByDepartmentName(String departmentName);

    DepartmentDto listDepartmentTreeListById(String departmentId, int depth);

    DepartmentParentDto listDepartmentSub(String departmentId);

}
