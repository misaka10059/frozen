package ccsah.frozen.firecontrol.domain.entity;

import ccsah.frozen.firecontrol.domain.knowledge.ConfirmedType;
import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/18 16:00
 * DESC
 */
@Entity
@Table
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class AlarmRecord extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Device device;

    @Column
    private String alarmContent;

    @Column
    private Timestamp alarmTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "alarm_receiver_id")
    private Employee alarmReceiver;

    @Column
    private Timestamp alarmReceiveTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "confirmed_person_id")
    private Employee confirmedPerson;

    @Column
    @Enumerated(EnumType.STRING)
    private ConfirmedType confirmedType;

    @Column
    private String confirmedResult;

    @Column
    private Timestamp confirmedTime;

    @Column
    private String eventScene;

    public static AlarmRecord create(Device device,
                                     String alarmContent,
                                     Timestamp alarmTime,
                                     Employee alarmReceiver,
                                     Timestamp alarmReceiveTime,
                                     Employee confirmedPerson,
                                     ConfirmedType confirmedType,
                                     String confirmedResult,
                                     Timestamp confirmedTime,
                                     String eventScene) {
        AlarmRecord alarmRecord = new AlarmRecord();
        alarmRecord.setDevice(device);
        alarmRecord.setAlarmContent(alarmContent);
        alarmRecord.setAlarmTime(alarmTime);
        alarmRecord.setAlarmReceiver(alarmReceiver);
        alarmRecord.setAlarmReceiveTime(alarmReceiveTime);
        alarmRecord.setConfirmedPerson(confirmedPerson);
        alarmRecord.setConfirmedType(confirmedType);
        alarmRecord.setConfirmedResult(confirmedResult);
        alarmRecord.setConfirmedTime(confirmedTime);
        alarmRecord.setEventScene(eventScene);
        session().persist(alarmRecord);
        return alarmRecord;
    }

}
