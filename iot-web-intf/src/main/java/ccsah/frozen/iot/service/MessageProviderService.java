package ccsah.frozen.iot.service;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 14:14
 * DESC
 */
public interface MessageProviderService {

    void sendAlarmMessageDetail(String deviceCode,
                                String alarmContent,
                                String fireState,
                                int baseVoltage,
                                int signalIntensity,
                                long alarmTime);

}
