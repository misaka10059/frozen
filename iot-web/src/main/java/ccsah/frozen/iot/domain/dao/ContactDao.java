package ccsah.frozen.iot.domain.dao;

import ccsah.frozen.iot.domain.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDao extends JpaRepository<Contact, String> {

    Contact findContactByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    Contact findContactByIdAndIsDeletedFalse(String contactId);

    Page<Contact> findAll(Specification<Contact> specification, Pageable pageRequest);

}
