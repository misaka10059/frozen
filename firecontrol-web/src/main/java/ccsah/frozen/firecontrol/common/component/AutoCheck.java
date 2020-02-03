package ccsah.frozen.firecontrol.common.component;

import ccsah.frozen.firecontrol.service.InspectionTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 11:54
 * DESC
 */
@EnableScheduling
@Component
public class AutoCheck {

    @Autowired
    private InspectionTaskService inspectionTaskService;

    /*
     * 秒 分 时 天 月 年
     */

    /**
     * DATE 2019/12/26 23:15
     * DESC 每隔30秒根据巡检模板生成巡检任务
     */
    @Scheduled(cron = "0 * * * * *")
    public void testDaily() {
        inspectionTaskService.check();
    }
}
