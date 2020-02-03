package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.DataSource;
import ccsah.frozen.iot.domain.entity.DeviceType;
import ccsah.frozen.iot.domain.entity.FunctionGroup;
import ccsah.frozen.iot.domain.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTypeDao extends JpaRepository<DeviceType, String> {

    DeviceType findDeviceTypeByProductNameAndProductTypeAndVendorAndDataSourceAndFunctionGroupAndIsDeletedFalse(String productName, String productType, Vendor vendor, DataSource dataSource, FunctionGroup functionGroup);

    DeviceType findDeviceTypeByIdAndIsDeletedFalse(String id);

    DeviceType findDeviceTypeByProductNameAndIsDeletedFalse(String typeName);

    Page<DeviceType> findAll(Specification<DeviceType> specification, Pageable pageRequest);
}
