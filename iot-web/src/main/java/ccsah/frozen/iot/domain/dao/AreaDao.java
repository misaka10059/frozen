package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaDao extends JpaRepository<Area, String> {

    List<Area> findAreasByParentIdAndIsDeletedFalse(String parentId);

    Area findAreaByAreaNameAndIsDeletedFalse(String areaName);

    Area findAreaByIdAndIsDeletedFalse(String areaId);

    Area findAreaByAreaCodeAndIsDeletedFalse(String areaCode);

    Area findAreaByAreaNameAndParentIdAndIsDeletedFalse(String areaName, String parentId);

    List<Area> findAreasByAreaNameLikeAndIsDeletedFalse(String areaName);

}
