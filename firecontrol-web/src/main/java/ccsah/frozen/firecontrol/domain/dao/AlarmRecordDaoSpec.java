package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.AlarmRecord;
import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.entity.Employee;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/27 11:28
 * DESC
 */
public class AlarmRecordDaoSpec {

    public static Specification<AlarmRecord> getVariableSpec(Device device,
                                                             String alarmContent,
                                                             Employee alarmReceiver,
                                                             Employee confirmedPerson,
                                                             String confirmedResult,
                                                             long startQueryTime,
                                                             long endQueryTime) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            if (device != null) {
                additionList.add(criteriaBuilder.equal(entity.get("device"), device));
            }
            if (!StringUtil.isNullOrEmpty(alarmContent)) {
                additionList.add(criteriaBuilder.like(entity.get("alarmContent"), "%" + alarmContent + "%"));
            }
            if (alarmReceiver != null) {
                additionList.add(criteriaBuilder.equal(entity.get("alarmReceiver"), alarmReceiver));
            }
            if (confirmedPerson != null) {
                additionList.add(criteriaBuilder.equal(entity.get("confirmedPerson"), confirmedPerson));
            }
            if (!StringUtil.isNullOrEmpty(confirmedResult)) {
                additionList.add(criteriaBuilder.like(entity.get("confirmedResult"), "%" + confirmedResult + "%"));
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
