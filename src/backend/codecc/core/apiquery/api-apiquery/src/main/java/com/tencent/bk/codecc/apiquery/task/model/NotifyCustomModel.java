/*
 * Tencent is pleased to support the open source community by making BlueKing available.
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the MIT License (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * http://opensource.org/licenses/MIT
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.tencent.bk.codecc.apiquery.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 通知定制信息模型
 * 
 * @date 2019/11/18
 * @version V1.0
 */
@Data
public class NotifyCustomModel {
    /**
     * rtx接收人类型：0-所有项目成员；1-接口人；2-自定义
     */
    @JsonProperty("rtx_receiver_type")
    private String rtxReceiverType;

    /**
     * rtx接收人列表，rtxReceiverType=2时，自定义的接收人保存在该字段
     */
    @JsonProperty("rtx_receiver_list")
    private Set<String> rtxReceiverList;

    /**
     * 邮件收件人类型：0-所有项目成员；1-接口人；2-自定义
     */
    @JsonProperty("email_receiver_type")
    private String emailReceiverType;

    /**
     * 邮件收件人列表，当emailReceiverType=2时，自定义的收件人保存在该字段
     */
    @JsonProperty("email_receiver_list")
    private Set<String> emailReceiverList;

    /**
     * 邮件抄送人列表
     */
    @JsonProperty("email_cc_receiver_list")
    private Set<String> emailCCReceiverList;

    /**
     * 定时报告任务的状态，有效：1，暂停：2
     */
    @JsonProperty("report_status")
    private Integer reportStatus;

    /**
     * 定时报告的发送日期:
     */
    @JsonProperty("report_date")
    private Set<Integer> reportDate;

    /**
     * 定时报告的发送时间，小时位
     */
    @JsonProperty("report_time")
    private Integer reportTime;

    /**
     * 定时报告分钟
     */
    @JsonProperty("report_minute")
    private Integer reportMinute;

    /**
     * 定时报告工具（即邮件需要发送哪些工具的数据报表）
     */
    @JsonProperty("report_tools")
    private Set<String> reportTools;

    /**
     * 定时报告job名称
     */
    @JsonProperty("report_job_name")
    private String reportJobName;


    /**
     * 即时报告状态，有效：1，暂停：2
     */
    @JsonProperty("instant_report_status")
    private String instantReportStatus;


    /*---------群机器人通知start---------*/

    /**
     * 群机器人通知地址
     */
    @JsonProperty("bot_webhook_url")
    private String botWebhookUrl;

    /**
     * 群机器人通知告警级别，多个级别直接相加
     */
    @JsonProperty("bot_remind_severity")
    private Integer botRemindSeverity;

    /**
     * 群机器人通知工具列表
     */
    @JsonProperty("bot_remind_tools")
    private Set<String> botRemaindTools;

    /**
     * 群机器人通知范围
     */
    @JsonProperty("bot_remind_range")
    private Integer botRemindRange;
    /*---------群机器人通知end---------*/

    /**
     * 开源代码规范定时报告的发送日期:
     */
    @JsonProperty("tosa_report_date")
    private List<String> tosaReportDate;

    /**
     * 开源代码规范定时报告的发送时间，格式是：HH:MM
     */
    @JsonProperty("tosa_report_time")
    private String tosaReportTime;

    /**
     * 开源规范工具列表
     */
    @JsonProperty("tosa_tools")
    private List<String> tosaTools;
}
