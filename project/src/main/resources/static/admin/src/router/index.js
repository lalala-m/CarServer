import Vue from 'vue';
import VueRouter from 'vue-router';
import index from '../views/index.vue';
import login from '../views/login.vue';
import forgot from '../views/forgot.vue';
Vue.use(VueRouter)

const routes = [
    // 主页
    {
        path: '/',
        name: 'index',
        component: index,
        meta: {
            index: 0,
            title: '首页'
        }
    },

    // 登录
    {
        path: '/login',
        name: 'login',
        component: login,
        meta: {
            index: 0,
            title: '登录'
        }
    },
    {
        path: "/filePreview",
        name: "filePreview",
        component: () => import("../views/filePreview/filePreview.vue"),
        meta: {
            title: "文件预览",
        },
    },
                                                                                                                
    
	    
    // 忘记密码
    {
        path: '/forgot',
        name: "forgot",
        component: forgot,
        meta: {
            index: 0,
            title: '忘记密码'
        }
    },

    // 修改密码
    {
        path: '/user/password',
        name: "password",
        component: () => import("../views/user/password.vue"),
        meta: {
            index: 0,
            title: '修改密码'
        }
    },

    
    
                // 轮播图路由
        {
            path: '/slides/table',
            name: 'slides_table',
            component: () => import('../views/slides/table.vue'),
            meta: {
                index: 0,
                title: '轮播图列表'
            }
        },
        {
            path: '/slides/view',
            name: 'slides_view',
            component: () => import('../views/slides/view.vue'),
            meta: {
                index: 0,
                title: '轮播图详情'
            }
        },
                    // 文章路由
            {
                path: '/article/table',
                name: 'article_table',
                component: () => import('../views/article/table.vue'),
                meta: {
                    index: 0,
                    title: '汽车资讯列表'
                }
            },
            {
                path: '/article/view',
                name: 'article_view',
                component: () => import('../views/article/view.vue'),
                meta: {
                    index: 0,
                    title: '汽车资讯详情'
                }
            },

            // 文章分类路由
            {
                path: '/article_type/table',
                name: 'article_type_table',
                component: () => import('../views/article_type/table.vue'),
                meta: {
                    index: 0,
                    title: '汽车资讯分类列表'
                }
            },
            {
                path: '/article_type/view',
                name: 'article_type_view',
                component: () => import('../views/article_type/view.vue'),
                meta: {
                    index: 0,
                    title: '汽车资讯分类详情'
                }
            },
                            
    
            // 论坛路由
        {
            path: '/forum/table',
            name: 'forum_table',
            component: () => import('../views/forum/table.vue'),
            meta: {
                index: 0,
                title: '论坛列表'
            }
        },
        {
            path: '/forum/view',
            name: 'forum_view',
            component: () => import('../views/forum/view.vue'),
            meta: {
                index: 0,
                title: '论坛详情'
            }
        },

        // 论坛分类路由
        {
            path: '/forum_type/table',
            name: 'forum_type_table',
            component: () => import('../views/forum_type/table.vue'),
            meta: {
                index: 0,
                title: '论坛分类列表'
            }
        },
        {
            path: '/forum_type/view',
            name: 'forum_type_view',
            component: () => import('../views/forum_type/view.vue'),
            meta: {
                index: 0,
                title: '论坛分类详情'
            }
        },
    
            // 公告路由
        {
            path: '/notice/table',
            name: 'notice_table',
            component: () => import('../views/notice/table.vue'),
            meta: {
                index: 0,
                title: '通知公告列表'
            }
        },
        {
            path: '/notice/view',
            name: 'notice_view',
            component: () => import('../views/notice/view.vue'),
            meta: {
                index: 0,
                title: '通知公告详情'
            }
        },
            	            {
            path: '/goods_type/table',
            name: 'goods_type_table',
            component: () => import('../views/goods_type/table.vue'),
            meta: {
                index: 0,
                title: '商品分类列表'
            }
        },
        {
            path: '/goods_type/view',
            name: 'goods_type_view',
            component: () => import('../views/goods_type/view.vue'),
            meta: {
                index: 0,
                title: '商品分类详情'
            }
        },

        // 订单路由
        {
            path: '/order/table',
            name: 'order_table',
            component: () => import('../views/order/table.vue'),
            meta: {
                index: 0,
                title: '订单列表'
            }
        },
        {
            path: '/order/view',
            name: 'order_view',
            component: () => import('../views/order/view.vue'),
            meta: {
                index: 0,
                title: '订单详情'
            }
        },
                // 订单配送路由
        {
            path: '/logistics_delivery/table',
            name: 'logistics_delivery_table',
            component: () => import('../views/logistics_delivery/table.vue'),
            meta: {
                index: 0,
                title: '订单配送列表'
            }
        },
        {
            path: '/logistics_delivery/view',
            name: 'logistics_delivery_view',
            component: () => import('../views/logistics_delivery/view.vue'),
            meta: {
                index: 0,
                title: '订单配送详情'
            }
        },
                                
            // 评论路由
        {
            path: '/comment/table',
            name: 'comment_table',
            component: () => import('../views/comment/table.vue'),
            meta: {
                index: 0,
                title: '评论列表'
            }
        },
        {
            path: '/comment/view',
            name: 'comment_view',
            component: () => import('../views/comment/view.vue'),
            meta: {
                index: 0,
                title: '评论详情'
            }
        },
        	            // 车主用户路由
        {
            path: '/owner_user/table',
            name: 'owner_user_table',
            component: () => import('../views/owner_user/table.vue'),
            meta: {
                index: 0,
                title: '车主用户列表'
            }
        },
        {
            path: '/owner_user/view',
            name: 'owner_user_view',
            component: () => import('../views/owner_user/view.vue'),
            meta: {
                index: 0,
                title: '车主用户详情'
            }
        },
		                				            // 商家用户路由
        {
            path: '/business_user/table',
            name: 'business_user_table',
            component: () => import('../views/business_user/table.vue'),
            meta: {
                index: 0,
                title: '商家用户列表'
            }
        },
        {
            path: '/business_user/view',
            name: 'business_user_view',
            component: () => import('../views/business_user/view.vue'),
            meta: {
                index: 0,
                title: '商家用户详情'
            }
        },
		                				            // 财务用户路由
        {
            path: '/financial_user/table',
            name: 'financial_user_table',
            component: () => import('../views/financial_user/table.vue'),
            meta: {
                index: 0,
                title: '财务用户列表'
            }
        },
        {
            path: '/financial_user/view',
            name: 'financial_user_view',
            component: () => import('../views/financial_user/view.vue'),
            meta: {
                index: 0,
                title: '财务用户详情'
            }
        },
		                				            // 经理用户路由
        {
            path: '/manager_user/table',
            name: 'manager_user_table',
            component: () => import('../views/manager_user/table.vue'),
            meta: {
                index: 0,
                title: '经理用户列表'
            }
        },
        {
            path: '/manager_user/view',
            name: 'manager_user_view',
            component: () => import('../views/manager_user/view.vue'),
            meta: {
                index: 0,
                title: '经理用户详情'
            }
        },
		                				            // 销售经理路由
        {
            path: '/sales_manager/table',
            name: 'sales_manager_table',
            component: () => import('../views/sales_manager/table.vue'),
            meta: {
                index: 0,
                title: '销售经理列表'
            }
        },
        {
            path: '/sales_manager/view',
            name: 'sales_manager_view',
            component: () => import('../views/sales_manager/view.vue'),
            meta: {
                index: 0,
                title: '销售经理详情'
            }
        },
		                				            // 车辆信息路由
        {
            path: '/vehicle_information/table',
            name: 'vehicle_information_table',
            component: () => import('../views/vehicle_information/table.vue'),
            meta: {
                index: 0,
                title: '车辆信息列表'
            }
        },
        {
            path: '/vehicle_information/view',
            name: 'vehicle_information_view',
            component: () => import('../views/vehicle_information/view.vue'),
            meta: {
                index: 0,
                title: '车辆信息详情'
            }
        },
		                				            // 车型分类路由
        {
            path: '/vehicle_class_nameification/table',
            name: 'vehicle_class_nameification_table',
            component: () => import('../views/vehicle_class_nameification/table.vue'),
            meta: {
                index: 0,
                title: '车型分类列表'
            }
        },
        {
            path: '/vehicle_class_nameification/view',
            name: 'vehicle_class_nameification_view',
            component: () => import('../views/vehicle_class_nameification/view.vue'),
            meta: {
                index: 0,
                title: '车型分类详情'
            }
        },
		                				            // 品牌分类路由
        {
            path: '/brand_class_nameification/table',
            name: 'brand_class_nameification_table',
            component: () => import('../views/brand_class_nameification/table.vue'),
            meta: {
                index: 0,
                title: '品牌分类列表'
            }
        },
        {
            path: '/brand_class_nameification/view',
            name: 'brand_class_nameification_view',
            component: () => import('../views/brand_class_nameification/view.vue'),
            meta: {
                index: 0,
                title: '品牌分类详情'
            }
        },
		                				            // 汽车信息路由
        {
            path: '/car_information/table',
            name: 'car_information_table',
            component: () => import('../views/car_information/table.vue'),
            meta: {
                index: 0,
                title: '汽车信息列表'
            }
        },
        {
            path: '/car_information/view',
            name: 'car_information_view',
            component: () => import('../views/car_information/view.vue'),
            meta: {
                index: 0,
                title: '汽车信息详情'
            }
        },
		                				            // 试驾记录路由
        {
            path: '/test_drive_record/table',
            name: 'test_drive_record_table',
            component: () => import('../views/test_drive_record/table.vue'),
            meta: {
                index: 0,
                title: '试驾记录列表'
            }
        },
        {
            path: '/test_drive_record/view',
            name: 'test_drive_record_view',
            component: () => import('../views/test_drive_record/view.vue'),
            meta: {
                index: 0,
                title: '试驾记录详情'
            }
        },
		                				            // 购买订单路由
        {
            path: '/purchase_order/table',
            name: 'purchase_order_table',
            component: () => import('../views/purchase_order/table.vue'),
            meta: {
                index: 0,
                title: '购买订单列表'
            }
        },
        {
            path: '/purchase_order/view',
            name: 'purchase_order_view',
            component: () => import('../views/purchase_order/view.vue'),
            meta: {
                index: 0,
                title: '购买订单详情'
            }
        },
		                				            // 维修养护路由
        {
            path: '/maintenance/table',
            name: 'maintenance_table',
            component: () => import('../views/maintenance/table.vue'),
            meta: {
                index: 0,
                title: '维修养护列表'
            }
        },
        {
            path: '/maintenance/view',
            name: 'maintenance_view',
            component: () => import('../views/maintenance/view.vue'),
            meta: {
                index: 0,
                title: '维修养护详情'
            }
        },
		                				            // 保养订单路由
        {
            path: '/maintenance_order/table',
            name: 'maintenance_order_table',
            component: () => import('../views/maintenance_order/table.vue'),
            meta: {
                index: 0,
                title: '保养订单列表'
            }
        },
        {
            path: '/maintenance_order/view',
            name: 'maintenance_order_view',
            component: () => import('../views/maintenance_order/view.vue'),
            meta: {
                index: 0,
                title: '保养订单详情'
            }
        },
		                				            // 卡券类型路由
        {
            path: '/card_type/table',
            name: 'card_type_table',
            component: () => import('../views/card_type/table.vue'),
            meta: {
                index: 0,
                title: '卡券类型列表'
            }
        },
        {
            path: '/card_type/view',
            name: 'card_type_view',
            component: () => import('../views/card_type/view.vue'),
            meta: {
                index: 0,
                title: '卡券类型详情'
            }
        },
		                				            // 发放卡券路由
        {
            path: '/issuing_card_voucher/table',
            name: 'issuing_card_voucher_table',
            component: () => import('../views/issuing_card_voucher/table.vue'),
            meta: {
                index: 0,
                title: '发放卡券列表'
            }
        },
        {
            path: '/issuing_card_voucher/view',
            name: 'issuing_card_voucher_view',
            component: () => import('../views/issuing_card_voucher/view.vue'),
            meta: {
                index: 0,
                title: '发放卡券详情'
            }
        },
		                				            // 核销信息路由
        {
            path: '/write_off_information/table',
            name: 'write_off_information_table',
            component: () => import('../views/write_off_information/table.vue'),
            meta: {
                index: 0,
                title: '核销信息列表'
            }
        },
        {
            path: '/write_off_information/view',
            name: 'write_off_information_view',
            component: () => import('../views/write_off_information/view.vue'),
            meta: {
                index: 0,
                title: '核销信息详情'
            }
        },
		                				            // 救援记录路由
        {
            path: '/rescue_record/table',
            name: 'rescue_record_table',
            component: () => import('../views/rescue_record/table.vue'),
            meta: {
                index: 0,
                title: '救援记录列表'
            }
        },
        {
            path: '/rescue_record/view',
            name: 'rescue_record_view',
            component: () => import('../views/rescue_record/view.vue'),
            meta: {
                index: 0,
                title: '救援记录详情'
            }
        },
		                				            // 服务分类路由
        {
            path: '/service_class_nameification/table',
            name: 'service_class_nameification_table',
            component: () => import('../views/service_class_nameification/table.vue'),
            meta: {
                index: 0,
                title: '服务分类列表'
            }
        },
        {
            path: '/service_class_nameification/view',
            name: 'service_class_nameification_view',
            component: () => import('../views/service_class_nameification/view.vue'),
            meta: {
                index: 0,
                title: '服务分类详情'
            }
        },
		                				            // 时段分类路由
        {
            path: '/period_class_nameification/table',
            name: 'period_class_nameification_table',
            component: () => import('../views/period_class_nameification/table.vue'),
            meta: {
                index: 0,
                title: '时段分类列表'
            }
        },
        {
            path: '/period_class_nameification/view',
            name: 'period_class_nameification_view',
            component: () => import('../views/period_class_nameification/view.vue'),
            meta: {
                index: 0,
                title: '时段分类详情'
            }
        },
		                				            // 精品中心路由
        {
            path: '/boutique_center/table',
            name: 'boutique_center_table',
            component: () => import('../views/boutique_center/table.vue'),
            meta: {
                index: 0,
                title: '精品中心列表'
            }
        },
        {
            path: '/boutique_center/view',
            name: 'boutique_center_view',
            component: () => import('../views/boutique_center/view.vue'),
            meta: {
                index: 0,
                title: '精品中心详情'
            }
        },
		                				            // 预约记录路由
        {
            path: '/reservation_record/table',
            name: 'reservation_record_table',
            component: () => import('../views/reservation_record/table.vue'),
            meta: {
                index: 0,
                title: '预约记录列表'
            }
        },
        {
            path: '/reservation_record/view',
            name: 'reservation_record_view',
            component: () => import('../views/reservation_record/view.vue'),
            meta: {
                index: 0,
                title: '预约记录详情'
            }
        },
		                				            // 供应厂商路由
        {
            path: '/supplier/table',
            name: 'supplier_table',
            component: () => import('../views/supplier/table.vue'),
            meta: {
                index: 0,
                title: '供应厂商列表'
            }
        },
        {
            path: '/supplier/view',
            name: 'supplier_view',
            component: () => import('../views/supplier/view.vue'),
            meta: {
                index: 0,
                title: '供应厂商详情'
            }
        },
		                				            // 仓库信息路由
        {
            path: '/warehouse_information/table',
            name: 'warehouse_information_table',
            component: () => import('../views/warehouse_information/table.vue'),
            meta: {
                index: 0,
                title: '仓库信息列表'
            }
        },
        {
            path: '/warehouse_information/view',
            name: 'warehouse_information_view',
            component: () => import('../views/warehouse_information/view.vue'),
            meta: {
                index: 0,
                title: '仓库信息详情'
            }
        },
		                				            // 库存信息路由
        {
            path: '/inventory_information/table',
            name: 'inventory_information_table',
            component: () => import('../views/inventory_information/table.vue'),
            meta: {
                index: 0,
                title: '库存信息列表'
            }
        },
        {
            path: '/inventory_information/view',
            name: 'inventory_information_view',
            component: () => import('../views/inventory_information/view.vue'),
            meta: {
                index: 0,
                title: '库存信息详情'
            }
        },
		                				            // 入库记录路由
        {
            path: '/inbound_record/table',
            name: 'inbound_record_table',
            component: () => import('../views/inbound_record/table.vue'),
            meta: {
                index: 0,
                title: '入库记录列表'
            }
        },
        {
            path: '/inbound_record/view',
            name: 'inbound_record_view',
            component: () => import('../views/inbound_record/view.vue'),
            meta: {
                index: 0,
                title: '入库记录详情'
            }
        },
		                				            // 出库记录路由
        {
            path: '/outbound_record/table',
            name: 'outbound_record_table',
            component: () => import('../views/outbound_record/table.vue'),
            meta: {
                index: 0,
                title: '出库记录列表'
            }
        },
        {
            path: '/outbound_record/view',
            name: 'outbound_record_view',
            component: () => import('../views/outbound_record/view.vue'),
            meta: {
                index: 0,
                title: '出库记录详情'
            }
        },
		                				            // 网上商城路由
        {
            path: '/online_mall/table',
            name: 'online_mall_table',
            component: () => import('../views/online_mall/table.vue'),
            meta: {
                index: 0,
                title: '网上商城列表'
            }
        },
        {
            path: '/online_mall/view',
            name: 'online_mall_view',
            component: () => import('../views/online_mall/view.vue'),
            meta: {
                index: 0,
                title: '网上商城详情'
            }
        },
		                				            // 员工绩效路由
        {
            path: '/employee_performance/table',
            name: 'employee_performance_table',
            component: () => import('../views/employee_performance/table.vue'),
            meta: {
                index: 0,
                title: '员工绩效列表'
            }
        },
        {
            path: '/employee_performance/view',
            name: 'employee_performance_view',
            component: () => import('../views/employee_performance/view.vue'),
            meta: {
                index: 0,
                title: '员工绩效详情'
            }
        },
		                				    	        // 用户路由
    {
        path: '/user/table',
        name: 'user_table',
        component: () => import('../views/user/table.vue'),
        meta: {
            index: 0,
            title: '用户列表'
        }
    },
    {
        path: '/user/view',
        name: 'user_view',
        component: () => import('../views/user/view.vue'),
        meta: {
            index: 0,
            title: '用户详情'
        }
    },
    {
        path: '/user/info',
        name: 'user_info',
        component: () => import('../views/user/info.vue'),
        meta: {
            index: 0,
            title: '个人信息'
        }
    },
    // 用户组路由
    {
        path: '/user_group/table',
        name: 'user_group_table',
        component: () => import('../views/user_group/table.vue'),
        meta: {
            index: 0,
            title: '用户组列表'
        }
    },
    {
        path: '/user_group/view',
        name: 'user_group_view',
        component: () => import('../views/user_group/view.vue'),
        meta: {
            index: 0,
            title: '用户组详情'
        }
    }
]

const router = new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    let token = to.query.token;
    if (token) {
        $.db.set("token", token, 120);
    }
    next();
})

router.afterEach((to, from, next) => {
    let title = "e行家车友会服务系统-admin";
    document.title = title;
})

// 解决重载报错
const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return VueRouterPush.call(this, location).catch((err) => err);
};

export default router
