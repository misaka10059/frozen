package ccsah.frozen.firecontrol.domain.dao;

import ccsah.frozen.firecontrol.domain.entity.AlarmArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmAreaDao extends JpaRepository<AlarmArea, String> {

    AlarmArea findAlarmAreaByIdAndIsDeletedFalse(String id);

    AlarmArea findAlarmAreaByAreaNameAndParentIdAndIsDeletedFalse(String areaName, String parentId);

    List<AlarmArea> findAlarmAreasByParentIdAndIsDeletedFalse(String parentId);

}
