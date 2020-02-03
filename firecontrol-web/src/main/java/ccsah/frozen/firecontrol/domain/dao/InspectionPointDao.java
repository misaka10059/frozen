package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.Device;
import ccsah.frozen.firecontrol.domain.entity.InspectionPoint;
import ccsah.frozen.firecontrol.domain.knowledge.InspectionPointState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionPointDao extends JpaRepository<InspectionPoint, String> {

    InspectionPoint findByIdAndIsDeletedFalse(String pointId);

    List<InspectionPoint> findAllByDeviceAndPointStateAndIsDeletedFalse(Device device, InspectionPointState pointState);

}
