package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.domain.dao.VendorDao;
import ccsah.frozen.iot.domain.dao.VendorDaoSpec;
import ccsah.frozen.iot.domain.dto.vendor.VendorContactSimpleDto;
import ccsah.frozen.iot.domain.dto.vendor.VendorDto;
import ccsah.frozen.iot.domain.dto.vendor.VendorSimpleDto;
import ccsah.frozen.iot.domain.entity.Vendor;
import ccsfr.core.domain.BaseEntity;
import ccsfr.core.util.StringUtil;
import ccsfr.core.web.PageData;
import ccsfr.core.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 11:40
 * DESC 供应商服务
 */
@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorDao vendorDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/5 14:21
     * DESC 添加Vendor
     */
    @Override
    @Transactional
    public VendorSimpleDto addVendor(String vendorName, String address) {
        Vendor vendor = baseService.getVendorByVendorName(vendorName);
        if (vendor != null) {
            throw new ServiceException(520, ExceptionCode.VENDOR520);
        }
        vendor = Vendor.create(vendorName, getVendorAddress(address));
        return getVendorSimpleDto(vendor);
    }

    /**
     * DATE 2019/12/11 11:20
     * DESC 删除vendor
     */
    @Override
    @Transactional
    public VendorDto deleteVendor(String vendorId) {
        Vendor vendor = baseService.getVendorById(vendorId);
        vendor.deleteLogical();
        vendor.getContactList().forEach(BaseEntity::deleteLogical);
        return baseService.getVendorDto(vendor);
    }

    /**
     * DATE 2019/12/11 11:33
     * DESC 更新vendor
     */
    @Override
    @Transactional
    public VendorDto updateVendor(String vendorId, String vendorName, String address) {
        Vendor vendor = baseService.getVendorById(vendorId);
        if (vendor == null) {
            throw new ServiceException(521, ExceptionCode.VENDOR521);
        }
        vendor.setVendorName(vendorName);
        vendor.setAddress(getVendorAddress(address));
        return baseService.getVendorDto(vendor);
    }

    /**
     * DATE 2019/12/11 15:32
     * DESC 获取一条vendor记录的详细信息
     */
    @Override
    public VendorDto getVendorDetailById(String id) {
        return baseService.getVendorDto(baseService.getVendorById(id));
    }

    /**
     * DATE 2019/12/11 10:19
     * DESC 通过vendorName模糊查询Vendor的列表
     */
    @Override
    public List<VendorSimpleDto> listVendorByVendorName(String vendorName) {
        return getVendorSimpleDto(getVendorListByVendorNameLike(vendorName));
    }

    /**
     * DATE 2019/12/9 9:04
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<VendorContactSimpleDto> listVendorByParameter(String vendorName, String vendorAddress, long startQueryTime, long endQueryTime, Pageable pageRequest) {
        Specification<Vendor> querySpec = VendorDaoSpec.getVariableSpec(
                vendorName,
                vendorAddress,
                startQueryTime,
                endQueryTime);
        Page<Vendor> vendorList = vendorDao.findAll(querySpec, pageRequest);
        List<VendorContactSimpleDto> vendorContactSimpleDtoList = getVendorContactSimpleDto(vendorList.getContent());
        return new PageData<>(vendorContactSimpleDtoList, (int) vendorList.getTotalElements());
    }

    /**
     * DATE 2019/12/16 8:55
     * DESC
     */
    private List<Vendor> getVendorListByVendorNameLike(String vendorName) {
        return vendorDao.findVendorsByVendorNameLikeAndIsDeletedFalseOrderByVendorNameAsc("%" + vendorName + "%");
    }

    /**
     * DATE 2019/12/13 12:55
     * DESC
     */
    private String getVendorAddress(String address) {
        if (StringUtil.isNullOrEmpty(address)) {
            address = "";
        }
        return address;
    }

    private List<VendorContactSimpleDto> getVendorContactSimpleDto(List<Vendor> vendorList) {
        return vendorList.stream().map(baseService::getVendorContactSimpleDto).collect(Collectors.toList());
    }

    private VendorSimpleDto getVendorSimpleDto(Vendor vendor) {
        return new VendorSimpleDto(vendor.getId(), vendor.getVendorName());
    }

    private List<VendorSimpleDto> getVendorSimpleDto(List<Vendor> vendorList) {
        return vendorList.stream().map(this::getVendorSimpleDto).collect(Collectors.toList());
    }
}
