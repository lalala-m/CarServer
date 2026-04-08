package com.project.demo.controller;

import com.project.demo.entity.OnlineMall;
import com.project.demo.service.OnlineMallService;
import com.alibaba.fastjson.JSON;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import com.project.demo.constant.FindConfig;
import com.project.demo.entity.Goods;
import com.project.demo.service.GoodsService;
import java.util.stream.Collectors;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;


/**
 * 网上商城：(OnlineMall)表控制层
 *
 */
@RestController
@RequestMapping("/online_mall")
public class OnlineMallController extends BaseController<OnlineMall, OnlineMallService> {

    /**
     * 网上商城对象
     */
    @Autowired
    public OnlineMallController(OnlineMallService service) {
        setService(service);
    }

    @Autowired
    private GoodsService goodsService;


    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        paramMap.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value instanceof String && ((String) value).isEmpty();
        });
        OnlineMall online_mall = new OnlineMall();
            online_mall.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                            online_mall.setCommodity_specifications(paramMap.get("commodity_specifications")==null?null:String.valueOf(paramMap.get("commodity_specifications")));
                        online_mall.setHits(paramMap.get("hits")==null?null:Integer.valueOf(String.valueOf(paramMap.get("hits"))));
        online_mall.setCart_title(paramMap.get("cart_title")==null?null:String.valueOf(paramMap.get("cart_title")));
        online_mall.setCart_img(paramMap.get("cart_img")==null?null:String.valueOf(paramMap.get("cart_img")));
        online_mall.setCart_description(paramMap.get("cart_description")==null?null:String.valueOf(paramMap.get("cart_description")));
        online_mall.setCart_price_ago(paramMap.get("cart_price_ago")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_price_ago"))));
        online_mall.setCart_price(paramMap.get("cart_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_price"))));
        online_mall.setCart_inventory(paramMap.get("cart_inventory")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_inventory"))));
        online_mall.setCart_type(paramMap.get("cart_type")==null?null:String.valueOf(paramMap.get("cart_type")));
        online_mall.setCart_content(paramMap.get("cart_content")==null?null:String.valueOf(paramMap.get("cart_content")));
        online_mall.setCart_img_1(paramMap.get("cart_img_1")==null?null:String.valueOf(paramMap.get("cart_img_1")));
        online_mall.setCart_img_2(paramMap.get("cart_img_2")==null?null:String.valueOf(paramMap.get("cart_img_2")));
        online_mall.setCart_img_3(paramMap.get("cart_img_3")==null?null:String.valueOf(paramMap.get("cart_img_3")));
        online_mall.setCart_img_4(paramMap.get("cart_img_4")==null?null:String.valueOf(paramMap.get("cart_img_4")));
        online_mall.setCart_img_5(paramMap.get("cart_img_5")==null?null:String.valueOf(paramMap.get("cart_img_5")));
            online_mall.setCart_integral(paramMap.get("cart_integral")==null?null:Integer.valueOf(String.valueOf(paramMap.get("cart_integral"))));
                online_mall.setListStatus(paramMap.get("list_status")==null?null:Integer.valueOf(String.valueOf(paramMap.get("list_status"))));
        online_mall.setSku(paramMap.get("sku")==null?null:String.valueOf(paramMap.get("sku")));
                                                                                                                                                    online_mall.setCreate_by(paramMap.get("create_by")==null?null:Integer.valueOf(String.valueOf(paramMap.get("create_by"))));
        this.addEntity(online_mall);
        System.out.println("网上商城新增成功");
        return success(1);
    }

    @PostMapping("/set")
    @Transactional
    public Map<String, Object> set(HttpServletRequest request) throws IOException {
        Map<String,String> queryMap = service.readQuery(request);
        Map<String,String> configMap = service.readConfig(request);
        Map<String,Object> paramMap = service.readBody(request.getReader());
        paramMap.entrySet().removeIf(entry -> {
            Object value = entry.getValue();
            return value instanceof String && ((String) value).isEmpty();
        });
        OnlineMall online_mall = new OnlineMall();
            online_mall.setBusiness_user(paramMap.get("business_user")==null?null:Integer.valueOf(String.valueOf(paramMap.get("business_user"))));
                    online_mall.setCommodity_specifications(paramMap.get("commodity_specifications")==null?null:String.valueOf(paramMap.get("commodity_specifications")));
                online_mall.setHits(paramMap.get("hits")==null?null:Integer.valueOf(String.valueOf(paramMap.get("hits"))));
        online_mall.setCart_title(paramMap.get("cart_title")==null?null:String.valueOf(paramMap.get("cart_title")));
        online_mall.setCart_img(paramMap.get("cart_img")==null?null:String.valueOf(paramMap.get("cart_img")));
        online_mall.setCart_description(paramMap.get("cart_description")==null?null:String.valueOf(paramMap.get("cart_description")));
        online_mall.setCart_price_ago(paramMap.get("cart_price_ago")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_price_ago"))));
        online_mall.setCart_price(paramMap.get("cart_price")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_price"))));
        online_mall.setCart_inventory(paramMap.get("cart_inventory")==null?null:Double.valueOf(String.valueOf(paramMap.get("cart_inventory"))));
        online_mall.setCart_type(paramMap.get("cart_type")==null?null:String.valueOf(paramMap.get("cart_type")));
        online_mall.setCart_content(paramMap.get("cart_content")==null?null:String.valueOf(paramMap.get("cart_content")));
        online_mall.setCart_img_1(paramMap.get("cart_img_1")==null?null:String.valueOf(paramMap.get("cart_img_1")));
        online_mall.setCart_img_2(paramMap.get("cart_img_2")==null?null:String.valueOf(paramMap.get("cart_img_2")));
        online_mall.setCart_img_3(paramMap.get("cart_img_3")==null?null:String.valueOf(paramMap.get("cart_img_3")));
        online_mall.setCart_img_4(paramMap.get("cart_img_4")==null?null:String.valueOf(paramMap.get("cart_img_4")));
        online_mall.setCart_img_5(paramMap.get("cart_img_5")==null?null:String.valueOf(paramMap.get("cart_img_5")));
            online_mall.setCart_integral(paramMap.get("cart_integral")==null?null:Integer.valueOf(String.valueOf(paramMap.get("cart_integral"))));
                online_mall.setListStatus(paramMap.get("list_status")==null?null:Integer.valueOf(String.valueOf(paramMap.get("list_status"))));
        online_mall.setSku(paramMap.get("sku")==null?null:String.valueOf(paramMap.get("sku")));
                                    this.setEntity(queryMap,configMap,online_mall);
        System.out.println("网上商城修改成功");
        return success(1);
    }
    private static final double PURCHASE_WEIGHT = 5.0;     // 购买权重
    private static final double COLLECT_WEIGHT = 3.0;      // 收藏权重
        private static final double CLICK_WEIGHT = 1.0;        // 点击权重
        private static final double COMMENT_WEIGHT = 2.0;      // 评论权重
    /**
     * 协同过滤算法
     */
    @RequestMapping("/get_hits_list")
    public Map<String, Object> getHits(HttpServletRequest request) {
        Map<String,String> paramMap = service.readQuery(request);
        if (paramMap.get("user_id")==null||paramMap.get("user_id").equals("")){
						Map<String,String> configMap = service.readConfig(request);
			configMap.computeIfAbsent(FindConfig.PAGE, k -> String.valueOf(1));
			configMap.computeIfAbsent(FindConfig.SIZE, k -> String.valueOf(1));
			configMap.put(FindConfig.ORDER_BY, "goods_id asc");
			List goodsList = goodsService.selectBaseList(goodsService.select(paramMap, configMap));
			Map<String, Object> map = new HashMap<>();
			map.put("list", goodsList);
			map.put("count", goodsList.size());
			return success(map);
			        }else{
            try {
                if (paramMap.get("examine_state")!=null && !paramMap.get("examine_state").equals("")){
                    paramMap.put("examine_state", URLDecoder.decode(paramMap.get("examine_state"), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
									//加权查询
			String hitsSource = buildCartWeightedSql(paramMap.get("user_id"));
												List<Map<String,Object>> hitsSourceList = goodsService.selectMapBaseList(hitsSource);
            if (hitsSourceList != null && hitsSourceList.size() > 0) {
                System.out.println("前5条行为数据: ");
                for (int i = 0; i < Math.min(5, hitsSourceList.size()); i++) {
                    Map<String, Object> item = hitsSourceList.get(i);
                    System.out.println("  商品ID: " + item.get("goods_id") + ", 加权分数: " + item.get("hits_count"));
                }
            }
			            //判断是否为空,代表当前用户暂无点击数据，那么取其他用户的最常点击
            if (hitsSourceList==null || hitsSourceList.size()<=0){
												hitsSource = buildCartWeightedSql(null);
																hitsSourceList = goodsService.selectMapBaseList(hitsSource);
				            }
						String table_name = "goods";
			String table_id = "goods_id";
			String type_name = "type";
			            //二次处理-如果依旧为空则取添加时间前12条数据
            if (hitsSourceList==null || hitsSourceList.size()<=0){
                String sql = "SELECT * FROM " + table_name;
                if (paramMap.get("examine_state")!=null && !paramMap.get("examine_state").equals("")){
                    sql = sql + " where examine_state = '"+paramMap.get("examine_state")+"'";
                }
                sql = sql + " ORDER BY create_time DESC LIMIT 0,12";
								List list = goodsService.selectBaseList(sql);
				                Map<String, Object> map=new HashMap<>();
                map.put("list",list);
                map.put("count",list.size());
                return success(map);
            }else {
                //对用户点击量进行排序取得最大值
                double max = 0.0;
                int maxSourceId = 0;
                for (Map<String,Object> map:hitsSourceList) {
                    double hitsCount = Double.parseDouble(String.valueOf(map.get("hits_count")));
                    if (hitsCount > max){
                        max = hitsCount;
												maxSourceId = Integer.parseInt(String.valueOf(map.get("goods_id")));
						                    }
                }
                //查询该用户点击最大值的种类
                String typeSql = "SELECT " + type_name + " FROM "  + table_name + " WHERE " + table_id + " = " + maxSourceId;
								List<Map<String,Object>> typeList = goodsService.selectMapBaseList(typeSql);
				                //如果为空则取添加时间前12条数据
                if (typeList==null || typeList.size()<=0){
                    String sql = "SELECT * FROM " + table_name;
                    if (paramMap.get("examine_state")!=null && !paramMap.get("examine_state").equals("")){
                        sql = sql + " where examine_state = '"+paramMap.get("examine_state")+"'";
                    }
                    sql = sql + " ORDER BY create_time DESC LIMIT 0,12";
					                    List list = goodsService.selectBaseList(sql);
					                    Map<String, Object> map=new HashMap<>();
                    map.put("list",list);
                    map.put("count",list.size());
                    return success(map);
                }else {
                    String typeName = String.valueOf(typeList.get(0).get(type_name));
                    //查询该种类的数据
                    String sql = "SELECT * FROM "  + table_name + " WHERE " + type_name + " = '"+typeName+"'" ;
                    if (paramMap.get("examine_state")!=null && !paramMap.get("examine_state").equals("")){
                        sql = sql + " and examine_state = '"+paramMap.get("examine_state")+"' ORDER BY create_time DESC limit 0,12";
                    }else {
                        sql = sql + " ORDER BY create_time DESC limit 0,12";
                    }
					                    List resultList = goodsService.selectBaseList(sql);
					                    //如果为空则取添加时间前12条数据
                    if (resultList==null || resultList.size()<=0){
                        sql = "SELECT * FROM " + table_name;
                        if (paramMap.get("examine_state")!=null && !paramMap.get("examine_state").equals("")){
                            sql = sql + " where examine_state = '"+paramMap.get("examine_state")+"'";
                        }
                        sql = sql + " ORDER BY create_time DESC LIMIT 0,12";
						                        List list = goodsService.selectBaseList(sql);
						                        Map<String, Object> map=new HashMap<>();
                        map.put("list",list);
                        map.put("count",list.size());
                        return success(map);
                    }else {
                        //对结果排序-冒泡排序
                        for (int i = resultList.size() - 1; i > 0; i--) {
                            // 是否发生交换
                            boolean swapped = false;
                            for (int j = 0; j < i; j++) {
																Goods leftMap = (Goods) resultList.get(j);
								Goods rightMap = (Goods) resultList.get(j + 1);
								if (leftMap.getHits()<rightMap.getHits()){
								    Goods temp = rightMap;
								    rightMap = leftMap;
								    leftMap = temp;
								    swapped = true;
								}
								                            }
                            if (!swapped) {
                                // 没有发生交换，则说明数组已有序,停止冒泡
                                break;
                            }
                        }
                        //判断是否有12条-如果不足则计算其他类型和用户当前点击最多的余弦相似度，依次往下取得数据
                        if (resultList.size()<12) {
                            String typeCosSql = "SELECT " + type_name + " FROM "  + table_name + " WHERE " + type_name + " <> '" + typeName + "' GROUP BY " + type_name;
														List<Map<String, Object>> typeCosList = goodsService.selectMapBaseList(typeCosSql);
							                            if (typeCosList != null && typeCosList.size() > 0) {
                                for (Map<String, Object> typeCos : typeCosList) {
                                    typeCos.put("cosSim", calculateCosineSimilarity(typeName, String.valueOf(typeCos.get(type_name))));
                                }
                                //冒泡根据相似度排序
                                for (int i = typeCosList.size() - 1; i > 0; i--) {
                                    // 是否发生交换
                                    boolean swapped = false;
                                    for (int j = 0; j < i; j++) {
                                        Map<String, Object> leftMap = typeCosList.get(j);
                                        Map<String, Object> rightMap = typeCosList.get(j + 1);
                                        if (Double.parseDouble(String.valueOf(leftMap.get("cosSim" ))) < Double.parseDouble(String.valueOf(rightMap.get("cosSim" )))) {
                                            Map<String, Object> temp = rightMap;
                                            rightMap = leftMap;
                                            leftMap = temp;
                                            swapped = true;
                                        }
                                    }
                                    if (!swapped) {
                                        // 没有发生交换，则说明数组已有序,停止冒泡
                                        break;
                                    }
                                }
                                //逐条加入数据
                                for (Map<String, Object> typeCos : typeCosList) {
                                    sql = "SELECT * FROM "  + table_name + " WHERE " + type_name + " = '" + String.valueOf(typeCos.get(type_name)) + "'";
                                    if (paramMap.get("examine_state")!=null && !paramMap.get("examine_state").equals("")){
                                        sql = sql + " and examine_state = '"+paramMap.get("examine_state")+"'";
                                    }
                                    sql = sql + " ORDER BY create_time DESC LIMIT 0," + (12 - resultList.size());
									                                    List list = goodsService.selectBaseList(sql);
									                                    if (list != null && list.size() > 0) {
                                        resultList.addAll(list);
                                    }
                                    if (resultList.size() >= 12) {
                                        break;
                                    }
                                }
                            }
                        }
                        Map<String, Object> map=new HashMap<>();
                        map.put("list",resultList);
                        map.put("count",resultList.size());
                        return success(map);
                    }
                }

            }
        }
    }

    /**
     * 计算余弦相似度
     */
    private double calculateCosineSimilarity(String text1, String text2) {
        // 将文本分词并转换为向量表示
        Map<Character, Integer> wordCount1 = getWordCount(text1);
        Map<Character, Integer> wordCount2 = getWordCount(text2);

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (Character word : wordCount1.keySet()) {
            int count1 = wordCount1.get(word);
            int count2 = wordCount2.getOrDefault(word, 0);
            dotProduct += count1 * count2;
            magnitude1 += Math.pow(count1, 2);
        }

        for (Integer count : wordCount2.values()) {
            magnitude2 += Math.pow(count, 2);
        }

        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (magnitude1 * magnitude2);
    }

    private static Map<Character, Integer> getWordCount(String text) {
        Map<Character, Integer> wordCount = new HashMap<>();
        for (char c : text.toCharArray()) {
            wordCount.put(c, wordCount.getOrDefault(c, 0) + 1);
        }
        return wordCount;
    }
    /**
    * 构建加权行为SQL
    * @param userId 用户ID（null表示全局）
    * @return 加权SQL
    */
    private String buildCartWeightedSql(String userId) {
        // 用户条件
        String userCondition = (userId != null && !userId.isEmpty()) ? 
            " AND user_id = " + userId : "";
        return "SELECT goods_id, " +
            "(COUNT(CASE WHEN source_type = 'order' THEN 1 END) * " + PURCHASE_WEIGHT + " + " +
            "COUNT(CASE WHEN source_type = 'collect' THEN 1 END) * " + COLLECT_WEIGHT + " + " +
                        "COUNT(CASE WHEN source_type = 'hits' THEN 1 END) * " + CLICK_WEIGHT + " + " +
                        "COUNT(CASE WHEN source_type = 'comment' THEN 1 END) * " + COMMENT_WEIGHT + ") as hits_count " +
            "FROM (" +
            // 购买行为
            "SELECT goods_id, 'order' as source_type FROM `order` WHERE 1=1" + userCondition + " " +
            "UNION ALL " +
            // 收藏行为
            "SELECT source_id as goods_id, 'collect' as source_type FROM collect " +
            "WHERE source_table = 'goods'" + userCondition + " " +
            "UNION ALL " +
                        // 点击行为
            "SELECT source_id as goods_id, 'hits' as source_type FROM hits " +
            "WHERE source_table = 'goods'" + userCondition + " " +
            "UNION ALL " +
                        // 评论行为
            "SELECT source_id as goods_id, 'comment' as source_type FROM comment " +
            "WHERE source_table = 'goods'" + userCondition + " " +
            ") t " +
            "GROUP BY goods_id " +
            "ORDER BY hits_count DESC";
    }


}
