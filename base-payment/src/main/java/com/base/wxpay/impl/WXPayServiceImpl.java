package com.base.wxpay.impl;

import com.alibaba.fastjson.JSONObject;
import com.base.enums.BaseEnumCollections;
import com.base.utils.type.HttpClientUtils;
import com.base.vo.AjaxResult;
import com.base.wxpay.IWXPayService;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.net.InetAddress;
import java.util.*;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/3/24 9:08
 * @copyright XXX Copyright (c) 2020
 */
@Service
public class WXPayServiceImpl implements IWXPayService {

    private final static Logger log = LoggerFactory.getLogger(WXPayServiceImpl.class);

    @Value("${wxpay.appid}")
    private String appid;
    @Value("${wxpay.mch_id}")
    private String mch_id;
    @Value("${wxpay.api_key}")
    private String api_key;
    @Value("${wxpay.notify_url}")
    private String notify_url;
    @Value("${wxpay.wxpay_url}")
    private String wxpay_url;
    @Value("${wxpay.trade_type}")
    private String trade_type;
    @Value("${wxpay.order_query_url}")
    private String order_query_url;
    @Value("${wxpay.close_order}")
    private String close_order;

    /**
     * TODO 生成codeUrl
     *
     * @param orderInfo
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/02/24 11:47:35
     */
    @Override
    public AjaxResult generateQRCode(Map<String, String> orderInfo) {
        try {
            Map<String, String> requestDataMap = new HashMap<>();
            //公众账号ID
            requestDataMap.put("appid", appid);
            //商户号
            requestDataMap.put("mch_id", mch_id);
            //随机字符串
            requestDataMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //商品描述
            requestDataMap.put("body", orderInfo.get("goodsDescription"));
            //商户订单号
            requestDataMap.put("out_trade_no", orderInfo.get("orderCode"));
            //标价金额，单位为分
            requestDataMap.put("total_fee", orderInfo.get("payMoney"));
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            //终端IP
            requestDataMap.put("spbill_create_ip", hostAddress);
            //通知地址
            requestDataMap.put("notify_url", notify_url);
            //交易类型
            requestDataMap.put("trade_type", trade_type);
            //商品id
            requestDataMap.put("product_id", orderInfo.get("goodsId"));

            //生成签名
            String signValue = WXPayUtil.generateSignature(requestDataMap, api_key);
            //签名
            requestDataMap.put("sign", signValue);

            //请求参数转换为mxl格式
            String requestDataXml = WXPayUtil.mapToXml(requestDataMap);

            //调用微信统一下单接口，返回xml格式的数据，里面包含code_url
            String responseDataXml = HttpClientUtils.doPostByxml(wxpay_url, requestDataXml);

            //把xml格式数据转换成Map
            Map<String, String> responseDataMap = WXPayUtil.xmlToMap(responseDataXml);

            //得到返回后要判断两个是否成功，都成功了才是成功了
            String return_code = responseDataMap.get("return_code");
            if (WXPayConstants.SUCCESS.equals(return_code)) {
                //判断业务处理结果
                String result_code = responseDataMap.get("result_code");
                if (WXPayConstants.SUCCESS.equals(result_code)) {
                    //codeUrl生成成功
                    responseDataMap.put("orderId",orderInfo.get("orderCode"));

                    // 封装返回页面参数
                    JSONObject jsonObject = new JSONObject();
                    // 订单ID
                    jsonObject.put("orderId",responseDataMap.get("orderId"));
                    // 支付二维码
                    jsonObject.put("codeUrl",responseDataMap.get("code_url"));
                    return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "codeUrl生成成功", responseDataMap);
                } else {
                    //业务处理失败
                    return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "支付业务处理失败");
                }
            } else {
                //通信失败
                return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "无法连接到微信服务器");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "服务器异常" + e.getMessage());
        }
    }

    /**
     * TODO 查询支付结果
     *
     * @param orderCode
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/02/24 11:47:35
     */
    @Override
    public AjaxResult queryPat(String orderCode) {
        Map<String, String> responseDataMap = null;
        try {
            int sum = 0;
            while (true) {
                sum++;
                Map<String, String> requestDataMap = new HashMap<>();
                //公众账号ID
                requestDataMap.put("appid", appid);
                //商户号
                requestDataMap.put("mch_id", mch_id);
                //微信transaction_id/商户out_trade_no，订单号
                requestDataMap.put("out_trade_no", orderCode);
                //随机字符串
                requestDataMap.put("nonce_str", WXPayUtil.generateNonceStr());
                //生成签名
                String signValue = WXPayUtil.generateSignature(requestDataMap, api_key);
                //签名
                requestDataMap.put("sign", signValue);

                //请求参数转换为mxl格式
                String requestDataXml = WXPayUtil.mapToXml(requestDataMap);

                //调用查询
                String responseDataXml = HttpClientUtils.doPostByxml(order_query_url, requestDataXml);
                System.out.println(responseDataXml);

                //转为Map
                responseDataMap = WXPayUtil.xmlToMap(responseDataXml);

                //判断通信是否成功
                if (WXPayConstants.SUCCESS.equals(responseDataMap.get("return_code"))) {
                    //判断业务结果
                    if (WXPayConstants.SUCCESS.equals(responseDataMap.get("result_code"))) {
                        //判断支付状态
                        if (WXPayConstants.SUCCESS.equals(responseDataMap.get("trade_state"))) {
                            //获取查询结果
                            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "查询支付结果", responseDataMap);
                        }
                    } else {
                        //业务结果查询失败
                        return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "查询失败", responseDataMap);
                    }
                    //通信失败，请检查网络
                    return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "连接微信服务器失败", responseDataMap);
                }
                //5分钟未支付则超时
                if (sum >= 100) {
                    //支付超时
                    return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "支付超时", responseDataMap);
                }
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "服务器异常" + e.getMessage());
        }
    }

    /**
     * TODO 支付回调
     *
     * @param reString
     * @throws
     * @author 黄芝民
     * @date 2020/02/24 11:47:35
     */
    @Override
    public void notifyUrl(String reString) {
        try {
            //转换为Map方便解析
            Map<String, String> reMap = WXPayUtil.xmlToMap(reString);

            if (WXPayConstants.SUCCESS.equals(reMap.get("return_code"))) {
                if (WXPayConstants.SUCCESS.equals(reMap.get("result_code"))) {
                    //微信支付订单号
                    String transaction_id = reMap.get("transaction_id");
                    //商户订单号(已购套餐表id)
                    String out_trade_no = reMap.get("out_trade_no");
                    //订单金额
                    String total_fee = reMap.get("total_fee");

                    String openid = reMap.get("openid");
                    String trade_type = reMap.get("trade_type");

                    //签名验证
                    boolean signatureValid = WXPayUtil.isSignatureValid(reString, api_key);
                    //校验返回的订单金额是否与商户侧的订单金额一致
                    // 之前订单的金额
                    Integer price = null;
                    if (signatureValid && total_fee.equals(String.valueOf(price))) {
                        //已购买套餐中状态修改为支付成功，并添加支付时间

                    } else {
                        //校验失败记录日志
                        log.error("记录日志-校验失败位置：[WXPayServiceImpl->notifyUrl]");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO 关闭订单
     *
     * @param orderCode
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/02/24 11:47:35
     */
    @Override
    public AjaxResult closeOrder(String orderCode) {
        try {
            Map<String, String> requestDataMap = new HashMap<>();
            //公众账号ID
            requestDataMap.put("appid", appid);
            //商户号
            requestDataMap.put("mch_id", mch_id);
            //商户订单号
            requestDataMap.put("out_trade_no", orderCode);
            //随机字符串
            requestDataMap.put("nonce_str", WXPayUtil.generateNonceStr());

            //生成签名
            String signValue = WXPayUtil.generateSignature(requestDataMap, api_key);
            //签名
            requestDataMap.put("sign", signValue);

            //请求参数转换为mxl格式
            String requestDataXml = WXPayUtil.mapToXml(requestDataMap);

            //调用微信关单接口
            String responseDataXml = HttpClientUtils.doPostByxml(close_order, requestDataXml);
            System.out.println(responseDataXml);

            //把xml格式数据转换成Map
            Map<String, String> responseDataMap = WXPayUtil.xmlToMap(responseDataXml);

            //判断通信结果
            if (WXPayConstants.SUCCESS.equals(responseDataMap.get("return_code"))) {
                //判断关闭结果
                if (WXPayConstants.SUCCESS.equals(responseDataMap.get("result_code"))) {
                    //关闭成功
                    return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "关闭成功", responseDataMap);
                } else {
                    //关闭失败
                    return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "关闭失败", responseDataMap);
                }
            } else {
                //通信失败
                return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "连接微信服务器失败", responseDataMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "服务器异常" + e.getMessage());
        }
    }
}
