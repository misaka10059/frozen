package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.code.ExceptionCode;
import ccsah.frozen.firecontrol.domain.dao.EmployeeDao;
import ccsah.frozen.firecontrol.domain.dao.EmployeeDaoSpec;
import ccsah.frozen.firecontrol.domain.dto.EmployeeDto;
import ccsah.frozen.firecontrol.domain.entity.Employee;
import ccsfr.core.web.PageData;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/19 10:31
 * DESC 员工
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/20 8:53
     * DESC 添加员工
     */
    @Override
    @Transactional
    public EmployeeDto addEmployee(String employeeName) {
        if (getEmployeeByName(employeeName) != null) {
            throw new ServiceException(520, ExceptionCode.EMPLOYEE520);
        }
        Employee employee = Employee.create(employeeName);
        return getEmployeeDto(employee);
    }

    /**
     * DATE 2019/12/24 15:27
     * DESC 删除员工
     */
    @Override
    @Transactional
    public EmployeeDto deleteEmployee(String employeeId) {
        Employee employee = baseService.getEmployeeById(employeeId);
        employee.deleteLogical();
        return getEmployeeDto(employee);
    }

    /**
     * DATE 2019/12/20 9:24
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<EmployeeDto> listEmployeeByParameter(String inspectorName, long startQueryTime, long endQueryTime, Pageable pageRequest) {
        Specification<Employee> querySpec = EmployeeDaoSpec.getVariableSpec(
                inspectorName,
                startQueryTime,
                endQueryTime);
        Page<Employee> employeeList = employeeDao.findAll(querySpec, pageRequest);
        List<EmployeeDto> employeeDtoList = getEmployeeDto(employeeList.getContent());
        return new PageData<>(employeeDtoList, (int) employeeList.getTotalElements());
    }


    /**
     * DATE 2019/12/19 11:58
     * DESC
     */
    private Employee getEmployeeByName(String employeeName) {
        return employeeDao.findEmployeeByEmployeeNameAndIsDeletedFalse(employeeName);
    }

    private EmployeeDto getEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getEmployeeName());
    }

    private List<EmployeeDto> getEmployeeDto(List<Employee> employeeList) {
        return employeeList.stream().map(this::getEmployeeDto).collect(Collectors.toList());
    }
}
