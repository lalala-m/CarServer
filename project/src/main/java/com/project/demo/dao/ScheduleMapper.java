package com.project.demo.dao;

import java.util.List;

import com.project.demo.dao.base.BaseMapper;
import com.project.demo.entity.Schedule;

/**
 * 日程：Mapper接口
 *
 */
public interface ScheduleMapper extends BaseMapper<Schedule>
{
    /**
     * 查询日程：
     *
     * @param scheduleId 日程：ID
     * @return 日程：
     */
    Schedule selectScheduleById(Integer scheduleId);

    /**
     * 查询日程：列表
     *
     * @param score 日程：
     * @return 日程：集合
     */
    List<Schedule> selectScheduleList(Schedule schedule);

    /**
     * 新增日程：
     *
     * @param schedule 日程：
     * @return 结果
     */
    int insertSchedule(Schedule schedule);

    /**
     * 修改日程：
     *
     * @param schedule 日程：
     * @return 结果
     */
    int updateSchedule(Schedule schedule);

    /**
     * 删除日程：
     *
     * @param scheduleId 日程：ID
     * @return 结果
     */
    int deleteScheduleById(Integer ScheduleId);

    /**
     * 批量删除日程：
     *
     * @param scheduleIds 需要删除的数据ID
     * @return 结果
     */
    int deleteScheduleByIds(Integer[] ScheduleIds);
}
