package ccsah.frozen.iot.controller;

import ccsah.frozen.iot.common.string.BaseString;
import ccsah.frozen.iot.service.ContactService;
import ccsfr.core.domain.PageOffsetRequest;
import ccsfr.core.web.BaseApiController;
import ccsfr.core.web.ResponseData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/11 11:39
 * DESC
 */
@Api(value = "contact", description = "联系人模块")
@RequestMapping("contact")
@RestController
public class ContactController extends BaseApiController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/add_contact", method = RequestMethod.POST)
    @ApiOperation(
            value = "添加联系人",
            notes = "vendor_id为对应的厂商id  \n" +
                    "contact_name为联系人姓名  \n" +
                    "gender为联系人性别  \n" +
                    "phone_number为联系人电话  \n" +
                    "email为联系人邮箱  \n" +
                    "wechat为联系人微信")
    public Object addContact(@RequestParam(value = "vendor_id") String vendorId,
                             @RequestParam(value = "contact_name") String contactName,
                             @RequestParam(value = "gender") String gender,
                             @RequestParam(value = "phone_number") String phoneNumber,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "wechat", required = false) String wechat) {
        return ResponseData.ok(contactService.addContact(vendorId, contactName, gender, phoneNumber, email, wechat));
    }

    @RequestMapping(value = "delete_contact", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除联系人")
    public Object deleteContact(@RequestParam("contact_id") String contactId) {
        return ResponseData.ok(contactService.deleteContact(contactId));
    }

    @RequestMapping(value = "/update_contact", method = RequestMethod.POST)
    @ApiOperation(
            value = "更新联系人",
            notes = "contact_id为需修改联系人的id  \n" +
                    "contact_name为保存的姓名  \n" +
                    "gender为保存的性别  \n" +
                    "phone_number为保存的联系电话  \n" +
                    "email为保存的邮箱，可为空，为空则表示删除之前的邮箱  \n" +
                    "wechat为保存的微信号可为空，为空则表示删除之前的微信号  \n")
    public Object updateContact(@RequestParam(value = "contact_id") String contactId,
                                @RequestParam(value = "contact_name") String contactName,
                                @RequestParam(value = "gender") String gender,
                                @RequestParam(value = "phone_number") String phoneNumber,
                                @RequestParam(value = "email", required = false) String email,
                                @RequestParam(value = "wechat", required = false) String wechat) {
        return ResponseData.ok(contactService.updateContact(contactId, contactName, gender, phoneNumber, email, wechat));
    }

    @RequestMapping(value = "/list_contact_by_parameter", method = RequestMethod.GET)
    @ApiOperation(
            value = "可选参数查询，分页",
            notes = "contact_name为联系人姓名  \n" +
                    "gender为联系人性别  \n" +
                    "phone_number为联系人电话  \n" +
                    "email为联系人邮箱  \n" +
                    "wechat_number为联系人微信  \n" +
                    "start_query_time为查询的开始时间  \n" +
                    "end_query_time为查询的结束时间  \n" +
                    "offset为偏移量，从0开始，默认为0  \n" +
                    "limit为从偏移量开始的记录的条数，默认为5")
    public Object listContactByParameter(@RequestParam(value = "contact_name", required = false) String contactName,
                                         @RequestParam(value = "gender", required = false) String gender,
                                         @RequestParam(value = "phone_number", required = false) String phoneNumber,
                                         @RequestParam(value = "email", required = false) String email,
                                         @RequestParam(value = "wechat", required = false) String wechat,
                                         @RequestParam(value = "start_query_time", required = false, defaultValue = "0") long startQueryTime,
                                         @RequestParam(value = "end_query_time", required = false, defaultValue = "0") long endQueryTime,
                                         @RequestParam(value = "offset", defaultValue = BaseString.defaultOffset) int offset,
                                         @RequestParam(value = "limit", defaultValue = BaseString.defaultLimit) int limit) {
        Pageable pageRequest = PageOffsetRequest.getPageableByOffset(offset, limit, new Sort(Sort.Direction.DESC, "contactName", "ctime"));
        return ResponseData.ok(contactService.listContactByParameter(
                contactName,
                gender,
                phoneNumber,
                email,
                wechat,
                startQueryTime,
                endQueryTime,
                pageRequest));
    }
}
