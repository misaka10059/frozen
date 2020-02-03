package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.AlarmRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRecordDao extends JpaRepository<AlarmRecord, String> {

    AlarmRecord findAlarmRecordByIdAndIsDeletedFalse(String id);

    Page<AlarmRecord> findAll(Specification<AlarmRecord> querySpec, Pageable pageRequest);

}
