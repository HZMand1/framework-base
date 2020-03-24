package com.base.wxpay;

import com.base.vo.AjaxResult;

import java.util.Map;

/**
 * TODO 微信支付的详细API文档地址：https://pay.weixin.qq.com/wiki/doc/api/native.php
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/3/24 8:56
 * @copyright XXX Copyright (c) 2020
 */
public interface IWXPayService {
    /**TODO 生成codeUrl
     * @param orderInfo
     * @return AjaxResult
     * @author 黄芝民
     * @date 2020/02/24 11:47:35
     * @throws
     */
    AjaxResult generateQRCode(Map<String, String> orderInfo);

    /**TODO 查询支付结果
     * @param orderCode
     * @return AjaxResult
     * @author 黄芝民
     * @date 2020/02/24 11:47:35
     * @throws
     */
    AjaxResult queryPat(String orderCode);

    /**TODO 支付回调
     * @param reString
     * @author 黄芝民
     * @date 2020/02/24 11:47:35
     * @throws
     */
    void notifyUrl(String reString);

    /**TODO 关闭订单
     * @param orderCode
     * @return AjaxResult
     * @author 黄芝民
     * @date 2020/02/24 11:47:35
     * @throws
     */
    AjaxResult closeOrder(String orderCode);
}
