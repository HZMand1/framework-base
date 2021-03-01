package com.base.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2021/2/1 15:40
 * @Version V1.0
 * @Copyright  Copyright (c) 2020
 **/
@Data
public class ESGoodsVo {

    @Id
    private String id;// 主键ID

    private Long goodsId; // 商品ID

    private String goodsName; // 商品名称

    private String varietyName; // 品种名称

    private Long merchantId; // 商家ID

    private String merchantName; // 商家名称
}
