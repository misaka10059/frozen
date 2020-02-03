package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.code.ExceptionCode;
import ccsah.frozen.firecontrol.common.string.BaseString;
import ccsah.frozen.firecontrol.domain.dao.AlarmAreaDao;
import ccsah.frozen.firecontrol.domain.dao.EmployeeDao;
import ccsah.frozen.firecontrol.domain.dao.EquipmentDao;
import ccsah.frozen.firecontrol.domain.dao.InspectionTaskTemplateDao;
import ccsah.frozen.firecontrol.domain.dto.InspectionPointTemplateSingleDto;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskTemplatePointDto;
import ccsah.frozen.firecontrol.domain.entity.*;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/24 17:36
 * DESC
 */
@Service
public class BaseService {

    @Autowired
    private AlarmAreaDao alarmAreaDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private InspectionTaskTemplateDao taskTemplateDao;

    @Autowired
    private EquipmentDao equipmentDao;

    /**
     * DATE 2019/12/20 10:30
     * DESC
     */
    AlarmArea getAlarmAreaById(String alarmAreaId) {
        AlarmArea alarmArea = alarmAreaDao.findAlarmAreaByIdAndIsDeletedFalse(alarmAreaId);
        if (alarmArea == null) {
            throw new ServiceException(551, ExceptionCode.ALARM_AREA551);
        }
        return alarmArea;
    }

    /**
     * DATE 2019/12/19 10:35
     * DESC
     */
    Employee getEmployeeById(String id) {
        Employee employee = employeeDao.findEmployeeByIdAndIsDeletedFalse(id);
        if (employee == null) {
            throw new ServiceException(521, ExceptionCode.EMPLOYEE521);
        }
        return employee;
    }

    /**
     * DATE 2019/12/27 11:17
     * DESC
     */
    Employee getBaseEmployee() {
        return employeeDao.findEmployeeByEmployeeNameAndIsDeletedFalse(BaseString.EmployeeBaseName);
    }

    /**
     * DATE 2019/12/19 11:10
     * DESC
     */
    InspectionTaskTemplate getTaskTemplateById(String taskTemplateId) {
        InspectionTaskTemplate taskTemplate = taskTemplateDao.findInspectionTaskTemplateByIdAndIsDeletedFalse(taskTemplateId);
        if (taskTemplate == null) {
            throw new ServiceException(511, ExceptionCode.TASK_TEMPLATE511);
        }
        return taskTemplate;
    }

    /**
     * DATE 2019/12/19 8:34
     * DESC
     */
    Device getEquipmentById(String id) {
        Device device = equipmentDao.findDeviceByIdAndIsDeletedFalse(id);
        if (device == null) {
            throw new ServiceException(531, ExceptionCode.DEVICE531);
        }
        return device;
    }

    /**
     * DATE 2019/12/27 14:43
     * DESC
     */
    public Device getEquipmentByCode(String code) {
        Device device = equipmentDao.findDeviceByDeviceCodeAndIsDeletedFalse(code);
        if (device == null) {
            throw new ServiceException(531, ExceptionCode.DEVICE531);
        }
        return device;
    }

    private InspectionPointTemplateSingleDto getPointTemplateSingleDto(InspectionPointTemplate pointTemplate) {
        return new InspectionPointTemplateSingleDto(
                pointTemplate.getId(),
                pointTemplate.getInspectionTaskTemplate().getId(),
                pointTemplate.getDevice().getId(),
                pointTemplate.getDevice().getDeviceCode());
    }

    private List<InspectionPointTemplateSingleDto> getPointTemplateSingleDto(List<InspectionPointTemplate> pointTemplateList) {
        return pointTemplateList.stream()
                .map(this::getPointTemplateSingleDto)
                .collect(Collectors.toList());
    }

    InspectionTaskTemplatePointDto getTaskTemplatePointDto(InspectionTaskTemplate taskTemplate) {
        return new InspectionTaskTemplatePointDto(taskTemplate.getId(),
                taskTemplate.getTemplateState(),
                taskTemplate.getTemplateNumber(),
                taskTemplate.getInspectionContent(),
                taskTemplate.getInspectionFrequency(),
                taskTemplate.getAdditionalFields(),
                taskTemplate.getHourTime(),
                taskTemplate.getMinuteTime(),
                taskTemplate.getInspector().getId(),
                taskTemplate.getInspector().getEmployeeName(),
                getPointTemplateSingleDto(getValidPointTemplateList(taskTemplate.getPointTemplateList())));
    }

    private List<InspectionPointTemplate> getValidPointTemplateList(List<InspectionPointTemplate> pointTemplateList) {
        List<InspectionPointTemplate> newList = new ArrayList<>();
        for (InspectionPointTemplate pointTemplate : pointTemplateList) {
            if (!pointTemplate.isDeleted()) {
                newList.add(pointTemplate);
            }
        }
        return newList;
    }

    List<InspectionTaskTemplatePointDto> getTaskTemplatePointDto(List<InspectionTaskTemplate> taskTemplateList) {
        return taskTemplateList.stream()
                .map(this::getTaskTemplatePointDto)
                .collect(Collectors.toList());
    }
}
