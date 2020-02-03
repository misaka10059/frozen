package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.domain.dto.EmployeeDto;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    EmployeeDto addEmployee(String employeeName);

    EmployeeDto deleteEmployee(String employeeId);

    PageData<EmployeeDto> listEmployeeByParameter(String inspectorName,
                                                  long startQueryTime,
                                                  long endQueryTime,
                                                  Pageable pageRequest);
}
