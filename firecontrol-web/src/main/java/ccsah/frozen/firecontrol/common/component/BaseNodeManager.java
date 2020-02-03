package ccsah.frozen.firecontrol.common.component;

import ccsah.frozen.firecontrol.common.string.BaseString;
import ccsah.frozen.firecontrol.domain.dao.EmployeeDao;
import ccsah.frozen.firecontrol.service.EmployeeService;
import ccsfr.core.domain.EntityManagerHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 11:09
 * DESC 用于在数据库中生成基础的记录
 */
@Slf4j
@Component
public class BaseNodeManager implements InitializingBean {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeService employeeService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void afterPropertiesSet() {

        EntityManagerHolder.setEntityManager(em);

        /*
         * DESC Employee基础记录 EmployeeName = "未指定人员"
         */
        if (employeeDao.findEmployeeByEmployeeNameAndIsDeletedFalse(BaseString.EmployeeBaseName) == null) {
            employeeService.addEmployee(BaseString.EmployeeBaseName);
            log.info("================节点*" + BaseString.EmployeeBaseName + "*已建立================");
        } else {
            log.info("================节点*" + BaseString.EmployeeBaseName + "*已存在================");
        }
    }
}
