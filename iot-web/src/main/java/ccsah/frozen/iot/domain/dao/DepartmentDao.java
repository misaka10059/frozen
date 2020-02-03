package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentDao extends JpaRepository<Department, String> {

    List<Department> findDepartmentsByParentIdAndIsDeletedFalseOrderByDepartmentName(String parentId);

    Department findDepartmentByDepartmentNameAndIsDeletedFalse(String departmentName);

    Department findDepartmentByIdAndIsDeletedFalse(String departmentId);

    Department findDepartmentByDepartmentNameAndParentIdAndIsDeletedFalse(String departmentName,String parentId);

    List<Department> findDepartmentsByDepartmentNameLikeAndIsDeletedFalseOrderByDepartmentName(String departmentName);

}
