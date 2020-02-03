package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorDao extends JpaRepository<Vendor, String> {

    Vendor findVendorByVendorNameAndIsDeletedFalse(String vendorName);

    Vendor findVendorByIdAndIsDeletedFalse(String vendorId);

    List<Vendor> findVendorsByVendorNameLikeAndIsDeletedFalseOrderByVendorNameAsc(String vendorName);

    Page<Vendor> findAll(Specification<Vendor> specification, Pageable pageRequest);

}
