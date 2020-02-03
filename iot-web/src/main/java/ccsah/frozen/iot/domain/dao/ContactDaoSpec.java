package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.Contact;
import ccsfr.core.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/11 14:37
 * DESC 获取查询参数
 */
public class ContactDaoSpec {
    public static Specification<Contact> getVariableSpec(
            String contactName,
            String gender,
            String phoneNumber,
            String email,
            String wechat,
            long startQueryTime,
            long endQueryTime) {
        return (entity, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isFalse(entity.get("isDeleted"));
            List<Predicate> additionList = new ArrayList<>();

            if (!StringUtil.isNullOrEmpty(contactName)) {
                additionList.add(criteriaBuilder.like(entity.get("contactName"), "%" + contactName + "%"));
            }
            if (!StringUtil.isNullOrEmpty(gender)) {
                additionList.add(criteriaBuilder.like(entity.get("gender"), "%" + gender + "%"));
            }
            if (!StringUtil.isNullOrEmpty(phoneNumber)) {
                additionList.add(criteriaBuilder.like(entity.get("phoneNumber"), "%" + phoneNumber + "%"));
            }
            if (!StringUtil.isNullOrEmpty(email)) {
                additionList.add(criteriaBuilder.like(entity.get("email"), "%" + email + "%"));
            }
            if (!StringUtil.isNullOrEmpty(wechat)) {
                additionList.add(criteriaBuilder.like(entity.get("wechat"), "%" + wechat + "%"));
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
