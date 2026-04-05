package com.project.demo.controller;

import com.project.demo.entity.Schedule;
import com.project.demo.service.ScheduleService;

import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * 日程：(Schedule)表控制层
 *
 */
@RestController
@RequestMapping("schedule")
public class ScheduleController extends BaseController<Schedule, ScheduleService> {
    /**
     * 服务对象
     */
    @Autowired
    public ScheduleController(ScheduleService service) {
        setService(service);
    }

}


