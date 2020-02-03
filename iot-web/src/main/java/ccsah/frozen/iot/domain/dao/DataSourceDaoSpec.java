package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.entity.DataSource;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/13 15:33
 * DESC 获取查询参数
 */
public class DataSourceDaoSpec {
    public static Specification<DataSource> getVariableSpec(String sourceName, long startQueryTime, long endQueryTime) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            if (!StringUtil.isNullOrEmpty(sourceName)) {
                additionList.add(criteriaBuilder.like(entity.get("sourceName"), "%" + sourceName + "%"));
            }
            additionList.add(criteriaBuilder.notEqual(entity.get("sourceName"), BaseString.dataSourceBaseName));
            if (startQueryTime > 0) {
                Timestamp startTime = Timestamp.from(Instant.ofEpochMilli(startQueryTime));
                additionList.add(criteriaBuilder.greaterThan(entity.get("ctime"), startTime));
            }
            if (endQueryTime > 0) {
                Timestamp endTime = Timestamp.from(Instant.ofEpochMilli(endQueryTime));
                additionList.add(criteriaBuilder.lessThan(entity.get("ctime"), endTime));
            }

            for (Predicate addition : additionList) {
                predicate = criteriaBuilder.and(predicate, addition);
            }
            return predicate;
        };
    }
}
