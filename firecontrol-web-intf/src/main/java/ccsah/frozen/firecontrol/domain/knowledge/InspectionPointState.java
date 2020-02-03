package ccsah.frozen.firecontrol.domain.knowledge;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/26 9:20
 * DESC 巡检点状态
 */
public enum InspectionPointState {
    NOT_INSPECTED,       //未巡检
    INSPECTED_OK,       //已巡检，正常
    INSPECTED_PROBLEM   //已巡检，有异常
}
