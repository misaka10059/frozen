package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.DataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceDao extends JpaRepository<DataSource, String> {

    DataSource findDataSourceBySourceNameAndIsDeletedFalse(String sourceName);

    DataSource findDataSourceByIdAndIsDeletedFalse(String id);

    Page<DataSource> findAll(Specification<DataSource> querySpec, Pageable PageRequest);

}
