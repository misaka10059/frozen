package ccsah.frozen.iot.component;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.dao.*;
import ccsah.frozen.iot.service.*;
import ccsfr.core.domain.EntityManagerHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/10 14:38
 * DESC 在数据库中建立基础节点
 */
@Slf4j
@EnableScheduling
@Component
public class BaseNodeManager implements InitializingBean {

    @Autowired
    AreaDao areaDao;

    @Autowired
    AreaService areaService;

    @Autowired
    FunctionGroupDao functionGroupDao;

    @Autowired
    FunctionGroupService functionGroupService;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    VendorDao vendorDao;

    @Autowired
    VendorService vendorService;

    @Autowired
    DataSourceDao dataSourceDao;

    @Autowired
    DataSourceService dataSourceService;

    @Autowired
    DeviceTypeDao deviceTypeDao;

    @Autowired
    DeviceTypeService deviceTypeService;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProjectService projectService;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void afterPropertiesSet() {

        EntityManagerHolder.setEntityManager(em);

        /*
         * DESC Area基础记录 areaName = "未指定地区"
         */
        if (areaDao.findAreaByAreaNameAndIsDeletedFalse(BaseString.areaBaseName) == null) {
            areaService.addArea("0", BaseString.areaBaseName, "0");
            log.info("================节点*" + BaseString.areaBaseName + "*已建立================");
        } else {
            log.info("================节点*" + BaseString.areaBaseName + "*已存在================");
        }


        /*
         * DESC FunctionGroup基础记录 projectName = "未指定功能类别"
         */
        if (functionGroupDao.findFunctionGroupByGroupNameAndIsDeletedFalse(BaseString.functionGroupBaseName) == null) {
            functionGroupService.addFunctionGroup( BaseString.functionGroupBaseName, "0");
            log.info("================节点*" + BaseString.functionGroupBaseName + "*已建立================");
        } else {
            log.info("================节点*" + BaseString.functionGroupBaseName + "*已存在================");
        }

        /*
         * DESC Department基础记录 departmentName = "未指定部门"
         */
        if (departmentDao.findDepartmentByDepartmentNameAndIsDeletedFalse(BaseString.departmentBaseName) == null) {
            departmentService.addDepartment(BaseString.departmentBaseName, "0");
            log.info("================节点*" + BaseString.departmentBaseName + "*已建立================");
        } else {
            log.info("================节点*" + BaseString.departmentBaseName + "*已存在================");
        }

        /*
         * DESC Vendor基础记录 vendorName = "未指定厂商"
         */
        if (vendorDao.findVendorByVendorNameAndIsDeletedFalse(BaseString.vendorBaseName) == null) {
            vendorService.addVendor(BaseString.vendorBaseName, "default");
            log.info("================记录*" + BaseString.vendorBaseName + "*已建立================");
        } else {
            log.info("================记录*" + BaseString.vendorBaseName + "*已存在================");
        }

        /*
         * DESC DataSource基础记录 sourceName = "未指定数据源"
         */
        if (dataSourceDao.findDataSourceBySourceNameAndIsDeletedFalse(BaseString.dataSourceBaseName) == null) {
            dataSourceService.addDataSource(BaseString.dataSourceBaseName, "");
            log.info("================记录*" + BaseString.dataSourceBaseName + "*已建立================");
        } else {
            log.info("================记录*" + BaseString.dataSourceBaseName + "*已存在================");
        }

        /*
         * DESC DeviceType基础记录 deviceTypeName = "未指定设备类型"
         */
        if (deviceTypeDao.findDeviceTypeByProductNameAndIsDeletedFalse(BaseString.deviceTypeBaseName) == null) {
            deviceTypeService.addDeviceType(
                    BaseString.deviceTypeBaseName,
                    "",
                    "NS",  //Not Specified
                    vendorDao.findVendorByVendorNameAndIsDeletedFalse(BaseString.vendorBaseName).getId(),
                    dataSourceDao.findDataSourceBySourceNameAndIsDeletedFalse(BaseString.dataSourceBaseName).getId(),
                    functionGroupDao.findFunctionGroupByGroupNameAndIsDeletedFalse(BaseString.functionGroupBaseName).getId());
            log.info("================记录*" + BaseString.deviceTypeBaseName + "*已建立================");
        } else {
            log.info("================记录*" + BaseString.deviceTypeBaseName + "*已存在================");
        }

        /*
         * DESC Project基础记录 projectName = "未指定项目"
         */
        if (projectDao.findProjectByProjectNameAndIsDeletedFalse(BaseString.projectBaseName) == null) {
            projectService.addProject("0", BaseString.projectBaseName);
            log.info("================记录*" + BaseString.projectBaseName + "*已建立================");
        } else {
            log.info("================记录*" + BaseString.projectBaseName + "*已存在================");
        }
    }
}
