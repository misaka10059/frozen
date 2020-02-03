package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.AlarmRecord;
import ccsah.frozen.firecontrol.domain.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface AlarmRecordDao extends JpaRepository<AlarmRecord, String> {

    AlarmRecord findByIdAndIsDeletedFalse(String alarmRecordId);

    AlarmRecord findByDeviceAndAlarmTimeAndIsDeletedFalse(Device device, Timestamp alarmTime);

    List<AlarmRecord> findAllByDeviceAndIsDeletedFalseOrderByCtimeDesc(Device device);

    Page<AlarmRecord> findAll(Specification<AlarmRecord> querySpec, Pageable pageRequest);
}
