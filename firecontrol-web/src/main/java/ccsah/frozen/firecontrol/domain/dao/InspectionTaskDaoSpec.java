package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.Employee;
import ccsah.frozen.firecontrol.domain.entity.InspectionTask;
import ccsah.frozen.firecontrol.domain.knowledge.InspectionTaskState;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 10:54
 * DESC
 */
public class InspectionTaskDaoSpec {

    public static Specification<InspectionTask> getVariableSpec(Employee inspector,
                                                                String inspectionTaskNumber,
                                                                String inspectionContent,
                                                                InspectionTaskState inspectionTaskState,
                                                                String inspectionResult,
                                                                String additionalMessage) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            if (inspector != null) {
                additionList.add(criteriaBuilder.equal(entity.get("inspector"), inspector));
            }
            if (!StringUtil.isNullOrEmpty(inspectionTaskNumber)) {
                additionList.add(criteriaBuilder.like(entity.get("inspectionTaskNumber"), "%" + inspectionTaskNumber + "%"));
            }
            if (!StringUtil.isNullOrEmpty(inspectionContent)) {
                additionList.add(criteriaBuilder.like(entity.get("inspectionContent"), "%" + inspectionContent + "%"));
            }
            additionList.add(criteriaBuilder.equal(entity.get("inspectionTaskState"), inspectionTaskState));
            if (!StringUtil.isNullOrEmpty(inspectionResult)) {
                additionList.add(criteriaBuilder.like(entity.get("inspectionResult"), "%" + inspectionResult + "%"));
            }
            if (!StringUtil.isNullOrEmpty(additionalMessage)) {
                additionList.add(criteriaBuilder.like(entity.get("additionalMessage"), "%" + additionalMessage + "%"));
            }

            for (Predicate addition : additionList) {
                predicate = criteriaBuilder.and(predicate, addition);
            }
            return predicate;
        };
    }
}
