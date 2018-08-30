package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dazhen（1299501979@qq.com）
 * @create 2018-07-25 16:40
 * @desc
 **/

public class MyTemplate {

    //库存不足提醒  管理员
    public void test10(){
        LackGoodsMsg msg = new LackGoodsMsg();
        msg.setGoodsId("id");//商品编码
        msg.setGoodName("name");//商品名称
        msg.setGoodsCount("count");//库存数量
        List<UserThirdInfo> clerks = AppBeanInjector.userThirdInfoService.findAllClerks();
        if (clerks.size()!=0){
            for (UserThirdInfo thirdInfo:clerks) {
                msg.setTouser(thirdInfo.getWxOpenId());
                AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
            }
        }
    }
    //订单提交成功给管理员
    public void test1(){
        AddOrderSuccessForClerkMsg msg = new AddOrderSuccessForClerkMsg();
        Map<String,String> map = new HashMap<>();
        map.put("order_number","订单编号(商品明细)");
        map.put("order_time","下单时间");
        map.put("receipt_id","配送地址");
        map.put("contacts","联系人");
        map.put("status","付款状态");
        msg.setContentMsg(map);
        List<UserThirdInfo> clerks = AppBeanInjector.userThirdInfoService.findAllClerks();
        if (clerks.size()!=0){
            for (UserThirdInfo thirdInfo:clerks) {
                msg.setTouser(thirdInfo.getWxOpenId());
                AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
            }
        }
    }
    //订单提交成功给用户
    public void test2(){
        PaySuccessForCustomer msg = new PaySuccessForCustomer();
        Map<String,String> map = new HashMap<>();
        map.put("goods","订单商品");
        map.put("order_number","订单编号");
        map.put("paid_price","订单金额");
        map.put("paid_time","支付时间");
        map.put("receipt_id","收货信息");
        msg.setContentMsg(map);
        msg.setTouser("获取当前的用户的oppenid");
        AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
    }
    //订单提交失败后给管理员
    public void test3(){
        AddOrderFailToClerksMsg msg = new AddOrderFailToClerksMsg();
        Map<String,String> map = new HashMap<>();
        map.put("order_number","订单编号");
        map.put("paid_price","订单金额");
        map.put("created","创建时间");
        msg.setContentMsg(map);
        List<UserThirdInfo> clerks = AppBeanInjector.userThirdInfoService.findAllClerks();
        if (clerks.size()!=0){
            for (UserThirdInfo thirdInfo:clerks) {
                msg.setTouser(thirdInfo.getWxOpenId());
                AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
            }
        }
    }
    //订单提交失败后给用户的消息
    public void test4(){
        AddOrderFailToCustomerMsg msg = new AddOrderFailToCustomerMsg();
        Map<String,String> map = new HashMap<>();
        map.put("order_number","订单编号");
        map.put("paid_price","订单金额");
        map.put("created","创建时间");
        msg.setContentMsg(map);
        msg.setTouser("获取当前的用户的oppenid");
        AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
    }
    //订单发货提醒给用户（1）
    public void test5(){
        OrderDeliveryMsg msg = new OrderDeliveryMsg();
        Map<String,String> map = new HashMap<>();
        map.put("order_number","订单编号");
        map.put("order_time","发货时间");
        map.put("logistics_company","物流公司");
        map.put("courier_number","快递单号");
        map.put("pickup_information","收件信息");
        msg.setContentMsg(map);
        msg.setTouser("获取当前的用户的oppenid");
        AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
    }
    //收货之后需要填写评价（1）
    public void test6(){
        OrderEvaluateMsg msg = new OrderEvaluateMsg();
        Map<String,String> map = new HashMap<>();
        map.put("order_number","订单编号");
        map.put("order_time","订单时间");
        msg.setContentMsg(map);
        msg.setTouser("获取当前的用户的oppenid");
        AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
    }
    //订单取消后给管理员(1)
    public void test7(){
        OrderCloselToClerksMsg msg = new OrderCloselToClerksMsg();
        Map<String,String> map = new HashMap<>();
        map.put("order_number","订单号");
        map.put("goods_name","商品名称");
        map.put("order_price","订单价格");
        msg.setContentMsg(map);
        List<UserThirdInfo> clerks = AppBeanInjector.userThirdInfoService.findAllClerks();
        if (clerks.size()!=0){
            for (UserThirdInfo thirdInfo:clerks) {
                msg.setTouser(thirdInfo.getWxOpenId());
                AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
            }
        }
    }
    //订单取消后给用户的消息(1)
    public void test8(){
        OrderCloselToCustomerMsg msg = new OrderCloselToCustomerMsg();
        Map<String,String> map = new HashMap<>();
        map.put("order_number","订单号");
        map.put("goods_name","商品名称");
        map.put("order_price","订单价格");
        msg.setContentMsg(map);
        msg.setTouser("获取当前的用户的oppenid");
        AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
    }
}

