package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.*;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableState;
import ccsah.frozen.iot.domain.knowledge.DeviceEnableStateQuery;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineState;
import ccsah.frozen.iot.domain.knowledge.DeviceOnlineStateQuery;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/9 14:08
 * DESC 获取查询参数
 */
public class DeviceDaoSpec {
    public static Specification<Device> getVariableSpec(
            String deviceCode,
            String manufacturerCode,
            String imeiCode,
            DeviceEnableStateQuery enableStateQuery,
            DeviceOnlineStateQuery onlineStateQuery,
            long startQueryTime,
            long endQueryTime,
            DeviceType deviceType,
            Project project,
            Department department,
            Area area) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            if (!StringUtil.isNullOrEmpty(deviceCode)) {
                additionList.add(criteriaBuilder.like(entity.get("deviceCode"), "%" + deviceCode + "%"));
            }
            if (!StringUtil.isNullOrEmpty(manufacturerCode)) {
                additionList.add(criteriaBuilder.like(entity.get("manufacturerCode"), "%" + manufacturerCode + "%"));
            }
            if (!StringUtil.isNullOrEmpty(imeiCode)) {
                additionList.add(criteriaBuilder.like(entity.get("imeiCode"), "%" + imeiCode + "%"));
            }
            if (enableStateQuery == DeviceEnableStateQuery.ENABLE) {
                additionList.add(criteriaBuilder.equal(entity.get("enableState"), DeviceEnableState.ENABLE));
            }
            if (enableStateQuery == DeviceEnableStateQuery.DISABLE) {
                additionList.add(criteriaBuilder.equal(entity.get("enableState"), DeviceEnableState.DISABLE));
            }
            if (onlineStateQuery == DeviceOnlineStateQuery.ONLINE) {
                additionList.add(criteriaBuilder.equal(entity.get("onlineState"), DeviceOnlineState.ONLINE));
            }
            if (onlineStateQuery == DeviceOnlineStateQuery.OFFLINE) {
                additionList.add(criteriaBuilder.equal(entity.get("onlineState"), DeviceOnlineState.OFFLINE));
            }
            if (startQueryTime > 0) {
                Timestamp startTime = Timestamp.from(Instant.ofEpochMilli(startQueryTime));
                additionList.add(criteriaBuilder.greaterThan(entity.get("ctime"), startTime));
            }
            if (endQueryTime > 0) {
                Timestamp endTime = Timestamp.from(Instant.ofEpochMilli(endQueryTime));
                additionList.add(criteriaBuilder.lessThan(entity.get("ctime"), endTime));
            }
            if (deviceType != null) {
                additionList.add(criteriaBuilder.equal(entity.get("deviceType"), deviceType));
            }
            if (project != null) {
                additionList.add(criteriaBuilder.equal(entity.get("project"), project));
            }
            if (department != null) {
                additionList.add(criteriaBuilder.equal(entity.get("department"), department));
            }
            if (area != null) {
                additionList.add(criteriaBuilder.equal(entity.get("area"), area));
            }

            for (Predicate addition : additionList) {
                predicate = criteriaBuilder.and(predicate, addition);
            }
            return predicate;
        };
    }
}
