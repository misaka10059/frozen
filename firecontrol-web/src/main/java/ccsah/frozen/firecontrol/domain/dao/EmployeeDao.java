package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, String> {

    Employee findEmployeeByIdAndIsDeletedFalse(String id);

    Employee findEmployeeByEmployeeNameAndIsDeletedFalse(String name);

    Page<Employee> findAll(Specification<Employee> querySpec, Pageable pageRequest);
}
