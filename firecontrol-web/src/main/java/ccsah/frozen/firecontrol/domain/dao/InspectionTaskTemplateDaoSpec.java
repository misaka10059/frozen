package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.Employee;
import ccsah.frozen.firecontrol.domain.entity.InspectionTaskTemplate;
import ccsah.frozen.firecontrol.domain.knowledge.FrequencyTypes;
import ccsah.frozen.firecontrol.domain.knowledge.TemplateState;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/25 14:47
 * DESC
 */
public class InspectionTaskTemplateDaoSpec {

    public static Specification<InspectionTaskTemplate> getVariableSpec(TemplateState templateState,
                                                                        String templateNumber,
                                                                        String inspectionContent,
                                                                        FrequencyTypes inspectionFrequency,
                                                                        Employee inspector,
                                                                        long startQueryTime,
                                                                        long endQueryTime) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            additionList.add(criteriaBuilder.equal(entity.get("templateState"), templateState));
            if (!StringUtil.isNullOrEmpty(templateNumber)) {
                additionList.add(criteriaBuilder.like(entity.get("templateNumber"), "%" + templateNumber + "%"));
            }
            if (!StringUtil.isNullOrEmpty(inspectionContent)) {
                additionList.add(criteriaBuilder.like(entity.get("inspectionContent"), "%" + inspectionContent + "%"));
            }
            additionList.add(criteriaBuilder.equal(entity.get("inspectionFrequency"), inspectionFrequency));
            if (inspector != null) {
                additionList.add(criteriaBuilder.equal(entity.get("inspector"), inspector));
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
