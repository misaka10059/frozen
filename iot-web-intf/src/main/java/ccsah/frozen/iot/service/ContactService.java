package ccsah.frozen.iot.service;

import ccsah.frozen.iot.domain.dto.contact.ContactDto;
import ccsfr.core.web.PageData;
import org.springframework.data.domain.Pageable;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/11 9:29
 * DESC 联系人服务
 */
public interface ContactService {

    ContactDto addContact(String vendorId,
                          String contactName,
                          String gender,
                          String phoneNumber,
                          String email,
                          String wechat);

    ContactDto deleteContact(String contactId);

    ContactDto updateContact(String contactId,
                             String contactName,
                             String gender,
                             String phoneNumber,
                             String email,
                             String wechat);

    PageData<ContactDto> listContactByParameter(String contactName,
                                                String gender,
                                                String phoneNumber,
                                                String email,
                                                String wechat,
                                                long startQueryTime,
                                                long endQueryTime,
                                                Pageable pageRequest);

}
