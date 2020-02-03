package ccsah.frozen.firecontrol.service;

public interface MessageAlarmProviderService {

    void sendAlarmMessage(String alarmRecordId,
                          String deviceCode,
                          String alarmContent,
                          String fireState,
                          int baseVoltage,
                          int signalIntensity,
                          long alarmTime);
}
