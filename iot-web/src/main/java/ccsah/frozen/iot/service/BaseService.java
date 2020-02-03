package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.dao.*;
import ccsah.frozen.iot.domain.dto.contact.ContactDto;
import ccsah.frozen.iot.domain.dto.contact.ContactSimpleDto;
import ccsah.frozen.iot.domain.dto.device.DeviceListDto;
import ccsah.frozen.iot.domain.dto.vendor.VendorContactSimpleDto;
import ccsah.frozen.iot.domain.dto.vendor.VendorDto;
import ccsah.frozen.iot.domain.entity.*;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/23 14:02
 * DESC
 */
@Service
public class BaseService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private VendorDao vendorDao;

    @Autowired
    private DeviceTypeDao deviceTypeDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private DataSourceDao dataSourceDao;

    @Autowired
    private FunctionGroupDao functionGroupDao;

    /**
     * DATE 2019/12/12 19:54
     * DESC
     */
    Area getAreaById(String id) {
        Area area = areaDao.findAreaByIdAndIsDeletedFalse(id);
        if (area == null) {
            throw new ServiceException(541, ExceptionCode.AREA541);
        }
        return area;
    }

    /**
     * DATE 2019/12/25 9:36
     * DESC
     */
    Area getAreaByAreaCode(String areaCode) {
        return areaDao.findAreaByAreaCodeAndIsDeletedFalse(areaCode);
    }

    /**
     * DATE 2019/12/15 20:45
     * DESC
     */
    String getAreaId(String areaId) {
        if (StringUtil.isNullOrEmpty(areaId)) {
            areaId = areaDao.findAreaByAreaNameAndIsDeletedFalse(BaseString.areaBaseName).getId();
        }
        return areaId;
    }

    /**
     * DATE 2019/12/13 8:41
     * DESC
     */
    String getParentIdOnArea(String parentId) {
        if (StringUtil.isNullOrEmpty(parentId)) {
            parentId = areaDao.findAreaByAreaNameAndIsDeletedFalse(BaseString.areaBaseName).getId();
        }
        return parentId;
    }

    /**
     * DATE 2019/12/13 17:26
     * DESC
     */
    Device getDeviceById(String id) {
        Device device = deviceDao.findDeviceByIdAndIsDeletedFalse(id);
        if (device == null) {
            throw new ServiceException(571, ExceptionCode.DEVICE571);
        }
        return device;
    }

    /**
     * DATE 2019/12/13 11:36
     * DESC
     */
    Vendor getVendorById(String id) {
        Vendor vendor = vendorDao.findVendorByIdAndIsDeletedFalse(id);
        if (vendor == null) {
            throw new ServiceException(521, ExceptionCode.VENDOR521);
        }
        return vendor;
    }

    /**
     * DATE 2019/12/13 16:46
     * DESC
     */
    String getVendorId(String vendorId) {
        if (StringUtil.isNullOrEmpty(vendorId)) {
            vendorId = vendorDao.findVendorByVendorNameAndIsDeletedFalse(BaseString.vendorBaseName).getId();
        }
        return vendorId;
    }

    /**
     * DATE 2019/12/13 16:30
     * DESC
     */
    Vendor getVendorByVendorName(String vendorName) {
        return vendorDao.findVendorByVendorNameAndIsDeletedFalse(vendorName);
    }

    /**
     * DATE 2019/12/13 16:43
     * DESC
     */
    DeviceType getDeviceTypeById(String id) {
        DeviceType deviceType = deviceTypeDao.findDeviceTypeByIdAndIsDeletedFalse(id);
        if (deviceType == null) {
            throw new ServiceException(581, ExceptionCode.DEVICE_TYPE581);
        }
        return deviceType;
    }

    /**
     * DATE 2019/12/15 20:38
     * DESC
     */
    String getDeviceTypeId(String deviceTypeId) {
        if (StringUtil.isNullOrEmpty(deviceTypeId)) {
            deviceTypeId = deviceTypeDao.findDeviceTypeByProductNameAndIsDeletedFalse(BaseString.deviceTypeBaseName).getId();
        }
        return deviceTypeId;
    }

    /**
     * DATE 2020/1/2 14:17
     * DESC
     */
    Device getDeviceByImeiCode(String manufacturerCode) {
        return deviceDao.findByImeiCodeAndIsDeletedFalse(manufacturerCode);
    }

    /**
     * DATE 2019/12/16 12:02
     * DESC
     */
    List<Device> getDeviceListByArea(Area area) {
        return deviceDao.findDevicesByArea(area);
    }


    /**
     * DATE 2019/12/13 9:42
     * DESC
     */
    Project getProjectById(String id) {
        Project project = projectDao.findProjectByIdAndIsDeletedFalse(id);
        if (project == null) {
            throw new ServiceException(601, ExceptionCode.PROJECT601);
        }
        return project;
    }

    private DeviceListDto getDeviceListDto(Device device) {
        return new DeviceListDto(
                device.getId(),
                device.getDeviceCode(),
                device.getEnableState(),
                device.getOnlineState(),
                device.getDeviceType().getId(),
                device.getDeviceType().getProductName(),
                device.getDeviceType().getDataSource().getId(),
                device.getDeviceType().getDataSource().getSourceName(),
                device.getDeviceType().getFunctionGroup().getId(),
                device.getDeviceType().getFunctionGroup().getGroupName(),
                device.getProject().getId(),
                device.getProject().getProjectName(),
                device.getDepartment().getId(),
                device.getDepartment().getDepartmentName(),
                device.getArea().getId(),
                device.getArea().getAreaName());
    }

    List<DeviceListDto> getDeviceListDto(List<Device> deviceList) {
        return deviceList.stream().map(this::getDeviceListDto).collect(Collectors.toList());
    }

    /**
     * DATE 2019/12/15 20:43
     * DESC
     */
    String getProjectId(String projectId) {
        if (StringUtil.isNullOrEmpty(projectId)) {
            projectId = projectDao.findProjectByProjectNameAndIsDeletedFalse(BaseString.projectBaseName).getId();
        }
        return projectId;
    }

    /**
     * DATE 2019/12/13 9:27
     * DESC
     */
    Department getDepartmentById(String id) {
        Department department = departmentDao.findDepartmentByIdAndIsDeletedFalse(id);
        if (department == null) {
            throw new ServiceException(561, ExceptionCode.DEPARTMENT561);
        }
        return department;
    }

    /**
     * DATE 2019/12/15 20:44
     * DESC
     */
    String getDepartmentId(String departmentId) {
        if (StringUtil.isNullOrEmpty(departmentId)) {
            departmentId = departmentDao.findDepartmentByDepartmentNameAndIsDeletedFalse(BaseString.departmentBaseName).getId();
        }
        return departmentId;
    }

    /**
     * DATE 2019/12/13 15:17
     * DESC
     */
    DataSource getDataSourceById(String id) {
        DataSource dataSource = dataSourceDao.findDataSourceByIdAndIsDeletedFalse(id);
        if (dataSource == null) {
            throw new ServiceException(551, ExceptionCode.DATA_SOURCE551);
        }
        return dataSource;
    }

    /**
     * DATE 2019/12/13 16:47
     * DESC
     */
    String getDataSourceId(String dataSourceId) {
        if (StringUtil.isNullOrEmpty(dataSourceId)) {
            dataSourceId = dataSourceDao.findDataSourceBySourceNameAndIsDeletedFalse(BaseString.dataSourceBaseName).getId();
        }
        return dataSourceId;
    }

    /**
     * DATE 2019/12/13 14:54
     * DESC
     */
    DataSource getDataSourceBySourceName(String sourceName) {
        return dataSourceDao.findDataSourceBySourceNameAndIsDeletedFalse(sourceName);
    }

    /**
     * DATE 2019/12/13 10:06
     * DESC
     */
    FunctionGroup getFunctionGroupById(String id) {
        FunctionGroup functionGroup = functionGroupDao.findFunctionGroupByIdAndIsDeletedFalse(id);
        if (functionGroup == null) {
            throw new ServiceException(591, ExceptionCode.FUNCTION_GROUP591);
        }
        return functionGroup;
    }

    /**
     * DATE 2019/12/13 16:48
     * DESC
     */
    String getFunctionGroupId(String functionGroupId) {
        if (StringUtil.isNullOrEmpty(functionGroupId)) {
            functionGroupId = functionGroupDao.findFunctionGroupByGroupNameAndIsDeletedFalse(BaseString.functionGroupBaseName).getId();
        }
        return functionGroupId;
    }

    VendorDto getVendorDto(Vendor vendor) {
        return new VendorDto(
                vendor.getId(),
                vendor.getVendorName(),
                vendor.getAddress(),
                getContactDto(vendor.getContactList()));
    }

    VendorContactSimpleDto getVendorContactSimpleDto(Vendor vendor) {
        return new VendorContactSimpleDto(
                vendor.getId(),
                vendor.getVendorName(),
                vendor.getAddress(),
                getContactSimpleDto(getValidContactList(vendor.getContactList())));
    }

    private List<Contact> getValidContactList(List<Contact> contactList) {
        List<Contact> newList = new ArrayList<>();
        for (Contact contact : contactList) {
            if (!contact.isDeleted()) {
                newList.add(contact);
            }
        }
        return newList;
    }


    ContactDto getContactDto(Contact contact) {
        return new ContactDto(
                contact.getId(),
                contact.getContactName(),
                contact.getGender(),
                contact.getPhoneNumber(),
                contact.getEmail(),
                contact.getWechat(),
                contact.getVendor().getId(),
                contact.getVendor().getVendorName());
    }

    List<ContactDto> getContactDto(List<Contact> contactList) {
        return contactList.stream().map(this::getContactDto).collect(Collectors.toList());
    }

    private ContactSimpleDto getContactSimpleDto(Contact contact) {
        return new ContactSimpleDto(contact.getId(), contact.getContactName(), contact.getPhoneNumber());
    }

    private List<ContactSimpleDto> getContactSimpleDto(List<Contact> contactList) {
        return contactList.stream().map(this::getContactSimpleDto).collect(Collectors.toList());
    }

}
