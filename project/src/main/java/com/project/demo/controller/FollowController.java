package com.project.demo.controller;

import com.project.demo.entity.Follow;
import com.project.demo.service.FollowService;

import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * 关注用户
 *
 */
@RestController
@RequestMapping("follow")
public class FollowController extends BaseController<Follow, FollowService> {
    /**
     * 服务对象
     */
    @Autowired
    public FollowController(FollowService service) {
        setService(service);
    }

}


