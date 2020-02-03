package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.code.ExceptionCode;
import ccsah.frozen.firecontrol.domain.dao.InspectionPointTemplateDao;
import ccsah.frozen.firecontrol.domain.dto.InspectionTaskTemplatePointDto;
import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.entity.InspectionPointTemplate;
import ccsah.frozen.firecontrol.domain.entity.InspectionTaskTemplate;
import ccsfr.core.domain.EntityManagerHolder;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/25 16:16
 * DESC 巡检点模板
 */
@Service
public class InspectionPointTemplateServiceImpl implements InspectionPointTemplateService {

    @Autowired
    private InspectionPointTemplateDao pointTemplateDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/20 10:09
     * DESC 添加巡检点模板
     */
    @Override
    @Transactional
    public InspectionTaskTemplatePointDto addPointTemplate(String taskTemplateId, List<String> deviceIdList) {
        InspectionTaskTemplate taskTemplate = baseService.getTaskTemplateById(taskTemplateId);
        deviceIdList.stream()
                .filter(deviceId -> getPointTemplateByTaskTemplateIdAndDeviceId(taskTemplateId, deviceId) == null)
                .forEach(deviceId -> InspectionPointTemplate.create(
                        taskTemplate,
                        baseService.getEquipmentById(deviceId)));
        EntityManagerHolder.getEntityManager().flush();
        EntityManagerHolder.getEntityManager().refresh(taskTemplate);
        return baseService.getTaskTemplatePointDto(taskTemplate);
    }

    /**
     * DATE 2019/12/25 17:11
     * DESC
     */
    @Override
    @Transactional
    public InspectionTaskTemplatePointDto deletePointTemplate(String pointTemplateId) {
        InspectionPointTemplate pointTemplate = getPointTemplateById(pointTemplateId);
        InspectionTaskTemplate taskTemplate = pointTemplate.getInspectionTaskTemplate();
        pointTemplate.deleteLogical();
        EntityManagerHolder.getEntityManager().flush();
        EntityManagerHolder.getEntityManager().refresh(taskTemplate);
        return baseService.getTaskTemplatePointDto(taskTemplate);
    }

    /**
     * DATE 2019/12/25 16:43
     * DESC
     */
    private InspectionPointTemplate getPointTemplateById(String pointTemplateId) {
        InspectionPointTemplate pointTemplate = pointTemplateDao.findByIdAndIsDeletedFalse(pointTemplateId);
        if (pointTemplate == null) {
            throw new ServiceException(561, ExceptionCode.POINT_TEMPLATE561);
        }
        return pointTemplate;
    }

    /**
     * DATE 2019/12/19 11:38
     * DESC
     */
    private InspectionPointTemplate getPointTemplateByTaskTemplateIdAndDeviceId(String taskTemplateId, String deviceId) {
        InspectionTaskTemplate taskTemplate = baseService.getTaskTemplateById(taskTemplateId);
        Device device = baseService.getEquipmentById(deviceId);
        return pointTemplateDao.findInspectionPointTemplateByInspectionTaskTemplateAndDeviceAndIsDeletedFalse(taskTemplate, device);
    }


}
