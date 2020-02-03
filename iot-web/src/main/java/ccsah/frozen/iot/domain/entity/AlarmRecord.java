package ccsah.frozen.iot.domain.entity;

import ccsfr.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/5 11:02
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

    @Column(name = "alarm_time")
    private Timestamp alarmTime;

    public static AlarmRecord create(Timestamp alarmTime, String alarmContent, Device device) {
        AlarmRecord alarmRecord = new AlarmRecord();
        alarmRecord.setAlarmTime(alarmTime);
        alarmRecord.setAlarmContent(alarmContent);
        alarmRecord.setDevice(device);
        session().persist(alarmRecord);
        return alarmRecord;
    }
}
