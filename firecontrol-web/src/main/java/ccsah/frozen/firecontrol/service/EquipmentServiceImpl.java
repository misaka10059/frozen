package ccsah.frozen.firecontrol.service;

import ccsah.frozen.firecontrol.common.code.ExceptionCode;
import ccsah.frozen.firecontrol.domain.dao.EquipmentDao;
import ccsah.frozen.firecontrol.domain.dao.EquipmentDaoSpec;
import ccsah.frozen.firecontrol.domain.dto.AlarmRecordListDto;
import ccsah.frozen.firecontrol.domain.dto.EquipmentDto;
import ccsah.frozen.firecontrol.domain.dto.SmokeDetectorDto;
import ccsah.frozen.firecontrol.domain.entity.AlarmArea;
import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.knowledge.DeviceState;
import ccsah.frozen.iot.domain.dto.device.DeviceDto;
import ccsah.frozen.iot.domain.dto.device.DeviceListDto;
import ccsah.frozen.iot.service.DeviceService;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.PageData;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/24 17:33
 * DESC 设备
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private BaseService baseService;

    @Autowired
    private AlarmRecordService alarmRecordService;

    @Resource
    private DeviceService deviceService;

    /**
     * DATE 2019/12/20 10:37
     * DESC 添加设备
     */
    @Override
    @Transactional
    public EquipmentDto addEquipment(String deviceCode, String alarmAreaId, DeviceState deviceState) {
        if (getEquipmentByDeviceCode(deviceCode) != null) {
            throw new ServiceException(530, ExceptionCode.DEVICE530);
        }
        Device device = Device.create(
                deviceCode,
                baseService.getAlarmAreaById(alarmAreaId),
                deviceState);
        return getEquipmentDto(device);
    }

    /**
     * DATE 2019/12/24 17:43
     * DESC 删除设备
     */
    @Override
    @Transactional
    public EquipmentDto deleteEquipment(String equipmentId) {
        Device device = baseService.getEquipmentById(equipmentId);
        device.deleteLogical();
        return getEquipmentDto(device);
    }

    /**
     * DATE 2019/12/31 11:26
     * DESC 更新设备状态
     */
    @Override
    @Transactional
    public EquipmentDto updateEquipmentState(String deviceCode, DeviceState deviceState) {
        Device device = baseService.getEquipmentByCode(deviceCode);
        device.setDeviceState(deviceState);
        return getEquipmentDto(device);
    }

    @Override
    public SmokeDetectorDto getSmokeDetectorDetail(String deviceCode) {
        DeviceDto deviceDto = deviceService.getDeviceDetailByDeviceCode(deviceCode);
        String equipmentId = getEquipmentByDeviceCode(deviceCode).getId();
        List<AlarmRecordListDto> alarmRecordListDtoList = alarmRecordService.listAlarmRecordByEquipment(equipmentId);
        if (alarmRecordListDtoList == null || alarmRecordListDtoList.isEmpty()) {
            throw new ServiceException(541, ExceptionCode.ALARM_RECORD541);
        }
        AlarmRecordListDto alarmRecordListDto = alarmRecordListDtoList.get(0);
        String content = alarmRecordListDto.getAlarmContent();
        int baseVoltage = Integer.parseInt(content.substring(9, 9 + 4));
        int signalIntensity = Integer.parseInt(content.substring(13, 13 + 2));
        return new SmokeDetectorDto(
                deviceDto.getDeviceId(),
                deviceDto.getDeviceCode(),
                deviceDto.getManufacturerCode(),
                deviceDto.getImeiCode(),
                deviceDto.getEnableState(),
                deviceDto.getOnlineState(),
                deviceDto.getDeviceTypeId(),
                deviceDto.getProductName(),
                deviceDto.getProductType(),
                deviceDto.getVendorId(),
                deviceDto.getVendorName(),
                deviceDto.getDataSourceId(),
                deviceDto.getDataSourceName(),
                deviceDto.getFunctionGroupId(),
                deviceDto.getFunctionGroupName(),
                deviceDto.getProjectId(),
                deviceDto.getProjectCode(),
                deviceDto.getProjectName(),
                deviceDto.getDepartmentId(),
                deviceDto.getDepartmentName(),
                deviceDto.getAreaId(),
                deviceDto.getAreaCode(),
                deviceDto.getAreaName(),
                baseVoltage,
                signalIntensity);
    }

    /**
     * DATE 2019/12/19 11:33
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<EquipmentDto> listEquipmentByParameter(String deviceCode,
                                                           DeviceState deviceState,
                                                           String alarmAreaId,
                                                           long startQueryTime,
                                                           long endQueryTime,
                                                           Pageable pageRequest) {
        AlarmArea alarmArea = StringUtil.isNullOrEmpty(alarmAreaId) ? null : baseService.getAlarmAreaById(alarmAreaId);
        Specification<Device> querySpec = EquipmentDaoSpec.getVariableSpec(
                deviceCode,
                deviceState,
                alarmArea,
                startQueryTime,
                endQueryTime);
        Page<Device> deviceList = equipmentDao.findAll(querySpec, pageRequest);
        List<EquipmentDto> equipmentDtoList = getEquipmentDto(deviceList.getContent());
        return new PageData<>(equipmentDtoList, (int) deviceList.getTotalElements());
    }

    /**
     * DATE 2019/12/25 10:02
     * DESC 查询指定AlarmArea下可添加的设备
     */
    @Override
    public List<DeviceListDto> listEquipmentCanBeAddedByAlarmAreaId(String alarmAreaId) {
        AlarmArea alarmArea = baseService.getAlarmAreaById(alarmAreaId);
        List<EquipmentDto> equipmentDtoList = getEquipmentDto(getEquipmentsByAlarmArea(alarmArea));
        List<DeviceListDto> deviceDtoList = deviceService.listDeviceByAreaCode(alarmArea.getAreaCode());
        List<DeviceListDto> newList = new ArrayList<>();
        for (DeviceListDto deviceListDto : deviceDtoList) {
            boolean state = false;
            for (EquipmentDto equipmentDto : equipmentDtoList) {
                if (equipmentDto.getDeviceCode().equals(deviceListDto.getDeviceCode())) {
                    state = true;
                }
            }
            if (!state) {
                newList.add(deviceListDto);
            }
        }
        return newList;
    }

    /**
     * DATE 2019/12/25 9:47
     * DESC
     */
    private List<Device> getEquipmentsByAlarmArea(AlarmArea alarmArea) {
        return equipmentDao.findDevicesByAlarmAreaAndIsDeletedFalse(alarmArea);
    }

    /**
     * DATE 2019/12/20 10:24
     * DESC
     */
    private Device getEquipmentByDeviceCode(String deviceCode) {
        return equipmentDao.findDeviceByDeviceCodeAndIsDeletedFalse(deviceCode);
    }

    private EquipmentDto getEquipmentDto(Device device) {
        return new EquipmentDto(
                device.getId(),
                device.getAlarmArea().getAreaName(),
                device.getDeviceCode(),
                device.getDeviceState());
    }

    private List<EquipmentDto> getEquipmentDto(List<Device> deviceList) {
        return deviceList.stream().map(this::getEquipmentDto).collect(Collectors.toList());
    }
}
