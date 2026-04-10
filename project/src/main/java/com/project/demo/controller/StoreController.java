package com.project.demo.controller;

import com.project.demo.service.StoreService;
import com.project.demo.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 4S店/店面管理控制器
 */
@RestController
@RequestMapping("api/store")
@CrossOrigin
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * 获取店面列表
     */
    @GetMapping("get_list")
    public Map<String, Object> get_list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "store_name", required = false) String storeName,
            @RequestParam(value = "orderby", required = false) String orderby,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<Store> list = storeService.getAllStores();
            result.put("result", Map.of("list", list, "count", list.size()));
            result.put("error", null);
        } catch (Exception e) {
            result.put("result", null);
            result.put("error", Map.of("message", e.getMessage()));
        }
        return result;
    }

    /**
     * 获取单个店面信息
     */
    @GetMapping("get_obj")
    public Map<String, Object> get_obj(@RequestParam("store_id") Integer storeId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Store store = storeService.getById(storeId);
            result.put("result", Map.of("obj", store));
            result.put("error", null);
        } catch (Exception e) {
            result.put("result", null);
            result.put("error", Map.of("message", e.getMessage()));
        }
        return result;
    }

    /**
     * 添加店面
     */
    @PostMapping("add")
    public Map<String, Object> add(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Store store = new Store();
            if (params.containsKey("store_name")) {
                store.setStoreName((String) params.get("store_name"));
            }
            if (params.containsKey("store_address")) {
                store.setStoreAddress((String) params.get("store_address"));
            }
            if (params.containsKey("store_phone")) {
                store.setStorePhone((String) params.get("store_phone"));
            }
            if (params.containsKey("store_manager")) {
                store.setStoreManager((String) params.get("store_manager"));
            }
            store.setExamineState("已通过"); // 默认已通过
            
            storeService.save(store);
            result.put("result", store.getStoreId());
            result.put("error", null);
        } catch (Exception e) {
            result.put("result", null);
            result.put("error", Map.of("message", e.getMessage()));
        }
        return result;
    }

    /**
     * 修改店面信息
     */
    @PostMapping("set")
    public Map<String, Object> set(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Integer storeId = params.containsKey("store_id") ? 
                (params.get("store_id") instanceof Integer ? 
                    (Integer) params.get("store_id") : 
                    Integer.parseInt(params.get("store_id").toString())) : null;
            
            if (storeId == null) {
                throw new Exception("store_id不能为空");
            }
            
            Store store = storeService.getById(storeId);
            if (store == null) {
                throw new Exception("店面不存在");
            }
            
            if (params.containsKey("store_name")) {
                store.setStoreName((String) params.get("store_name"));
            }
            if (params.containsKey("store_address")) {
                store.setStoreAddress((String) params.get("store_address"));
            }
            if (params.containsKey("store_phone")) {
                store.setStorePhone((String) params.get("store_phone"));
            }
            if (params.containsKey("store_manager")) {
                store.setStoreManager((String) params.get("store_manager"));
            }
            if (params.containsKey("examine_state")) {
                store.setExamineState((String) params.get("examine_state"));
            }
            
            storeService.update(store);
            result.put("result", true);
            result.put("error", null);
        } catch (Exception e) {
            result.put("result", null);
            result.put("error", Map.of("message", e.getMessage()));
        }
        return result;
    }

    /**
     * 删除店面
     */
    @GetMapping("del")
    public Map<String, Object> del(@RequestParam("store_id") Integer storeId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            storeService.delete(storeId);
            result.put("result", true);
            result.put("error", null);
        } catch (Exception e) {
            result.put("result", null);
            result.put("error", Map.of("message", e.getMessage()));
        }
        return result;
    }
}
