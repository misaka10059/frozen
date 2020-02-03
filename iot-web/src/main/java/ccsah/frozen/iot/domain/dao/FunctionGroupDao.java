package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.FunctionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FunctionGroupDao extends JpaRepository<FunctionGroup, String> {

    List<FunctionGroup> findFunctionGroupsByParentIdAndIsDeletedFalseOrderByGroupName(String parentId);

    FunctionGroup findFunctionGroupByGroupNameAndIsDeletedFalse(String groupName);

    FunctionGroup findFunctionGroupByIdAndIsDeletedFalse(String id);

    FunctionGroup findFunctionGroupByGroupNameAndParentIdAndIsDeletedFalse(String groupName, String parentId);

    List<FunctionGroup> findFunctionGroupsByGroupNameLikeAndIsDeletedFalseOrderByGroupName(String groupName);

    int countAllBy();

}
