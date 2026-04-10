package com.project.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.demo.dao.StoreMapper;
import com.project.demo.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 4S店/店面：(Store)表服务接口
 */
@Service
public class StoreService {

    @Autowired
    private StoreMapper storeMapper;

    /**
     * 根据店面名称获取店面ID
     */
    public Integer getIdByName(String storeName) {
        if (storeName == null || storeName.trim().isEmpty()) {
            return 0;
        }
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.eq("store_name", storeName);
        List<Store> list = storeMapper.selectList(wrapper);
        if (list != null && !list.isEmpty()) {
            return list.get(0).getStoreId();
        }
        return 0;
    }

    /**
     * 获取所有店面列表
     */
    public List<Store> getAllStores() {
        return storeMapper.selectList(null);
    }

    /**
     * 根据店面ID获取店面信息
     */
    public Store getById(Integer storeId) {
        if (storeId == null || storeId <= 0) {
            return null;
        }
        return storeMapper.selectById(storeId);
    }

    /**
     * 保存店面信息
     */
    public void save(Store store) {
        storeMapper.insert(store);
    }

    /**
     * 更新店面信息
     */
    public void update(Store store) {
        storeMapper.updateById(store);
    }

    /**
     * 删除店面
     */
    public void delete(Integer storeId) {
        storeMapper.deleteById(storeId);
    }
}
