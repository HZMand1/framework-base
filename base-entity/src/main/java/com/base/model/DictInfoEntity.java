package com.base.model;

import com.base.vo.IfQuery;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * TODO 数据字典实体类
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/13 14:00
 * @copyright XXX Copyright (c) 2019
 */
@Data
@Table(name = "dict_info")
public class DictInfoEntity extends IfQuery {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "pid")
    private String pId;

    @Column(name = "type")
    private String type;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;

    @Column(name = "sort_no")
    private Integer sortNo;

    @Column(name = "status")
    private Integer status;

    @Column(name = "description")
    private String description;

    @Column(name = "string1")
    private String string1;

    @Column(name = "string2")
    private String string2;

    @Column(name = "string3")
    private String string3;

    @Column(name = "update_time")
    private Date updateTime;
}
