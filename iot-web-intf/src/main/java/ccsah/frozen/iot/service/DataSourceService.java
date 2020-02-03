package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.DataSourceDto;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 14:50
 * DESC 数据源服务
 */
public interface DataSourceService {

    DataSourceDto addDataSource(String sourceName, String managementPlatform);

    DataSourceDto deleteDataSource(String id);

    DataSourceDto updateDataSource(String id, String sourceName, String managementPlatform);

    PageData<DataSourceDto> listDataSourceByParameter(String sourceName,
                                                      long startQueryTime,
                                                      long endQueryTime,
                                                      Pageable pageRequest);

}
