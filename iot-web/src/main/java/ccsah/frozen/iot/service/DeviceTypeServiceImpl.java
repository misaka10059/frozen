package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.domain.dao.DeviceTypeDao;
import ccsah.frozen.iot.domain.dao.DeviceTypeDaoSpec;
import ccsah.frozen.iot.domain.dto.DeviceTypeDto;
import ccsah.frozen.iot.domain.dto.DeviceTypeListDto;
import ccsah.frozen.iot.domain.entity.DataSource;
import ccsah.frozen.iot.domain.entity.DeviceType;
import ccsah.frozen.iot.domain.entity.FunctionGroup;
import ccsah.frozen.iot.domain.entity.Vendor;
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
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/6 11:29
 * DESC 设备类型服务
 */
@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {

    @Autowired
    private DeviceTypeDao deviceTypeDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/6 11:49
     * DESC 添加DeviceType
     */
    @Override
    @Transactional
    public DeviceTypeDto addDeviceType(String productName, String productType, String abbreviation, String vendorId, String dataSourceId, String functionGroupId) {
        Vendor vendor = baseService.getVendorById(baseService.getVendorId(vendorId));
        DataSource dataSource = baseService.getDataSourceById(baseService.getDataSourceId(dataSourceId));
        FunctionGroup functionGroup = baseService.getFunctionGroupById(baseService.getFunctionGroupId(functionGroupId));
        if (getDeviceTypeByProductNameAndProductTypeAndVendorAndDataSourceAndFunctionGroup(productName, productType, vendor, dataSource, functionGroup) != null) {
            throw new ServiceException(580, ExceptionCode.DEVICE_TYPE580);
        }
        DeviceType deviceType = DeviceType.create(productName, productType, abbreviation, vendor, dataSource, functionGroup);
        return getDeviceTypeDto(deviceType);
    }

    /**
     * DATE 2019/12/12 9:56
     * DESC 删除DeviceType
     */
    @Override
    @Transactional
    public DeviceTypeDto deleteDeviceType(String id) {
        DeviceType deviceType = baseService.getDeviceTypeById(id);
        deviceType.deleteLogical();
        return getDeviceTypeDto(deviceType);
    }

    /**
     * DATE 2019/12/12 10:11
     * DESC 更新DeviceType
     */
    @Override
    @Transactional
    public DeviceTypeDto updateDeviceType(String id,
                                          String productName,
                                          String productType,
                                          String abbreviation,
                                          String vendorId,
                                          String dataSourceId,
                                          String functionGroupId) {
        DeviceType deviceType = baseService.getDeviceTypeById(id);
        deviceType.setProductName(productName);
        deviceType.setProductType(productType);
        deviceType.setAbbreviation(abbreviation);
        deviceType.setVendor(baseService.getVendorById(baseService.getVendorId(vendorId)));
        deviceType.setDataSource(baseService.getDataSourceById(baseService.getDataSourceId(dataSourceId)));
        deviceType.setFunctionGroup(baseService.getFunctionGroupById(baseService.getFunctionGroupId(functionGroupId)));
        return getDeviceTypeDto(deviceType);
    }

    /**
     * DATE 2019/12/16 14:48
     * DESC 获取单条DeviceType的详细信息
     */
    @Override
    public DeviceTypeDto getDeviceTypeDetailById(String deviceId) {
        return getDeviceTypeDto(baseService.getDeviceTypeById(deviceId));
    }

    /**
     * DATE 2019/12/12 10:53
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<DeviceTypeListDto> listDeviceTypeByParameter(String productName,
                                                                 String productType,
                                                                 String abbreviation,
                                                                 String vendorId,
                                                                 String dataSourceId,
                                                                 String functionGroupId,
                                                                 long startQueryTime,
                                                                 long endQueryTime,
                                                                 Pageable pageRequest) {
        Vendor vendor = StringUtil.isNullOrEmpty(vendorId) ? null : baseService.getVendorById(vendorId);
        DataSource dataSource = StringUtil.isNullOrEmpty(dataSourceId) ? null : baseService.getDataSourceById(dataSourceId);
        FunctionGroup functionGroup = StringUtil.isNullOrEmpty(functionGroupId) ? null : baseService.getFunctionGroupById(functionGroupId);
        Specification<DeviceType> querySpec = DeviceTypeDaoSpec.getVariableSpec(
                productName,
                productType,
                abbreviation,
                vendor,
                dataSource,
                functionGroup,
                startQueryTime,
                endQueryTime);
        Page<DeviceType> deviceTypeList = deviceTypeDao.findAll(querySpec, pageRequest);
        List<DeviceTypeListDto> deviceTypeListDtoList = getDeviceTypeListDto(deviceTypeList.getContent());
        return new PageData<>(deviceTypeListDtoList, (int) deviceTypeList.getTotalElements());
    }

    /**
     * DATE 2019/12/13 16:39
     * DESC
     */
    private DeviceType getDeviceTypeByProductNameAndProductTypeAndVendorAndDataSourceAndFunctionGroup(String productName, String productType, Vendor vendor, DataSource dataSource, FunctionGroup functionGroup) {
        return deviceTypeDao.findDeviceTypeByProductNameAndProductTypeAndVendorAndDataSourceAndFunctionGroupAndIsDeletedFalse(
                productName,
                productType,
                vendor,
                dataSource,
                functionGroup);
    }

    private DeviceTypeDto getDeviceTypeDto(DeviceType deviceType) {
        return new DeviceTypeDto(
                deviceType.getId(),
                deviceType.getProductName(),
                deviceType.getProductType(),
                deviceType.getAbbreviation(),
                deviceType.getVendor().getId(),
                deviceType.getVendor().getVendorName(),
                deviceType.getDataSource().getId(),
                deviceType.getDataSource().getSourceName(),
                deviceType.getFunctionGroup().getId(),
                deviceType.getFunctionGroup().getGroupName());
    }

    private DeviceTypeListDto getDeviceTypeListDto(DeviceType deviceType) {
        return new DeviceTypeListDto(
                deviceType.getId(),
                deviceType.getProductName(),
                deviceType.getProductType(),
                deviceType.getAbbreviation(),
                deviceType.getVendor().getId(),
                deviceType.getVendor().getVendorName(),
                deviceType.getDataSource().getId(),
                deviceType.getDataSource().getSourceName(),
                deviceType.getFunctionGroup().getId(),
                deviceType.getFunctionGroup().getGroupName());
    }

    private List<DeviceTypeListDto> getDeviceTypeListDto(List<DeviceType> deviceTypeList) {
        return deviceTypeList.stream().map(this::getDeviceTypeListDto).collect(Collectors.toList());
    }
}
