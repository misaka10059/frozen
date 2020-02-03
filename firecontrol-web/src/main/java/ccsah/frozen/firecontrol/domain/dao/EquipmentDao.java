package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.AlarmArea;
import ccsah.frozen.firecontrol.domain.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentDao extends JpaRepository<Device, String> {

    Device findDeviceByIdAndIsDeletedFalse(String id);

    Device findDeviceByDeviceCodeAndIsDeletedFalse(String deviceCode);

    List<Device> findDevicesByAlarmAreaAndIsDeletedFalse(AlarmArea alarmArea);

    Page<Device> findAll(Specification<Device> querySpec, Pageable pageRequest);
}
