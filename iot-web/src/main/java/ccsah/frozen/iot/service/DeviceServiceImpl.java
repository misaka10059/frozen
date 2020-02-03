package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.domain.dao.DeviceDao;
import ccsah.frozen.iot.domain.dao.DeviceDaoSpec;
import ccsah.frozen.iot.domain.dto.device.CameraDeviceDto;
import ccsah.frozen.iot.domain.dto.device.DeviceDto;
import ccsah.frozen.iot.domain.dto.device.DeviceListDto;
import ccsah.frozen.iot.domain.entity.*;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableStateQuery;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineStateQuery;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.PageData;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 14:02
 * DESC 设备服务
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/6 14:23
     * DESC 添加Device
     */
    @Override
    @Transactional
    public DeviceDto addDevice(String manufacturerCode,
                               String imeiCode,
                               DeviceEnableState enableState,
                               DeviceOnlineState onlineState,
                               String deviceTypeId,
                               String projectId,
                               String departmentId,
                               String areaId) {
        if (baseService.getDeviceByImeiCode(imeiCode) != null) {
            throw new ServiceException(570, ExceptionCode.DEVICE570);
        }
        DeviceType deviceType = baseService.getDeviceTypeById(deviceTypeId);
        Device device = Device.create(
                getDeviceCode(deviceType),
                getManufacturerCode(manufacturerCode),
                getImeiCode(imeiCode),
                enableState,
                onlineState,
                deviceType,
                baseService.getProjectById(baseService.getProjectId(projectId)),
                baseService.getDepartmentById(baseService.getDepartmentId(departmentId)),
                baseService.getAreaById(baseService.getAreaId(areaId)));
        return getDeviceDto(device);
    }

    /**
     * DATE 2019/12/12 14:07
     * DESC 删除Device
     */
    @Override
    @Transactional
    public DeviceDto deleteDevice(String deviceId) {
        Device device = baseService.getDeviceById(deviceId);
        device.deleteLogical();
        return getDeviceDto(device);
    }

    /**
     * DATE 2019/12/12 16:23
     * DESC 更新Device
     */
    @Override
    @Transactional
    public DeviceDto updateDevice(String deviceId,
                                  String manufacturerCode,
                                  String imeiCode,
                                  DeviceEnableState enableState,
                                  DeviceOnlineState onlineState,
                                  String deviceTypeId,
                                  String projectId,
                                  String departmentId,
                                  String areaId) {
        Device device = baseService.getDeviceById(deviceId);
        device.setManufacturerCode(getManufacturerCode(manufacturerCode));
        device.setImeiCode(getImeiCode(imeiCode));
        device.setEnableState(enableState);
        device.setOnlineState(onlineState);
        device.setDeviceType(baseService.getDeviceTypeById(baseService.getDeviceTypeId(deviceTypeId)));
        device.setProject(baseService.getProjectById(baseService.getProjectId(projectId)));
        device.setDepartment(baseService.getDepartmentById(baseService.getDepartmentId(departmentId)));
        device.setArea((baseService.getAreaById(baseService.getAreaId(areaId))));
        return getDeviceDto(device);
    }

    /**
     * DATE 2019/12/16 14:32
     * DESC 获取单条Device详细信息
     */
    @Override
    public DeviceDto getDeviceDetailById(String deviceId) {
        return getDeviceDto(baseService.getDeviceById(deviceId));
    }

    /**
     * DATE 2020/1/7 11:53
     * DESC 
     */
    @Override
    public DeviceDto getDeviceDetailByDeviceCode(String deviceCode) {
        return getDeviceDto(deviceDao.findDeviceByDeviceCodeAndIsDeletedFalse(deviceCode));
    }

    /**
     * DATE 2019/12/31 11:17
     * DESC
     */
    @Override
    public CameraDeviceDto getDeviceCameraDtoById(String deviceId) {
        return getDeviceCameraDto(baseService.getDeviceById(deviceId));
    }

    /**
     * DATE 2019/12/9 15:52
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<DeviceListDto> listDeviceByParameter(String deviceCode,
                                                         String manufacturerCode,
                                                         String imeiCode,
                                                         DeviceEnableStateQuery enableStateQuery,
                                                         DeviceOnlineStateQuery onlineStateQuery,
                                                         long startQueryTime,
                                                         long endQueryTime,
                                                         String deviceTypeId,
                                                         String projectId,
                                                         String departmentId,
                                                         String areaId,
                                                         Pageable pageRequest) {
        DeviceType deviceType = StringUtil.isNullOrEmpty(deviceTypeId) ? null : baseService.getDeviceTypeById(deviceTypeId);
        Project project = StringUtil.isNullOrEmpty(projectId) ? null : baseService.getProjectById(projectId);
        Department department = StringUtil.isNullOrEmpty(departmentId) ? null : baseService.getDepartmentById(departmentId);
        Area area = StringUtil.isNullOrEmpty(areaId) ? null : baseService.getAreaById(areaId);

        Specification<Device> querySpec = DeviceDaoSpec.getVariableSpec(
                deviceCode,
                manufacturerCode,
                imeiCode,
                enableStateQuery,
                onlineStateQuery,
                startQueryTime,
                endQueryTime,
                deviceType,
                project,
                department,
                area);
        Page<Device> deviceList = deviceDao.findAll(querySpec, pageRequest);
        List<DeviceListDto> deviceListDtoList = baseService.getDeviceListDto(deviceList.getContent());
        return new PageData<>(deviceListDtoList, (int) deviceList.getTotalElements());
    }

    /**
     * DATE 2019/12/25 9:37
     * DESC
     */
    public List<DeviceListDto> listDeviceByAreaCode(String areaCode) {
        Area area = baseService.getAreaByAreaCode(areaCode);
        if (area == null) {
            return null;
        }
        return baseService.getDeviceListDto(baseService.getDeviceListByArea(area));
    }

    /**
     * DATE 2020/1/3 15:39
     * DESC
     */
    private String getDeviceCode(DeviceType deviceType) {
        int number = deviceDao.countAllByDeviceTypeAndIsDeletedFalse(deviceType) + 1000 + 1;
        String code = deviceType.getFunctionGroup().getGroupCode();
        return deviceType.getAbbreviation() + code + number;
    }

    /**
     * DATE 2019/12/15 20:28
     * DESC
     */
    private String getManufacturerCode(String manufacturerCode) {
        if (StringUtil.isNullOrEmpty(manufacturerCode)) {
            manufacturerCode = "";
        }
        return manufacturerCode;
    }

    /**
     * DATE 2019/12/15 20:29
     * DESC
     */
    private String getImeiCode(String imeiCode) {
        if (StringUtil.isNullOrEmpty(imeiCode)) {
            imeiCode = "";
        }
        return imeiCode;
    }

    private DeviceDto getDeviceDto(Device device) {
        return new DeviceDto(
                device.getId(),
                device.getDeviceCode(),
                device.getManufacturerCode(),
                device.getImeiCode(),
                device.getEnableState(),
                device.getOnlineState(),
                device.getDeviceType().getId(),
                device.getDeviceType().getProductName(),
                device.getDeviceType().getProductType(),
                device.getDeviceType().getVendor().getId(),
                device.getDeviceType().getVendor().getVendorName(),
                device.getDeviceType().getDataSource().getId(),
                device.getDeviceType().getDataSource().getSourceName(),
                device.getDeviceType().getFunctionGroup().getId(),
                device.getDeviceType().getFunctionGroup().getGroupName(),
                device.getProject().getId(),
                device.getProject().getProjectCode(),
                device.getProject().getProjectName(),
                device.getDepartment().getId(),
                device.getDepartment().getDepartmentName(),
                device.getArea().getId(),
                device.getArea().getAreaCode(),
                device.getArea().getAreaName());
    }

    private CameraDeviceDto getDeviceCameraDto(Device device) {
        return new CameraDeviceDto(
                device.getId(),
                device.getDeviceCode(),
                device.getManufacturerCode(),
                device.getImeiCode(),
                device.getEnableState(),
                device.getOnlineState(),
                device.getDeviceType().getId(),
                device.getDeviceType().getProductName(),
                device.getDeviceType().getProductType(),
                device.getDeviceType().getVendor().getId(),
                device.getDeviceType().getVendor().getVendorName(),
                device.getDeviceType().getDataSource().getId(),
                device.getDeviceType().getDataSource().getSourceName(),
                device.getDeviceType().getFunctionGroup().getId(),
                device.getDeviceType().getFunctionGroup().getGroupName(),
                device.getProject().getId(),
                device.getProject().getProjectCode(),
                device.getProject().getProjectName(),
                device.getDepartment().getId(),
                device.getDepartment().getDepartmentName(),
                device.getArea().getId(),
                device.getArea().getAreaCode(),
                device.getArea().getAreaName(),
                device.getDeviceType().getDataSource().getManagementPlatform() + device.getManufacturerCode());
    }
}