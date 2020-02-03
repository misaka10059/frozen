package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.vendor.VendorContactSimpleDto;
import ccsah.frozen.iot.domain.dto.vendor.VendorDto;
import ccsah.frozen.iot.domain.dto.vendor.VendorSimpleDto;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 11:40
 * DESC 供应商服务
 */
public interface VendorService {

    VendorSimpleDto addVendor(String vendorName, String address);

    VendorDto deleteVendor(String vendorId);

    VendorDto updateVendor(String vendorId, String vendorName, String address);

    VendorDto getVendorDetailById(String id);

    List<VendorSimpleDto> listVendorByVendorName(String vendorName);

    PageData<VendorContactSimpleDto> listVendorByParameter(String vendorName,
                                                           String vendorAddress,
                                                           long startQueryTime,
                                                           long endQueryTime,
                                                           Pageable pageRequest);

}
