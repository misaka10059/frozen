package ccsah.frozen.firecontrol.service;

public interface MessageProviderService {

    void sendInspectionMessage(String deviceId,
                               String deviceCode,
                               String pointState,
                               String alarmAreaId,
                               String alarmAreaName);

}
