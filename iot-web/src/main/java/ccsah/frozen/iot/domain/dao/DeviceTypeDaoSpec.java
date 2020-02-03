package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.entity.DataSource;
import ccsah.frozen.iot.domain.entity.DeviceType;
import ccsah.frozen.iot.domain.entity.FunctionGroup;
import ccsah.frozen.iot.domain.entity.Vendor;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/12 10:22
 * DESC 获取查询参数
 */
public class DeviceTypeDaoSpec {
    public static Specification<DeviceType> getVariableSpec(String productName, String productType,String abbreviation, Vendor vendor, DataSource dataSource, FunctionGroup functionGroup, long startQueryTime, long endQueryTime) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            if (!StringUtil.isNullOrEmpty(productName)) {
                additionList.add(criteriaBuilder.like(entity.get("productName"), "%" + productName + "%"));
            }
            additionList.add(criteriaBuilder.notEqual(entity.get("productName"), BaseString.projectBaseName));
            if (!StringUtil.isNullOrEmpty(productType)) {
                additionList.add(criteriaBuilder.like(entity.get("productType"), "%" + productType + "%"));
            }
            if(!StringUtil.isNullOrEmpty(abbreviation)){
                additionList.add(criteriaBuilder.like(entity.get("abbreviation"),"%"+abbreviation+"%"));
            }
            if (vendor != null) {
                additionList.add(criteriaBuilder.equal(entity.get("vendor"), vendor));
            }
            if (dataSource != null) {
                additionList.add(criteriaBuilder.equal(entity.get("dataSource"), dataSource));
            }
            if (functionGroup != null) {
                additionList.add(criteriaBuilder.equal(entity.get("functionGroup"), functionGroup));
            }
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
