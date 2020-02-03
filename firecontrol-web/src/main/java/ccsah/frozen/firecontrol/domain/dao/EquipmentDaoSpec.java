package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.AlarmArea;
import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.knowledge.DeviceState;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/20 10:38
 * DESC
 */
public class EquipmentDaoSpec {
    public static Specification<Device> getVariableSpec(
            String deviceCode,
            DeviceState deviceState,
            AlarmArea alarmArea,
            long startQueryTime,
            long endQueryTime) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            if (!StringUtil.isNullOrEmpty(deviceCode)) {
                additionList.add(criteriaBuilder.like(entity.get("deviceCode"), "%" + deviceCode + "%"));
            }
            if (deviceState != null) {
                additionList.add(criteriaBuilder.equal(entity.get("deviceState"), deviceState));
            }
            if (alarmArea != null) {
                additionList.add(criteriaBuilder.equal(entity.get("alarmArea"), alarmArea));
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
