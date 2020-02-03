package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.domain.dao.DataSourceDao;
import ccsah.frozen.iot.domain.dao.DataSourceDaoSpec;
import ccsah.frozen.iot.domain.dto.DataSourceDto;
import ccsah.frozen.iot.domain.entity.DataSource;
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
 * DATE 2019/12/5 14:50
 * DESC 数据源服务
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Autowired
    private DataSourceDao dataSourceDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/5 14:56
     * DESC 添加DataSource
     */
    @Override
    @Transactional
    public DataSourceDto addDataSource(String sourceName, String managementPlatform) {
        DataSource dataSource = baseService.getDataSourceBySourceName(sourceName);
        if (dataSource != null) {
            throw new ServiceException(550, ExceptionCode.DATA_SOURCE550);
        }
        dataSource = DataSource.create(sourceName, getManagementPlatform(managementPlatform));
        return getDataSourceDto(dataSource);
    }

    /**
     * DATE 2019/12/13 15:18
     * DESC 删除DataSource
     */
    @Override
    @Transactional
    public DataSourceDto deleteDataSource(String id) {
        DataSource dataSource = baseService.getDataSourceById(id);
        dataSource.deleteLogical();
        return getDataSourceDto(dataSource);
    }

    /**
     * DATE 2019/12/13 15:34
     * DESC 更新DataSource
     */
    @Override
    @Transactional
    public DataSourceDto updateDataSource(String id, String sourceName, String managementPlatform) {
        DataSource dataSource = baseService.getDataSourceById(id);
        dataSource.setSourceName(sourceName);
        dataSource.setManagementPlatform(getManagementPlatform(managementPlatform));
        return getDataSourceDto(dataSource);
    }

    /**
     * DATE 2019/12/13 16:04
     * DESC 可选参数查询，分页
     */
    @Override
    @Transactional
    public PageData<DataSourceDto> listDataSourceByParameter(String sourceName, long startQueryTime, long endQueryTime, Pageable pageRequest) {
        Specification<DataSource> querySpec = DataSourceDaoSpec.getVariableSpec(sourceName, startQueryTime, endQueryTime);
        Page<DataSource> dataSourceList = dataSourceDao.findAll(querySpec, pageRequest);
        List<DataSourceDto> dataSourceDtoList = getDataSourceDto(dataSourceList.getContent());
        return new PageData<>(dataSourceDtoList, (int) dataSourceList.getTotalElements());
    }

    /**
     * DATE 2019/12/13 14:58
     * DESC
     */
    private String getManagementPlatform(String managementPlatform) {
        if (StringUtil.isNullOrEmpty(managementPlatform)) {
            managementPlatform = "";
        }
        return managementPlatform;
    }

    private DataSourceDto getDataSourceDto(DataSource dataSource) {
        return new DataSourceDto(dataSource.getId(), dataSource.getSourceName(), dataSource.getManagementPlatform());
    }

    private List<DataSourceDto> getDataSourceDto(List<DataSource> dataSourceList) {
        return dataSourceList.stream().map(this::getDataSourceDto).collect(Collectors.toList());
    }
}
