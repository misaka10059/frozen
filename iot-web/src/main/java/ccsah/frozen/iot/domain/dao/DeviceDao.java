package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.Area;
import ccsah.frozen.iot.domain.entity.Device;
import ccsah.frozen.iot.domain.entity.DeviceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceDao extends JpaRepository<Device, String> {

    Page<Device> findAll(Specification<Device> querySpec, Pageable pageRequest);

    List<Device> findDevicesByArea(Area area);

    Device findDeviceByDeviceCodeAndIsDeletedFalse(String deviceCode);

    Device findByImeiCodeAndIsDeletedFalse(String imeiCode);

    Device findByManufacturerCodeAndIsDeletedFalse(String manufacturerCode);

    Device findDeviceByIdAndIsDeletedFalse(String deviceId);

    int countAllByDeviceTypeAndIsDeletedFalse(DeviceType deviceType);

}
