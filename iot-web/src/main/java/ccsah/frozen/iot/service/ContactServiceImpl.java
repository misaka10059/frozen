package ccsah.frozen.iot.service;

import ccsah.frozen.iot.common.code.ExceptionCode;
import ccsah.frozen.iot.domain.dao.ContactDao;
import ccsah.frozen.iot.domain.dao.ContactDaoSpec;
import ccsah.frozen.iot.domain.dto.contact.ContactDto;
import ccsah.frozen.iot.domain.entity.Contact;
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

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/11 9:29
 * DESC 联系人服务
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private BaseService baseService;

    /**
     * DATE 2019/12/11 9:39
     * DESC 添加联系人
     */
    @Override
    @Transactional
    public ContactDto addContact(String vendorId, String contactName, String gender, String phoneNumber, String email, String wechat) {
        Contact contact = getContactByPhoneNumber(phoneNumber);
        if (contact != null) {
            throw new ServiceException(510, ExceptionCode.CONTACT510);
        }
        contact = Contact.create(
                baseService.getVendorById(vendorId),
                contactName,
                gender,
                phoneNumber,
                getEmail(email),
                getWechat(wechat));
        return baseService.getContactDto(contact);
    }

    /**
     * DATE 2019/12/13 11:45
     * DESC 删除联系人
     */
    @Override
    @Transactional
    public ContactDto deleteContact(String contactId) {
        Contact contact = getContactById(contactId);
        contact.deleteLogical();
        return baseService.getContactDto(contact);
    }

    /**
     * DATE 2019/12/11 10:06
     * DESC 更新联系人
     */
    @Override
    @Transactional
    public ContactDto updateContact(String contactId, String contactName, String gender, String phoneNumber, String email, String wechat) {
        Contact contact = getContactById(contactId);
        contact.setContactName(contactName);
        contact.setGender(gender);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmail(getEmail(email));
        contact.setWechat(getWechat(wechat));
        return baseService.getContactDto(contact);
    }

    /**
     * DATE 2019/12/11 14:58
     * DESC 可选参数查询，分页
     */
    @Override
    public PageData<ContactDto> listContactByParameter(String contactName,
                                                       String gender,
                                                       String phoneNumber,
                                                       String email,
                                                       String wechat,
                                                       long startQueryTime,
                                                       long endQueryTime,
                                                       Pageable pageRequest) {
        Specification<Contact> querySpec = ContactDaoSpec.getVariableSpec(
                contactName,
                gender,
                phoneNumber,
                email,
                wechat,
                startQueryTime,
                endQueryTime);
        Page<Contact> contactList = contactDao.findAll(querySpec, pageRequest);
        List<ContactDto> contactDtoList = baseService.getContactDto(contactList.getContent());
        return new PageData<>(contactDtoList, (int) contactList.getTotalElements());
    }

    /**
     * DATE 2019/12/13 11:45
     * DESC
     */
    private Contact getContactById(String id) {
        Contact contact = contactDao.findContactByIdAndIsDeletedFalse(id);
        if (contact == null) {
            throw new ServiceException(511, ExceptionCode.CONTACT511);
        }
        return contact;
    }

    /**
     * DATE 2019/12/13 11:29
     * DESC
     */
    private Contact getContactByPhoneNumber(String phoneNumber) {
        return contactDao.findContactByPhoneNumberAndIsDeletedFalse(phoneNumber);
    }

    /**
     * DATE 2019/12/13 11:40
     * DESC
     */
    private String getEmail(String email) {
        if (StringUtil.isNullOrEmpty(email)) {
            email = "";
        }
        return email;
    }

    /**
     * DATE 2019/12/13 11:40
     * DESC
     */
    private String getWechat(String wechat) {
        if (StringUtil.isNullOrEmpty(wechat)) {
            wechat = "";
        }
        return wechat;
    }
}
