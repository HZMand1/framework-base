package com.base.schedule.model;

import com.base.vo.IfQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/3/2 11:15
 * @copyright XXX Copyright (c) 2020
 */
@Data
@Table(name = "sys_job")
@ApiModel("定时任务配置表")
public class SysJobEntity extends IfQuery {
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "任务ID")
    private String id;

    @Column(name = "job_name")
    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @Column(name = "job_group")
    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @Column(name = "invoke_target")
    @ApiModelProperty(value = "调用目标字符串")
    private String invokeTarget;

    @Column(name = "cron_expression")
    @ApiModelProperty(value = "cron执行表达式")
    private String cronExpression;

    @Column(name = "misfire_policy")
    @ApiModelProperty(value = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
    private String misfirePolicy;

    @Column(name = "concurrent")
    @ApiModelProperty(value = "是否并发执行（0允许 1禁止）")
    private String concurrent;

    @Column(name = "status")
    @ApiModelProperty(value = "状态（0正常 1暂停")
    private String status;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date create_time;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

}
