package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.domain.entity.Project;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/12 8:48
 * DESC 获取查询参数
 */
public class ProjectDaoSpec {
    public static Specification<Project> getVariableSpec(String projectCode, String projectName, long startQueryTime, long endQueryTime) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            if (!StringUtil.isNullOrEmpty(projectCode)) {
                additionList.add(criteriaBuilder.like(entity.get("projectCode"), "%" + projectCode + "%"));
            }
            if (!StringUtil.isNullOrEmpty(projectName)) {
                additionList.add(criteriaBuilder.like(entity.get("projectName"), "%" + projectName + "%"));
            }
            additionList.add(criteriaBuilder.notEqual(entity.get("projectName"), BaseString.projectBaseName));
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
